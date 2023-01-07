package hundun.gdxgame.textuma.core.logic.manager;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.share.framework.data.RootSaveData;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.simulationgame.umamusume.game.gameplay.IGameplayFrontend;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourcePair;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourceType;
import hundun.simulationgame.umamusume.game.gameplay.data.GameRuleData;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainActionType;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainRuleConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData.OperationBoardState;

/**
 * @author hundun
 * Created on 2022/08/10
 */
public interface ILibgdxGameplayFrontend extends IGameAreaChangeListener, ILogicFrameListener {
    void raceStart();
    OperationBoardState getOperationBoardState();
    void replayRaceRecord();
    
    void smallStepRaceRecordNode();
    void bigStepRaceRecordNode();
    boolean isWaitingNextRaceRecordNode();
    boolean isWaitingEndRaceRecord();
    void endRaceRecord();
    
    void subApplySaveData(Map<String, AccountSaveData> umaSaveData, GameRuleData gameRuleData,
            LibgdxFrontEndSaveData frontEndSaveData);
    void subCurrentSituationToSaveData(RootSaveData saveData);
    Map<String, Long> getOwnResoueces();
    void trainAndNextDay(TrainActionType type);
    
    TrainRuleConfig getTrainOutputComponentConfig(TrainActionType trainActionType);
    Map<String, Long> gameResourceTypeToInner(List<GameResourcePair> list);
    
    
    
}
