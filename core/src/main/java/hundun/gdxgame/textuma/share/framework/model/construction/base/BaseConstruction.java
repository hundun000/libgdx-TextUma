package hundun.gdxgame.textuma.share.framework.model.construction.base;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.Random;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.data.ConstructionSaveData;
import hundun.gdxgame.textuma.share.framework.listener.IBuffChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.construction.base.DescriptionPackage.ILevelDescroptionProvider;

public abstract class BaseConstruction implements ILogicFrameListener, IBuffChangeListener {

    protected static final int DEFAULT_MAX_LEVEL = 99;
    protected int maxLevel = DEFAULT_MAX_LEVEL;
    // ------ replace-lombok ------
    public int getMaxLevel() {
        return maxLevel;
    }
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    protected static final int DEFAULT_MAX_DRAW_NUM = 5;
    // ------ replace-lombok ------
    protected int maxDrawNum = DEFAULT_MAX_DRAW_NUM;
    public int getMaxDrawNum() {
        return maxDrawNum;
    }
    public void setMaxDrawNum(int maxDrawNum) {
        this.maxDrawNum = maxDrawNum;
    }

    public static final DescriptionPackage WORKING_LEVEL_AUTO_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "AutoCost", "AutoGain", "UpgradeCost", "Upgrade",
            ILevelDescroptionProvider.WORKING_LEVEL_IMP);

    public static final DescriptionPackage MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "AutoCost", "AutoGain", "UpgradeCost", "Upgrade",
            ILevelDescroptionProvider.ONLY_LEVEL_IMP);

    public static final DescriptionPackage SELLING_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "Sell", "Gain", "UpgradeCost", "Upgrade",
            ILevelDescroptionProvider.WORKING_LEVEL_IMP);


    public static final DescriptionPackage GATHER_DESCRIPTION_PACKAGE = new DescriptionPackage(
            "Pay", "Gain", null, "Gather",
            ILevelDescroptionProvider.EMPTY_IMP);

    public static final DescriptionPackage WIN_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, null, "Pay", "Unlock",
            ILevelDescroptionProvider.LOCK_IMP);

    protected Random random = new Random();
    protected final BaseIdleGame game;

    /**
     * NotNull
     */
    protected ConstructionSaveData saveData;
    // ------ replace-lombok ------
    public ConstructionSaveData getSaveData() {
        return saveData;
    }
    public void setSaveData(ConstructionSaveData saveData) {
        this.saveData = saveData;
    }

    public String name;
    // ------ replace-lombok ------
    public String getName() {
        return name;
    }

    public String id;
    // ------ replace-lombok ------
    public String getId() {
        return id;
    }

    public String detailDescroptionConstPart;
    // ------ replace-lombok ------
    public String getDetailDescroptionConstPart() {
        return detailDescroptionConstPart;
    }

    public DescriptionPackage descriptionPackage;
    // ------ replace-lombok ------
    public DescriptionPackage getDescriptionPackage() {
        return descriptionPackage;
    }

    /**
     * NotNull
     */
    protected UpgradeComponent upgradeComponent;
    // ------ replace-lombok ------
    public UpgradeComponent getUpgradeComponent() {
        return upgradeComponent;
    }
    public void setUpgradeComponent(UpgradeComponent upgradeComponent) {
        this.upgradeComponent = upgradeComponent;
    }

    /**
     * NotNull
     */
    protected OutputComponent outputComponent;
    // ------ replace-lombok ------
    public OutputComponent getOutputComponent() {
        return outputComponent;
    }
    public void setOutputComponent(OutputComponent outputComponent) {
        this.outputComponent = outputComponent;
    }

    /**
     * NotNull
     */
    protected LevelComponent levelComponent;
    // ------ replace-lombok ------
    public LevelComponent getLevelComponent() {
        return levelComponent;
    }
    public void setLevelComponent(LevelComponent levelComponent) {
        this.levelComponent = levelComponent;
    }

    public void lazyInitDescription() {
        outputComponent.lazyInitDescription();
        upgradeComponent.lazyInitDescription();
    }

    public BaseConstruction(BaseIdleGame game, String id) {
        this.game = game;
        this.saveData = new ConstructionSaveData();
        this.id = id;

        game.getEventManager().registerListener(this);
        if(game.debugMode) {
            printDebugInfoAfterConstructed();
        }
    }

    public abstract void onClick();

    public abstract boolean canClickEffect();

    public String getButtonDescroption() {
        return descriptionPackage.getButtonDescroption();
    }

    //protected abstract long calculateModifiedUpgradeCost(long baseValue, int level);
    protected abstract long calculateModifiedOutput(long baseValue, int level);
    protected abstract long calculateModifiedOutputCost(long baseValue, int level);



    /**
     * 重新计算各个数值的加成后的结果
     */
    public void updateModifiedValues() {
        Gdx.app.log(this.name, "updateCurrentCache called");
        // --------------
        boolean reachMaxLevel = this.getSaveData().getLevel() == this.getMaxLevel();
        upgradeComponent.updateModifiedValues(reachMaxLevel);
        outputComponent.updateModifiedValues();

    }

    @Override
    public void onBuffChange() {
        updateModifiedValues();
    }


    protected void printDebugInfoAfterConstructed() {
        // default do nothing
    }

    protected boolean canOutput() {
        return outputComponent.canOutput();
    }


    protected boolean canUpgrade() {
        return upgradeComponent.canUpgrade();
    }

    public String getSaveDataKey() {
        return id;
    }


}
