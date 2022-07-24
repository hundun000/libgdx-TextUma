package hundun.gdxgame.textuma.share.framework.model.construction.base;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.Random;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.data.UmaUserActionHandlerSaveData;
import hundun.gdxgame.textuma.share.framework.listener.IBuffChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.construction.base.DescriptionPackage.ILevelDescroptionProvider;

public abstract class UmaActionHandler {


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
    protected UmaUserActionHandlerSaveData saveData;
    // ------ replace-lombok ------
    public UmaUserActionHandlerSaveData getSaveData() {
        return saveData;
    }
    public void setSaveData(UmaUserActionHandlerSaveData saveData) {
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


    public UmaActionHandler(BaseIdleGame game, String id) {
        this.game = game;
        this.saveData = new UmaUserActionHandlerSaveData();
        this.id = id;
        this.name = game.getGameDictionary().constructionIdToShowName(this.getId());
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



    protected void printDebugInfoAfterConstructed() {
        // default do nothing
    }


    public String getSaveDataKey() {
        return id;
    }
    public abstract String getSecondDescroption();
    public abstract void updateModifiedValues();


}
