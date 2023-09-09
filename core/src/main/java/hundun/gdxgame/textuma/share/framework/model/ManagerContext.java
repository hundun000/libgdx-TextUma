package hundun.gdxgame.textuma.share.framework.model;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.manager.IGameplayUIController;
import hundun.gdxgame.textuma.core.logic.manager.TextUmaGameplayUIController;
import hundun.gdxgame.textuma.share.framework.data.ChildGameConfig;
import hundun.gdxgame.textuma.share.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.textuma.share.framework.model.manager.AchievementManager;
import hundun.gdxgame.textuma.share.framework.model.manager.BuffManager;
import hundun.gdxgame.textuma.share.framework.model.manager.ConstructionManager;
import hundun.gdxgame.textuma.share.framework.model.manager.StorageManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@Getter
@Setter
public class ManagerContext {
    StorageManager storageManager;
    BuffManager buffManager;
    AchievementManager achievementManager;

    BaseConstructionFactory constructionFactory;
    ConstructionManager constructionManager;


    TextUmaGame game;
    public ManagerContext(TextUmaGame game) {
        this.game = game;
    }
    public void lazyInitOnGameCreate(ChildGameConfig childGameConfig) {
        this.setStorageManager(new StorageManager(game));
        this.setBuffManager(new BuffManager(game));
        this.setAchievementManager(new AchievementManager(game));

        this.setConstructionFactory(new BaseConstructionFactory());
        this.setConstructionManager(new ConstructionManager(game));

        this.getConstructionFactory().lazyInit(childGameConfig.getConstructions());
        this.getConstructionManager().lazyInit(childGameConfig.getAreaControlableConstructionIds());
        this.getAchievementManager().lazyInit(childGameConfig.getAchievementPrototypes());
    }
}
