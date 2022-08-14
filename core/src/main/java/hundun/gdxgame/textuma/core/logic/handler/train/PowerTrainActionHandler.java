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
public class PowerTrainActionHandler extends BaseTrainActionHandler {

    public PowerTrainActionHandler(TextUmaGame game) {
        super(game, UserActionId.POWER_TRAIN);
        
        this.descriptionPackage = UmaActionHandler.TRAIN_DESCRIPTION_PACKAGE;
    
        TrainOutputComponent outputComponent = new TrainOutputComponent(this);
        outputComponent.setOutputCostPack(BuiltinConstructionsLoader.toPack(JavaHighVersionFeature.mapOf(
                ResourceType.COIN, 100
                )));
        outputComponent.setOutputGainPack(BuiltinConstructionsLoader.toPack(JavaHighVersionFeature.mapOf(
                ResourceType.HORSE_SPEED, 5,
                ResourceType.HORSE_STAMINA, 5,
                ResourceType.HORSE_POWER, 25
                )));
        this.setOutputComponent(outputComponent);
        
        TrainLevelComponent levelComponent = new TrainLevelComponent(this);
        this.setLevelComponent(levelComponent);
        
        this.lazyInitDescription();
        
        
    }

    

}