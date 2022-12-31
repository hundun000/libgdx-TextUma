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
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.framework.data.RootSaveData;
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
import hundun.simulationgame.umamusume.gameplay.GameResourcePair;
import hundun.simulationgame.umamusume.gameplay.GameResourceType;
import hundun.simulationgame.umamusume.gameplay.GameRuleData;
import hundun.simulationgame.umamusume.gameplay.IGameplayFrontend;
import hundun.simulationgame.umamusume.gameplay.TurnConfig;
import hundun.simulationgame.umamusume.gameplay.UmaManager;
import hundun.simulationgame.umamusume.gameplay.AccountSaveData;
import hundun.simulationgame.umamusume.gameplay.AccountSaveData.OperationBoardState;
import hundun.simulationgame.umamusume.record.base.IRecorder;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode.EndRecordHorseInfo;
import hundun.simulationgame.umamusume.record.text.CharImageRecorder;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.StrategyPackage;
import hundun.simulationgame.umamusume.record.text.BotTextCharImageRender.Translator;
import lombok.Getter;

/**
 * implements ILibgdxGameplayFrontend: 对Libgdx提供服务；
 * implements IGameplayFrontend: 对接UmaManager；
 * @author hundun
 * Created on 2022/08/02
 */
public class LibgdxGameplayFrontend implements ILibgdxGameplayFrontend, IGameplayFrontend {
    TextUmaGame game;
    private UmaPlayScreen playScreen;
    private UmaManager manager;
    LibgdxFrontEndSaveData frontEndSaveData;
    public static final String SINGLETON_ID = "LIBGDX_GAMEPLAY_FRONTEND";
    
    static final int smallStepColdDownCountReset = BasePlayScreen.LOGIC_FRAME_PER_SECOND * 2;
    int smallStepColdDownCount = -1;
    
    public LibgdxGameplayFrontend(TextUmaGame game, UmaPlayScreen playScreen) {
        this.playScreen = playScreen;
        this.game = game;
        this.manager = new UmaManager(this);
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
    public void notifiedCheckUi() {
        this.checkUiByAreaAndState(playScreen.getArea());
    }

    @Override
    public void saveCurrent() {
        game.saveCurrent();
    }

    @Override
    public void notifiedHorseStatusChange(HorsePrototype horsePrototype, String trainDescription,
            List<GameResourcePair> gainList) {
        playScreen.getMainInfoBoard().updateAsHorseStatus(
                horsePrototype, 
                trainDescription, 
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
    public void trainAndNextDay(String trainDescription, List<ResourcePair> costList, List<ResourcePair> gainList) {
        manager.trainAndNextDay(manager.getAccountSaveData(SINGLETON_ID), trainDescription, innerToGameResourceType(costList), innerToGameResourceType(gainList));
    }

    public void subApplySaveData(Map<String, AccountSaveData> umaSaveData, GameRuleData gameRuleData,
            LibgdxFrontEndSaveData frontEndSaveData) {
        manager.applySaveData(umaSaveData, gameRuleData);
        this.frontEndSaveData = frontEndSaveData;
    }

    public void subCurrentSituationToSaveData(RootSaveData saveData) {
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
        game.getModelContext().getStorageManager().modifyAllResourceNum(
                map.entrySet().stream().collect(Collectors.toMap(
                        it -> gameResourceTypeToInner(it.getKey()), 
                        it -> it.getValue())), 
                plus);
    }

    

}
