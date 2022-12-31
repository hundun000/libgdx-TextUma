package hundun.gdxgame.textuma.share.framework.data;

import java.util.Map;
import java.util.Set;

import hundun.gdxgame.textuma.core.logic.manager.LibgdxFrontEndSaveData;
import hundun.simulationgame.umamusume.gameplay.AccountSaveData;
import hundun.simulationgame.umamusume.gameplay.GameRuleData;
import lombok.Data;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/11/09
 */
@Data
public class RootSaveData {
    
    
    Set<String> unlockedResourceTypes;
    Map<String, Integer> buffAmounts;
    Map<String, UmaUserActionHandlerSaveData> constructionSaveDataMap;
    Set<String> unlockedAchievementNames;
    Map<String, AccountSaveData> umaSaveData;
    GameRuleData gameRuleData;
    LibgdxFrontEndSaveData frontEndSaveData;

}
