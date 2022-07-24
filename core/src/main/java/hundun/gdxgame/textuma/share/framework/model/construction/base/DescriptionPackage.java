package hundun.gdxgame.textuma.share.framework.model.construction.base;

/**
 * @author hundun
 * Created on 2021/11/29
 */
public class DescriptionPackage {
    String outputCostDescriptionStart;
    String outputGainDescriptionStart;
    String upgradeCostDescriptionStart;
    String upgradeMaxLevelDescription = "(max level)";
    String buttonDescroption;
    ILevelDescroptionProvider levelDescroptionProvider;

    public static interface ILevelDescroptionProvider {

        public static ILevelDescroptionProvider EMPTY_IMP = (level, workingLevel, reachMaxLevel) -> "";
        public static ILevelDescroptionProvider ONLY_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
            return "lv." + level;
        };
        public static ILevelDescroptionProvider WORKING_LEVEL_IMP = (level, workingLevel, reachMaxLevel) -> {
            return workingLevel + "/" + level + (reachMaxLevel ? "(max)" : "");
        };
        public static ILevelDescroptionProvider LOCK_IMP = (level, workingLevel, reachMaxLevel) -> {
            return (reachMaxLevel ? "Unlocked" : "");
        };

        String provide(int level, int workingLevel, boolean reachMaxLevel);
    }

    // ------ replace-lombok ------
    public DescriptionPackage(String outputCostDescriptionStart, String outputGainDescriptionStart,
            String upgradeCostDescriptionStart, String buttonDescroption, ILevelDescroptionProvider levelDescroptionProvider) {
        super();
        this.outputCostDescriptionStart = outputCostDescriptionStart;
        this.outputGainDescriptionStart = outputGainDescriptionStart;
        this.upgradeCostDescriptionStart = upgradeCostDescriptionStart;
        this.buttonDescroption = buttonDescroption;
        this.levelDescroptionProvider = levelDescroptionProvider;
    }
    public String getOutputCostDescriptionStart() {
        return outputCostDescriptionStart;
    }
    public void setOutputCostDescriptionStart(String outputCostDescriptionStart) {
        this.outputCostDescriptionStart = outputCostDescriptionStart;
    }
    public String getOutputGainDescriptionStart() {
        return outputGainDescriptionStart;
    }
    public void setOutputGainDescriptionStart(String outputGainDescriptionStart) {
        this.outputGainDescriptionStart = outputGainDescriptionStart;
    }
    public String getUpgradeCostDescriptionStart() {
        return upgradeCostDescriptionStart;
    }
    public void setUpgradeCostDescriptionStart(String upgradeCostDescriptionStart) {
        this.upgradeCostDescriptionStart = upgradeCostDescriptionStart;
    }
    public String getButtonDescroption() {
        return buttonDescroption;
    }
    public void setButtonDescroption(String buttonDescroption) {
        this.buttonDescroption = buttonDescroption;
    }
    public ILevelDescroptionProvider getLevelDescroptionProvider() {
        return levelDescroptionProvider;
    }
    public void setLevelDescroptionProvider(ILevelDescroptionProvider levelDescroptionProvider) {
        this.levelDescroptionProvider = levelDescroptionProvider;
    }
    public String getUpgradeMaxLevelDescription() {
        return upgradeMaxLevelDescription;
    }
    public void setUpgradeMaxLevelDescription(String upgradeMaxLevelDescription) {
        this.upgradeMaxLevelDescription = upgradeMaxLevelDescription;
    }


}
