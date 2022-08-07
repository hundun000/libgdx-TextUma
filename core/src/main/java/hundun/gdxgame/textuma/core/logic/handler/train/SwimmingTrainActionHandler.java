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
public class SwimmingTrainActionHandler extends BaseTrainActionHandler {

    public SwimmingTrainActionHandler(TextUmaGame game) {
        super(game, UserActionId.SWIMMING_TRAIN);
        
        this.descriptionPackage = UmaActionHandler.TRAIN_DESCRIPTION_PACKAGE;
    
        TrainOutputComponent outputComponent = new TrainOutputComponent(this);
        outputComponent.setOutputCostPack(BuiltinConstructionsLoader.toPack(JavaHighVersionFeature.mapOf(
                ResourceType.COIN, 100
                )));
        outputComponent.setOutputGainPack(BuiltinConstructionsLoader.toPack(JavaHighVersionFeature.mapOf(
                ResourceType.HORSE_STAMINA, 25,
                ResourceType.HORSE_POWER, 10
                )));
        this.setOutputComponent(outputComponent);
        
        TrainLevelComponent levelComponent = new TrainLevelComponent(this);
        this.setLevelComponent(levelComponent);
        
        this.lazyInitDescription();
        
        
    }

    

}
