package hundun.gdxgame.textuma.core.logic.handler.train;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainLevelComponent;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainOutputComponent;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.framework.util.JavaHighVersionFeature;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class FreeTrainActionHandler extends BaseTrainActionHandler {

    public FreeTrainActionHandler(TextUmaGame game) {
        super(game, UserActionId.FREE_TRAIN);
        
        this.descriptionPackage = UmaActionHandler.TRAIN_DESCRIPTION_PACKAGE;
    
        TrainOutputComponent outputComponent = new TrainOutputComponent(this);
        outputComponent.setOutputGainPack(BuiltinConstructionsLoader.toPack(JavaHighVersionFeature.mapOf(
                ResourceType.HORSE_SPEED, 5,
                ResourceType.HORSE_STAMINA, 5,
                ResourceType.HORSE_POWER, 5
                )));
        this.setOutputComponent(outputComponent);
        
        TrainLevelComponent levelComponent = new TrainLevelComponent(this);
        this.setLevelComponent(levelComponent);
        
        this.lazyInitDescription();
        
        
    }

    

}
