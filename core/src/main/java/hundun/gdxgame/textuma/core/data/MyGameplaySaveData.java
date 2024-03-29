package hundun.gdxgame.textuma.core.data;

import java.util.Map;
import java.util.Set;

import hundun.gdxgame.textuma.core.logic.manager.LibgdxFrontEndSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.GameRuleData;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/09
 */
@Data
public class MyGameplaySaveData {
    
    
    Set<String> unlockedResourceTypes;
    Map<String, Integer> buffAmounts;
    Map<String, UmaUserActionHandlerSaveData> constructionSaveDataMap;
    Set<String> unlockedAchievementNames;
    Map<String, AccountSaveData<TextFrameData>> umaSaveData;
    GameRuleData gameRuleData;
    LibgdxFrontEndSaveData frontEndSaveData;

}
