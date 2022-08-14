package hundun.gdxgame.textuma.core.logic.manager;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.race.RacePrototypeFactory;
import hundun.simulationgame.umamusume.core.race.RaceSituation;
import hundun.simulationgame.umamusume.core.race.TrackWetType;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.record.base.IRecorder;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode.EndRecordHorseInfo;
import hundun.simulationgame.umamusume.record.text.CharImageRecorder;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.StrategyPackage;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.Translator;

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
    private final Translator translator;
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
        
        
        this.translator = Translator.Factory.english();
        StrategyPackage strategyPackage = new StrategyPackage();
        strategyPackage.setHorsePositionBarMaxWidth(30);
        strategyPackage.setCameraProcessBarWidth(25);
        strategyPackage.setCameraProcessBarChar1("█");
        strategyPackage.setCameraProcessBarChar2("▓");
        strategyPackage.setCameraProcessBarChar3("░");

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

        RecordNode<TextFrameData> nextImportantNode = umaSaveData.currentRaceRecordNodes.stream()
                .skip(umaSaveData.currentRaceRecordNodeIndex + 1)
                .filter(node -> node.getContent().getEventInfo() != null && node.getContent().getRaceInfo() != null)
                .findFirst()
                .orElseGet(() -> null);
        
        if (nextImportantNode != null) {
            int nextImportantNodeIndex = umaSaveData.currentRaceRecordNodes.indexOf(nextImportantNode);
            if (nextImportantNodeIndex > umaSaveData.currentRaceRecordNodeIndex + skipCount) {
                umaSaveData.currentRaceRecordNodeIndex = umaSaveData.currentRaceRecordNodeIndex + skipCount;
            } else {
                umaSaveData.currentRaceRecordNodeIndex = nextImportantNodeIndex;
                smallStepColdDownCount = smallStepColdDownCountReset;
            }
        } else {
            umaSaveData.currentRaceRecordNodeIndex = umaSaveData.currentRaceRecordNodes.size() -1;
        }

        Gdx.app.log(this.getClass().getSimpleName(), "nextLimitOrEventRaceRecordNode result = " + umaSaveData.currentRaceRecordNodeIndex);
        checkUiByAreaAndState(playScreen.getArea());
    }
    
    @Override
    public void bigStepRaceRecordNode() {
        int before = umaSaveData.currentRaceRecordNodeIndex;
        nextLimitOrEventRaceRecordNode(999999);
        Gdx.app.log(this.getClass().getSimpleName(), 
                JavaFeatureForGwt.stringFormat(
                        "bigStepRaceRecordNode result = %s -> %s", 
                        before,
                        umaSaveData.currentRaceRecordNodeIndex
                        )
                );

    }
    
    @Override
    public void raceStart() {
        TurnConfig currentTurnConfig = getCurrentTurnConfig();
        RaceSituation currentRaceSituation = new RaceSituation(displayer, currentTurnConfig.getRace(), TrackWetType.GOOD);
        HorsePrototype base = umaSaveData.playerHorse;
        
        List<HorsePrototype> randomRivals = currentTurnConfig.getRivalHorses();
        randomRivals.forEach(item -> {
            currentRaceSituation.addHorse(item, item.getDefaultRunStrategyType());
        });
        currentRaceSituation.addHorse(base, base.getDefaultRunStrategyType());
        
        currentRaceSituation.calculateResult();
        Gdx.app.log(this.getClass().getSimpleName(), "Race start and calculateResult done");
        //displayer.printRecordPackage();
        
        setStateAndLog(UmaState.RACE_DAY_RACE_HAS_RESULT_RECORD);
        umaSaveData.currentRaceRecordNodes = displayer.getRecordPackage().getNodes();
        umaSaveData.sortedRaceEndRecordNode = displayer.getRecordPackage().getEndNode().getHorseInfos().stream()
                .sorted(Comparator.comparing(EndRecordHorseInfo::getReachTick))
                .collect(Collectors.toList())
                ;
        
        replayRaceRecord();
    }
    
    private TurnConfig getCurrentTurnConfig() {
        int currentTurn = (int) game.getModelContext().getStorageManager().getResourceNumOrZero(ResourceType.TURN);
        return umaSaveData.turnConfigMap.get(currentTurn);
        
    }

    private Integer getNextRaceTurnTimeDiff() {
        int currentTurn = (int) game.getModelContext().getStorageManager().getResourceNumOrZero(ResourceType.TURN);
        Integer nextRaceTurn = umaSaveData.turnConfigMap.entrySet().stream()
            .filter(entry -> entry.getKey() > currentTurn)
            .sorted(Comparator.comparing(Entry<Integer, TurnConfig>::getKey))
            .map(entry -> entry.getKey())
            .findFirst()
            .orElseGet(() -> null)
            ;
        return nextRaceTurn != null ? (nextRaceTurn - currentTurn) : null;
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
        
        game.saveCurrent();
    }

    public void trainAndNextDay(String trainDescription, List<ResourcePair> costList, List<ResourcePair> gainList) {
        HorsePrototype horsePrototype = umaSaveData.playerHorse;
        playScreen.getMainInfoBoard().updateAsHorseStatus(horsePrototype, trainDescription, gainList);
        gainList.forEach(resourcePair -> {
            switch (resourcePair.getType()) {
                case ResourceType.HORSE_SPEED:
                    horsePrototype.setBaseSpeed(horsePrototype.getBaseSpeed() + resourcePair.getAmount().intValue());
                    break;
                case ResourceType.HORSE_STAMINA:
                    horsePrototype.setBaseStamina(horsePrototype.getBaseStamina() + resourcePair.getAmount().intValue());
                    break;
                case ResourceType.HORSE_POWER:
                    horsePrototype.setBasePower(horsePrototype.getBasePower() + resourcePair.getAmount().intValue());
                    break;
                default:
                    break;
            }
        });
        if (costList != null) {
            game.getModelContext().getStorageManager().modifyAllResourceNum(costList, false);
        }
        
        
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
                    playScreen.getMainInfoBoard().updateAsHorseStatus(umaSaveData.playerHorse, "Not train-day.", null);
                }
                break;
            case GameArea.AREA_RACE:
                if (getState() == UmaState.RACE_DAY_RACE_READY) {
                    RacePrototype racePrototype = currentTurnConfig.getRace();
                    Map<HorsePrototype, String> rivalHorsesToRunStrategyTextMap = currentTurnConfig.getRivalHorses().stream()
                            .collect(Collectors.toMap(
                                    horse -> horse, 
                                    horse -> translator.get(horse.getDefaultRunStrategyType()))
                                    )
                            ;
                    playScreen.getMainInfoBoard().updateAsRaceReady(racePrototype, rivalHorsesToRunStrategyTextMap);
                } else if (getState() == UmaState.RACE_DAY_RACE_HAS_RESULT_RECORD) {
                    if (waitingEndRaceRecord()) {
                        playScreen.getMainInfoBoard().updateAsRaceEndResult(
                                umaSaveData.sortedRaceEndRecordNode,
                                currentTurnConfig.getRankToAwardMap()
                                );
                    } else {
                        playScreen.getMainInfoBoard().updateAsRaceRecordNode(umaSaveData.currentRaceRecordNodes.get(umaSaveData.currentRaceRecordNodeIndex));
                    }
                    
                } else {
                    Integer nextRaceTurnTimeDiff = getNextRaceTurnTimeDiff();
                    if (nextRaceTurnTimeDiff != null) {
                        playScreen.getMainInfoBoard().updateAsText(JavaFeatureForGwt.stringFormat(
                                "Not race-day now. The next race is in %s %s.", 
                                nextRaceTurnTimeDiff,
                                nextRaceTurnTimeDiff > 1 ? "days" : "day"
                                ));
                    } else {
                        playScreen.getMainInfoBoard().updateAsText(
                                TextUmaGame.NO_MORE_RACE_MESSAGE
                                );
                    }
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
        EndRecordHorseInfo playerHorseInfo = umaSaveData.sortedRaceEndRecordNode.stream()
                .filter(horseInfo -> horseInfo.getHorseName().equals(umaSaveData.playerHorse.getCharImage()))
                .findAny()
                .get()
                ;
        int rank = umaSaveData.sortedRaceEndRecordNode.indexOf(playerHorseInfo);
        long award = getCurrentTurnConfig().getRankToAwardMap().get(rank);
        game.getModelContext().getStorageManager().modifyAllResourceNum(
                JavaFeatureForGwt.mapOf(ResourceType.COIN, award), true);
        umaSaveData.currentRaceRecordNodes = null;
        umaSaveData.sortedRaceEndRecordNode = null;
        nextDay();
    }
    @Override
    public void onLogicFrame() {
        if (waitingNextRaceRecordNode()) {
            smallStepRaceRecordNode();
        }
    }

    

}
