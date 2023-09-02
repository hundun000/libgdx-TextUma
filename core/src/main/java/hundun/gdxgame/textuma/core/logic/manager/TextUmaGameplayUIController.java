package hundun.gdxgame.textuma.core.logic.manager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.ui.screen.ScreenContext;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.core.data.MyGameplaySaveData;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BaseUmaPlayScreen;
import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.game.gameplay.IGameplayFrontend;
import hundun.simulationgame.umamusume.game.gameplay.UmaGameplayManager;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourcePair;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourceType;
import hundun.simulationgame.umamusume.game.gameplay.data.GameRuleData;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainActionType;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainRuleConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.TurnConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData.OperationBoardState;
import hundun.simulationgame.umamusume.record.base.IRaceRecorder;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.text.CharImageRecorder;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import hundun.simulationgame.umamusume.record.text.Translator;
import hundun.simulationgame.umamusume.record.text.Translator.StrategyPackage;

/**
 * implements ILibgdxGameplayFrontend: 对Libgdx提供服务；
 * implements IGameplayFrontend: 对接UmaManager；
 * @author hundun
 * Created on 2022/08/02
 */
public class TextUmaGameplayUIController implements IGameplayUIController, IGameplayFrontend {
    TextUmaGame game;
    private UmaPlayScreen playScreen;
    private UmaGameplayManager manager;
    LibgdxFrontEndSaveData frontEndSaveData;
    public static final String SINGLETON_ID = "LIBGDX_GAMEPLAY_FRONTEND";
    
    static final int smallStepColdDownCountReset = BaseUmaPlayScreen.LOGIC_FRAME_PER_SECOND * 2;
    int smallStepColdDownCount = -1;
    
    public TextUmaGameplayUIController(TextUmaGame game, ScreenContext screenContext) {
        this.playScreen = screenContext.getPlayScreen();
        this.game = game;
        
        Translator translator = Translator.Factory.english();
        StrategyPackage strategyPackage = new StrategyPackage();
        StrategyPackage.Factory.toLongWidth(strategyPackage);
        StrategyPackage.Factory.toCameraProcessBarCharType2(strategyPackage);

        strategyPackage.setHorseRaceStartTemplate(
                "${TRACK_PART}: ${NAME_PART} "
                + "${SPEED_KEY}${SPEED_VALUE}, "
                + "${STAMINA_KEY}${STAMINA_VALUE}, "
                + "${POWER_KEY}${POWER_VALUE}\n");
        IRaceRecorder<TextFrameData> raceRecorder = new CharImageRecorder(translator, strategyPackage);
        this.manager = new UmaGameplayManager(translator, raceRecorder, this);

        game.getSaveHandler().registerSubHandler(this);
    }

    @Override
    public void log(String tag, String message) {
        Gdx.app.log(tag, message);
    }

    @Override
    public void endRaceRecord() {
        manager.endRaceRecord(manager.getAccountSaveData(SINGLETON_ID));
    }
    
    @Override
    public OperationBoardState getOperationBoardState() {
        return manager.getOperationBoardState(manager.getAccountSaveData(SINGLETON_ID));
    }

    @Override
    public void bigStepRaceRecordNode() {
        int before = frontEndSaveData.currentRaceRecordNodeIndex;
        nextLimitOrEventRaceRecordNode(999999);
        Gdx.app.log(this.getClass().getSimpleName(), 
                JavaFeatureForGwt.stringFormat(
                        "bigStepRaceRecordNode result = %s -> %s", 
                        before,
                        frontEndSaveData.currentRaceRecordNodeIndex
                        )
                );
    }

    
    @Override
    public void onLogicFrame() {
        if (isWaitingNextRaceRecordNode()) {
            smallStepRaceRecordNode();
        }
    }
    

    @Override
    public boolean isWaitingEndRaceRecord() {
        return manager.getAccountSaveData(SINGLETON_ID).currentRaceRecordNodes != null && 
                frontEndSaveData.currentRaceRecordNodeIndex + 1 == manager.getAccountSaveData(SINGLETON_ID).currentRaceRecordNodes.size();
    }
    
    @Override
    public boolean isWaitingNextRaceRecordNode() {
        return manager.getAccountSaveData(SINGLETON_ID).currentRaceRecordNodes != null && 
                frontEndSaveData.currentRaceRecordNodeIndex + 1 < manager.getAccountSaveData(SINGLETON_ID).currentRaceRecordNodes.size();
    }
    
    @Override
    public void smallStepRaceRecordNode() {
        if (smallStepColdDownCount > 0) {
            smallStepColdDownCount--;
            return;
        }
        nextLimitOrEventRaceRecordNode(10);
    }
    
    private void checkUiByAreaAndState(String currentArea) {
        AccountSaveData accountSaveData = manager.getAccountSaveData(SINGLETON_ID);
        TurnConfig currentTurnConfig = manager.getCurrentTurnConfig(accountSaveData);
        switch (currentArea) {
            case GameArea.AREA_TRAIN:
                if (getOperationBoardState() == OperationBoardState.TRAIN_DAY) {
                    playScreen.getMainInfoBoard().updateAsHorseStatus(manager.getAccountSaveData(SINGLETON_ID).playerHorse, null, null);
                } else {
                    playScreen.getMainInfoBoard().updateAsHorseStatus(manager.getAccountSaveData(SINGLETON_ID).playerHorse, "Not train-day.", null);
                }
                break;
            case GameArea.AREA_RACE:
                if (getOperationBoardState() == OperationBoardState.RACE_DAY_RACE_READY) {
                    RacePrototype racePrototype = currentTurnConfig.getRace();
                    Map<HorsePrototype, String> rivalHorsesToRunStrategyTextMap = currentTurnConfig.getRivalHorses().stream()
                            .collect(Collectors.toMap(
                                    horse -> horse, 
                                    horse -> manager.getTranslator().get(horse.getDefaultRunStrategyType()))
                                    )
                            ;
                    playScreen.getMainInfoBoard().updateAsRaceReady(racePrototype, rivalHorsesToRunStrategyTextMap);
                } else if (getOperationBoardState() == OperationBoardState.RACE_DAY_RACE_HAS_RESULT_RECORD) {
                    if (isWaitingEndRaceRecord()) {
                        playScreen.getMainInfoBoard().updateAsRaceEndResult(
                                manager.getAccountSaveData(SINGLETON_ID).sortedRaceEndRecordNode,
                                currentTurnConfig.getRankToAwardMap()
                                );
                    } else {
                        playScreen.getMainInfoBoard().updateAsRaceRecordNode(manager.getAccountSaveData(SINGLETON_ID).currentRaceRecordNodes.get(frontEndSaveData.currentRaceRecordNodeIndex));
                    }
                    
                } else {
                    Integer nextRaceTurnTimeDiff = manager.getNextRaceTurnTimeDiff(accountSaveData);
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
    
    private void nextLimitOrEventRaceRecordNode(int skipCount) {

        RecordNode<TextFrameData> nextImportantNode = manager.getAccountSaveData(SINGLETON_ID).currentRaceRecordNodes.stream()
                .skip(frontEndSaveData.currentRaceRecordNodeIndex + 1)
                .filter(node -> node.getContent().getEventInfo() != null && node.getContent().getRaceInfo() != null)
                .findFirst()
                .orElseGet(() -> null);
        
        if (nextImportantNode != null) {
            int nextImportantNodeIndex = manager.getAccountSaveData(SINGLETON_ID).currentRaceRecordNodes.indexOf(nextImportantNode);
            if (nextImportantNodeIndex > frontEndSaveData.currentRaceRecordNodeIndex + skipCount) {
                frontEndSaveData.currentRaceRecordNodeIndex = frontEndSaveData.currentRaceRecordNodeIndex + skipCount;
            } else {
                frontEndSaveData.currentRaceRecordNodeIndex = nextImportantNodeIndex;
                smallStepColdDownCount = smallStepColdDownCountReset;
            }
        } else {
            frontEndSaveData.currentRaceRecordNodeIndex = manager.getAccountSaveData(SINGLETON_ID).currentRaceRecordNodes.size() -1;
        }

        Gdx.app.log(this.getClass().getSimpleName(), "nextLimitOrEventRaceRecordNode result = " + frontEndSaveData.currentRaceRecordNodeIndex);
        checkUiByAreaAndState(playScreen.getArea());
    }
    
    @Override
    public void notifiedReplayRaceRecord() {
        replayRaceRecord();
    }
    
    private String gameResourceTypeToInner(GameResourceType type) {
        switch (type) {
            case TURN:
                return ResourceType.TURN;
            case COIN:
                return ResourceType.COIN;
            case HORSE_SPEED:
                return ResourceType.HORSE_SPEED;
            case HORSE_POWER:
                return ResourceType.HORSE_POWER;
            case HORSE_STAMINA:
                return ResourceType.HORSE_STAMINA;
            default:
                throw new UnsupportedOperationException();
        }
    }
    
    private GameResourceType innerToGameResourceType(String type) {
        switch (type) {
            case ResourceType.TURN:
                return GameResourceType.TURN;
            case ResourceType.COIN:
                return GameResourceType.COIN;
            case ResourceType.HORSE_SPEED:
                return GameResourceType.HORSE_SPEED;
            case ResourceType.HORSE_POWER:
                return GameResourceType.HORSE_POWER;
            case ResourceType.HORSE_STAMINA:
                return GameResourceType.HORSE_STAMINA;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public void notifiedChangeOperationBoardState() {
        this.checkUiByAreaAndState(playScreen.getArea());
        game.getSaveHandler().gameSaveCurrent();
    }


    @Override
    public void notifiedHorseStatusChange(HorsePrototype horsePrototype,
            List<GameResourcePair> gainList) {
        playScreen.getMainInfoBoard().updateAsHorseStatus(
                horsePrototype, 
                "Train done.", 
                gainList.stream()
                    .map(it -> new ResourcePair(
                            gameResourceTypeToInner(it.getType()), 
                            it.getAmount()))
                    .collect(Collectors.toList())
                );
    }

    private List<GameResourcePair> innerToGameResourceType(List<ResourcePair> list) {
        return list.stream()
                .map(it -> new GameResourcePair(
                        innerToGameResourceType(it.getType()), 
                        it.getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public void onGameAreaChange(String last, String current) {
        checkUiByAreaAndState(current);
    }

    @Override
    public void trainAndNextDay(TrainActionType type) {
        manager.trainAndNextDay(manager.getAccountSaveData(SINGLETON_ID), type);
    }

    @Override
    public void applyGameplaySaveData(MyGameplaySaveData saveData) {
        manager.applySaveData(saveData.getUmaSaveData(), saveData.getGameRuleData());
        this.frontEndSaveData = saveData.getFrontEndSaveData();
    }

    @Override
    public void currentSituationToGameplaySaveData(MyGameplaySaveData saveData) {
        saveData.setFrontEndSaveData(frontEndSaveData);
        saveData.setUmaSaveData(manager.getAccountSaveDataMap());
        saveData.setGameRuleData(manager.getGameRuleData());
    }

    @Override
    public void raceStart() {
        manager.raceStart(manager.getAccountSaveData(SINGLETON_ID));
    }

    @Override
    public void replayRaceRecord() {
        smallStepColdDownCount = 0;
        frontEndSaveData.currentRaceRecordNodeIndex = 0;
        
        checkUiByAreaAndState(playScreen.getArea());
    }

    @Override
    public Map<String, Long> getOwnResoueces() {
        return manager.getAccountSaveData(SINGLETON_ID).getOwnResoueces().entrySet().stream()
                .collect(Collectors.toMap(
                        it -> gameResourceTypeToInner(it.getKey()), 
                        it -> it.getValue()))
                ;
    }

    @Override
    public void notifiedModifiedResourceNum(Map<GameResourceType, Long> map, boolean plus) {
        game.getManagerContext().getStorageManager().modifyAllResourceNum(
                map.entrySet().stream().collect(Collectors.toMap(
                        it -> gameResourceTypeToInner(it.getKey()), 
                        it -> it.getValue())), 
                plus);
    }

    @Override
    public TrainRuleConfig getTrainOutputComponentConfig(TrainActionType trainActionType) {
        return manager.getGameRuleData().getTrainRuleConfigMap().get(trainActionType);
    }

    @Override
    public Map<String, Long> gameResourceTypeToInner(List<GameResourcePair> list) {
        return list.stream().collect(Collectors.toMap(
                it -> gameResourceTypeToInner(it.getType()), 
                it -> it.getAmount()));
    }

    

}
