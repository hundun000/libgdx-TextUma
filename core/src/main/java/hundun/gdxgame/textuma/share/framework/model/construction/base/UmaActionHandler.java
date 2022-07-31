package hundun.gdxgame.textuma.share.framework.model.construction.base;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.Random;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.data.UmaUserActionHandlerSaveData;
import hundun.gdxgame.textuma.share.framework.listener.IBuffChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.construction.base.DescriptionPackage.ILevelDescroptionProvider;

public abstract class UmaActionHandler {


    public static final DescriptionPackage RACE_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, null, null, "DO",
            ILevelDescroptionProvider.EMPTY_IMP);

    public static final DescriptionPackage TRAIN_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, null, null, "DO",
            ILevelDescroptionProvider.ONLY_LEVEL_IMP);

    public static final DescriptionPackage RECORD_DESCRIPTION_PACKAGE = new DescriptionPackage(
            null, null, null, "DO",
            ILevelDescroptionProvider.WORKING_LEVEL_IMP);

    protected Random random = new Random();
    protected final TextUmaGame game;

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

    public DescriptionPackage descriptionPackage;
    // ------ replace-lombok ------
    public DescriptionPackage getDescriptionPackage() {
        return descriptionPackage;
    }


    public UmaActionHandler(TextUmaGame game, String id) {
        this.game = game;
        this.saveData = new UmaUserActionHandlerSaveData();
        this.id = id;
        this.name = game.getGameDictionary().constructionIdToShowName(this.getId());
        game.getEventManager().registerListener(this);
        if(game.debugMode) {
            printDebugInfoAfterConstructed();
        }
    }

    public abstract void onEffectableClick();

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
    public abstract String getWorkingLevelDescroption();
    public abstract void updateModifiedValues();
    

}
