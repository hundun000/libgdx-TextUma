package hundun.gdxgame.textuma.share.framework.model;

import hundun.gdxgame.textuma.core.logic.manager.ILibgdxGameplayFrontend;
import hundun.gdxgame.textuma.core.logic.manager.LibgdxGameplayFrontend;
import hundun.gdxgame.textuma.share.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.textuma.share.framework.model.manager.AchievementManager;
import hundun.gdxgame.textuma.share.framework.model.manager.BuffManager;
import hundun.gdxgame.textuma.share.framework.model.manager.ConstructionManager;
import hundun.gdxgame.textuma.share.framework.model.manager.GameEntityManager;
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
    GameEntityManager gameEntityManager;
    ILibgdxGameplayFrontend gameplayFrontend;

}
