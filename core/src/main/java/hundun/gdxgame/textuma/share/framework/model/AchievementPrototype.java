package hundun.gdxgame.textuma.share.framework.model;

import java.util.Map;

/**
 * @author hundun
 * Created on 2021/11/12
 */
public class AchievementPrototype {
    String name;
    String description;
    Map<String, Integer> requiredBuffs;
    Map<String, Integer> requiredResources;

    // ------ replace-lombok ------
    public AchievementPrototype() {
    }
    public AchievementPrototype(String name, String description, Map<String, Integer> requiredBuffs,
            Map<String, Integer> requiredResources) {
        super();
        this.name = name;
        this.description = description;
        this.requiredBuffs = requiredBuffs;
        this.requiredResources = requiredResources;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Map<String, Integer> getRequiredBuffs() {
        return requiredBuffs;
    }
    public void setRequiredBuffs(Map<String, Integer> requiredBuffs) {
        this.requiredBuffs = requiredBuffs;
    }
    public Map<String, Integer> getRequiredResources() {
        return requiredResources;
    }
    public void setRequiredResources(Map<String, Integer> requiredResources) {
        this.requiredResources = requiredResources;
    }

}
