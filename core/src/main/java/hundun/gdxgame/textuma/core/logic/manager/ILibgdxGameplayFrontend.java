package hundun.gdxgame.textuma.core.logic.manager;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.textuma.share.framework.data.RootSaveData;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.simulationgame.umamusume.gameplay.AccountSaveData;
import hundun.simulationgame.umamusume.gameplay.AccountSaveData.OperationBoardState;
import hundun.simulationgame.umamusume.gameplay.GameRuleData;
import hundun.simulationgame.umamusume.gameplay.IGameplayFrontend;

/**
 * @author hundun
 * Created on 2022/08/10
 */
public interface ILibgdxGameplayFrontend extends IGameAreaChangeListener, ILogicFrameListener {
    void raceStart();
    void trainAndNextDay(String trainDescription, List<ResourcePair> costList, List<ResourcePair> gainList);
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
    
    
    
}
