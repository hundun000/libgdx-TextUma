package hundun.gdxgame.textuma.share.framework.data;

import java.util.Map;
import java.util.Set;

/**
 * @author hundun
 * Created on 2021/11/09
 */
public class SaveData {
    Map<String, Long> ownResoueces;
    Set<String> unlockedResourceTypes;
    Map<String, Integer> buffAmounts;
    Map<String, ConstructionSaveData> constructionSaveDataMap;
    Set<String> unlockedAchievementNames;

    // ------ replace-lombok ------
    public Map<String, Long> getOwnResoueces() {
        return ownResoueces;
    }
    public void setOwnResoueces(Map<String, Long> ownResoueces) {
        this.ownResoueces = ownResoueces;
    }
    public Set<String> getUnlockedResourceTypes() {
        return unlockedResourceTypes;
    }
    public void setUnlockedResourceTypes(Set<String> unlockedResourceTypes) {
        this.unlockedResourceTypes = unlockedResourceTypes;
    }
    public Map<String, Integer> getBuffAmounts() {
        return buffAmounts;
    }
    public void setBuffAmounts(Map<String, Integer> buffAmounts) {
        this.buffAmounts = buffAmounts;
    }
    public Map<String, ConstructionSaveData> getConstructionSaveDataMap() {
        return constructionSaveDataMap;
    }
    public void setConstructionSaveDataMap(Map<String, ConstructionSaveData> constructionSaveDataMap) {
        this.constructionSaveDataMap = constructionSaveDataMap;
    }
    public Set<String> getUnlockedAchievementNames() {
        return unlockedAchievementNames;
    }
    public void setUnlockedAchievementNames(Set<String> unlockedAchievementNames) {
        this.unlockedAchievementNames = unlockedAchievementNames;
    }


}
