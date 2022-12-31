package hundun.gdxgame.textuma.core.logic.manager;

import hundun.simulationgame.umamusume.gameplay.AccountSaveData;
import hundun.simulationgame.umamusume.gameplay.GameRuleData;
import lombok.Data;

/**
 * @author hundun
 * Created on 2023/01/03
 */
@Data
public class LibgdxFrontEndSaveData {
    AccountSaveData umaSaveData;
    GameRuleData gameRuleData;
    
    public int currentRaceRecordNodeIndex;
}
