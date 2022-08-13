package hundun.gdxgame.textuma.share.framework.data;

import java.util.Map;
import java.util.Set;

import hundun.gdxgame.textuma.core.data.UmaSaveData;
import lombok.Getter;

/**
 * @author hundun
 * Created on 2021/11/09
 */
public class RootSaveData {
    @Getter
    Map<String, Long> ownResoueces;
    Set<String> unlockedResourceTypes;
    Map<String, Integer> buffAmounts;
    Map<String, UmaUserActionHandlerSaveData> constructionSaveDataMap;
    Set<String> unlockedAchievementNames;
    UmaSaveData umaSaveData;
    
    // ------ replace-lombok ------
//    public Map<String, Long> getOwnResoueces() {
//        return ownResoueces;
//    }
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
    public Map<String, UmaUserActionHandlerSaveData> getConstructionSaveDataMap() {
        return constructionSaveDataMap;
    }
    public void setConstructionSaveDataMap(Map<String, UmaUserActionHandlerSaveData> constructionSaveDataMap) {
        this.constructionSaveDataMap = constructionSaveDataMap;
    }
    public Set<String> getUnlockedAchievementNames() {
        return unlockedAchievementNames;
    }
    public void setUnlockedAchievementNames(Set<String> unlockedAchievementNames) {
        this.unlockedAchievementNames = unlockedAchievementNames;
    }
    public UmaSaveData getUmaSaveData() {
        return umaSaveData;
    }
    public void setUmaSaveData(UmaSaveData umaSaveData) {
        this.umaSaveData = umaSaveData;
    }

}
