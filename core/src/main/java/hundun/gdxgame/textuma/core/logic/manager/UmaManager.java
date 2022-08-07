package hundun.gdxgame.textuma.core.logic.manager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.data.UmaSaveData;
import hundun.gdxgame.textuma.core.data.UmaSaveData.TurnConfig;
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.manager.UmaManager.UmaState;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.framework.util.JavaHighVersionFeature;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.race.RacePrototype;
import hundun.simulationgame.umamusume.race.RacePrototypeFactory;
import hundun.simulationgame.umamusume.race.RaceSituation;
import hundun.simulationgame.umamusume.race.TrackWetType;
import hundun.simulationgame.umamusume.record.IRecorder;
import hundun.simulationgame.umamusume.record.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.Translator;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.StrategyPackage;
import hundun.simulationgame.umamusume.record.text.CharImageRecorder;
import hundun.simulationgame.umamusume.record.text.TextFrameData;

/**
 * @author hundun
 * Created on 2022/08/02
 */
public class UmaManager implements IGameAreaChangeListener, IUserRaceActionHandlerCore, ILogicFrameListener {
    TextUmaGame game;
    private UmaPlayScreen playScreen;
    UmaSaveData umaSaveData;
    
    //TurnConfig currentTurnConfig;
    private final IRecorder<TextFrameData> displayer;
    
    // ------ replace-lombok ------
    
    public UmaState getState() {
        return umaSaveData.state;
    }
    public void setStateAndLog(UmaState state) {
        umaSaveData.state = state;
        Gdx.app.log(this.getClass().getSimpleName(), "state change to: " + state);
    }
    
    public UmaManager(TextUmaGame game, UmaPlayScreen playScreen) {
        this.playScreen = playScreen;
        this.game = game;
        
        
        Translator translator = Translator.Factory.english();
        StrategyPackage strategyPackage = StrategyPackage.Factory.longWidth();
        // no GUTS WISDOM
        strategyPackage.setHorseRaceStartTemplate(
                "${TRACK_PART}: ${NAME_PART} "
                + "${SPEED_KEY}${SPEED_VALUE}, "
                + "${STAMINA_KEY}${STAMINA_VALUE}, "
                + "${POWER_KEY}${POWER_VALUE}\n");
        
        this.displayer = new CharImageRecorder(translator, strategyPackage);
    }
    
    

    
    public void applySaveData(UmaSaveData umaSaveData) {
        this.umaSaveData = umaSaveData;
    }
    public UmaSaveData getUmaSaveData() {
        return umaSaveData;
    }
    
    public enum UmaState {
        TRAIN_DAY,
        RACE_DAY_RACE_READY,
        RACE_DAY_RACE_HAS_RESULT_RECORD
        ;
    }
    
    @Override
    public boolean waitingEndRaceRecord() {
        return umaSaveData.currentRaceRecordNodes != null && 
                umaSaveData.currentRaceRecordNodeIndex + 1 == umaSaveData.currentRaceRecordNodes.size();
    }
    
    @Override
    public boolean waitingNextRaceRecordNode() {
        return umaSaveData.currentRaceRecordNodes != null && 
                umaSaveData.currentRaceRecordNodeIndex + 1 < umaSaveData.currentRaceRecordNodes.size();
    }
    
    @Override
    public void replayRaceRecord() {
        smallStepColdDownCount = 0;
        umaSaveData.currentRaceRecordNodeIndex = 0;
        
        checkUiByAreaAndState(playScreen.getArea());
    }
    
    @Override
    public void smallStepRaceRecordNode() {
        if (smallStepColdDownCount > 0) {
            smallStepColdDownCount--;
            return;
        }
        nextLimitOrEventRaceRecordNode(10);
    }
    
    static final int smallStepColdDownCountReset = BasePlayScreen.LOGIC_FRAME_PER_SECOND * 2;
    int smallStepColdDownCount = -1;
    
    private void nextLimitOrEventRaceRecordNode(int skipCount) {
        boolean toNext = waitingNextRaceRecordNode();        
        while (toNext) {
            umaSaveData.currentRaceRecordNodeIndex++;
            RecordNode<TextFrameData> node = umaSaveData.currentRaceRecordNodes.get(umaSaveData.currentRaceRecordNodeIndex);
            boolean isImportant = node.getContent().getEventInfo() != null && node.getContent().getRaceInfo() != null;
            if (isImportant) {
                smallStepColdDownCount = smallStepColdDownCountReset;
                skipCount = 0;
            } else {
                skipCount--;
            }
            toNext = waitingNextRaceRecordNode() && skipCount > 0;
        }
        Gdx.app.log(this.getClass().getSimpleName(), "nextLimitOrEventRaceRecordNode result = " + umaSaveData.currentRaceRecordNodeIndex);
        checkUiByAreaAndState(playScreen.getArea());
    }
    
    @Override
    public void bigStepRaceRecordNode() {
        nextLimitOrEventRaceRecordNode(999999);
    }
    
    @Override
    public void raceStart() {
        TurnConfig currentTurnConfig = getCurrentTurnConfig();
        RaceSituation currentRaceSituation = new RaceSituation(displayer, currentTurnConfig.race, TrackWetType.GOOD);
        HorsePrototype base = umaSaveData.playerHorse;
        
        List<HorsePrototype> randomRivals = currentTurnConfig.rivalHorses;
        randomRivals.forEach(item -> {
            currentRaceSituation.addHorse(item, item.getDefaultRunStrategyType());
        });
        currentRaceSituation.addHorse(base, base.getDefaultRunStrategyType());
        
        currentRaceSituation.calculateResult();
        Gdx.app.log(this.getClass().getSimpleName(), "Race start and calculateResult done, will displayer.printRecordPackage");
        displayer.printRecordPackage();
        
        setStateAndLog(UmaState.RACE_DAY_RACE_HAS_RESULT_RECORD);
        umaSaveData.currentRaceRecordNodes = displayer.getRecordPackage().getNodes();
        
        replayRaceRecord();
    }
    
    private TurnConfig getCurrentTurnConfig() {
        int currentTurn = (int) game.getModelContext().getStorageManager().getResourceNumOrZero(ResourceType.TURN);
        return umaSaveData.turnConfigMap.get(currentTurn);
        
    }

    private void nextDay() {
        game.getModelContext().getStorageManager().modifyAllResourceNum(
                JavaHighVersionFeature.mapOf(ResourceType.TURN, 1L), 
                true
                );
        TurnConfig currentTurnConfig = getCurrentTurnConfig();
        if (currentTurnConfig == null) {
            setStateAndLog(UmaState.TRAIN_DAY);
        } else {
            setStateAndLog(UmaState.RACE_DAY_RACE_READY);
        }
        checkUiByAreaAndState(playScreen.getArea());
    }

    public void trainAndNextDay(String trainDescription, List<ResourcePair> costList, List<ResourcePair> gainList) {
        HorsePrototype horsePrototype = umaSaveData.playerHorse;
        playScreen.getMainInfoBoard().updateAsHorseStatus(horsePrototype, trainDescription, gainList);
        gainList.forEach(resourcePair -> {
            switch (resourcePair.getType()) {
                case ResourceType.HORSE_SPEED:
                    horsePrototype.setBaseSpeed(horsePrototype.getBaseSpeed() + resourcePair.getAmount().intValue());
                    break;
                default:
                    break;
            }
        });
        game.getModelContext().getStorageManager().modifyAllResourceNum(costList, false);
        
        Gdx.app.log(this.getClass().getSimpleName(), "train done, gain = " + gainList.toString());
        nextDay();
    }
    
    
    
    private void checkUiByAreaAndState(String currentArea) {
        TurnConfig currentTurnConfig = getCurrentTurnConfig();
        switch (currentArea) {
            case GameArea.AREA_TRAIN:
                if (getState() == UmaState.TRAIN_DAY) {
                    playScreen.getMainInfoBoard().updateAsHorseStatus(umaSaveData.playerHorse, null, null);
                } else {
                    playScreen.getMainInfoBoard().updateAsText("Not train-day.");
                }
                break;
            case GameArea.AREA_RACE:
                if (getState() == UmaState.RACE_DAY_RACE_READY) {
                    RacePrototype racePrototype = currentTurnConfig.race;
                    List<HorsePrototype> rivalHorses = currentTurnConfig.rivalHorses;
                    playScreen.getMainInfoBoard().updateAsRaceReady(racePrototype, rivalHorses);
                } else if (getState() == UmaState.RACE_DAY_RACE_HAS_RESULT_RECORD) {
                    playScreen.getMainInfoBoard().updateAsRaceRecordNode(umaSaveData.currentRaceRecordNodes.get(umaSaveData.currentRaceRecordNodeIndex));
                } else {
                    playScreen.getMainInfoBoard().updateAsText("Not race-day.");
                }
                break;
            default:
                break;
        }
        Gdx.app.log(this.getClass().getSimpleName(), "checkUiByAreaAndState done.");
    }

    @Override
    public void onGameAreaChange(String last, String current) {
        checkUiByAreaAndState(current);
        //playScreen.getMainInfoBoard().updateAsClear();
    }
    
    @Override
    public void endRaceRecord() {
        umaSaveData.currentRaceRecordNodes = null;
        nextDay();
    }
    @Override
    public void onLogicFrame() {
        if (waitingNextRaceRecordNode()) {
            smallStepRaceRecordNode();
        }
    }

    

}
