package hundun.gdxgame.textuma.share.framework.model;

import hundun.gdxgame.textuma.share.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.textuma.share.framework.model.manager.AchievementManager;
import hundun.gdxgame.textuma.share.framework.model.manager.BuffManager;
import hundun.gdxgame.textuma.share.framework.model.manager.ConstructionManager;
import hundun.gdxgame.textuma.share.framework.model.manager.GameEntityManager;
import hundun.gdxgame.textuma.share.framework.model.manager.StorageManager;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class ModelContext {
    StorageManager storageManager;
    BuffManager buffManager;
    AchievementManager achievementManager;

    BaseConstructionFactory constructionFactory;
    ConstructionManager constructionManager;
    GameEntityManager gameEntityManager;

    // ------ replace-lombok ------
    public StorageManager getStorageManager() {
        return storageManager;
    }
    public void setStorageManager(StorageManager storageManager) {
        this.storageManager = storageManager;
    }
    public BuffManager getBuffManager() {
        return buffManager;
    }
    public void setBuffManager(BuffManager buffManager) {
        this.buffManager = buffManager;
    }
    public AchievementManager getAchievementManager() {
        return achievementManager;
    }
    public void setAchievementManager(AchievementManager achievementManager) {
        this.achievementManager = achievementManager;
    }
    public BaseConstructionFactory getConstructionFactory() {
        return constructionFactory;
    }
    public void setConstructionFactory(BaseConstructionFactory constructionFactory) {
        this.constructionFactory = constructionFactory;
    }
    public ConstructionManager getConstructionManager() {
        return constructionManager;
    }
    public void setConstructionManager(ConstructionManager constructionManager) {
        this.constructionManager = constructionManager;
    }
    public GameEntityManager getGameEntityManager() {
        return gameEntityManager;
    }
    public void setGameEntityManager(GameEntityManager gameEntityManager) {
        this.gameEntityManager = gameEntityManager;
    }


}
