package hundun.gdxgame.textuma.core.logic.manager;

import java.util.List;
import java.util.Map;

import hundun.gdxgame.gamelib.starter.save.PairChildrenSaveHandler.ISubGameplaySaveHandler;
import hundun.gdxgame.gamelib.starter.save.PairChildrenSaveHandler.ISubSystemSettingSaveHandler;
import hundun.gdxgame.textuma.core.data.MyGameplaySaveData;
import hundun.gdxgame.textuma.core.data.RootSaveData.MySystemSettingSaveData;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.GameResourcePair;
import hundun.simulationgame.umamusume.game.gameplay.data.GameRuleData;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainActionType;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainRuleConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData.OperationBoardState;

/**
 * @author hundun
 * Created on 2022/08/10
 */
public interface IGameplayUIController extends
        IGameAreaChangeListener,
        ILogicFrameListener,
        ISubGameplaySaveHandler<MyGameplaySaveData>,
        ISubSystemSettingSaveHandler<MySystemSettingSaveData>
{
    void raceStart();
    OperationBoardState getOperationBoardState();
    void replayRaceRecord();
    
    void smallStepRaceRecordNode();
    void bigStepRaceRecordNode();
    boolean isWaitingNextRaceRecordNode();
    boolean isWaitingEndRaceRecord();
    void endRaceRecord();
    Map<String, Long> getOwnResoueces();
    void trainAndNextDay(TrainActionType type);
    
    TrainRuleConfig getTrainOutputComponentConfig(TrainActionType trainActionType);
    Map<String, Long> gameResourceTypeToInner(List<GameResourcePair> list);
    
    
    
}
