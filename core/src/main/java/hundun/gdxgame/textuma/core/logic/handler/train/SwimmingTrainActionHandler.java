package hundun.gdxgame.textuma.core.logic.handler.train;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainOutputComponent;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainActionType;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class SwimmingTrainActionHandler extends BaseTrainActionHandler {

    public SwimmingTrainActionHandler(TextUmaGame game) {
        super(game, UserActionId.SWIMMING_TRAIN, TrainActionType.SWIMMING_TRAIN);
        
        
    }

    

}
