package hundun.gdxgame.textuma.core.logic.manager;
/**
 * @author hundun
 * Created on 2022/08/10
 */
public interface IUserRaceActionHandlerCore {
    void raceStart();
    void smallStepRaceRecordNode();
    void bigStepRaceRecordNode();
    boolean waitingNextRaceRecordNode();
    boolean waitingEndRaceRecord();
    void endRaceRecord();
    void replayRaceRecord();
    
}
