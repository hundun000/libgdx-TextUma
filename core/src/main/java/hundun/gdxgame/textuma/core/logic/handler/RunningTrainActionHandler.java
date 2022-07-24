package hundun.gdxgame.textuma.core.logic.handler;

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
public class RunningTrainActionHandler extends BaseTrainActionHandler {

    public RunningTrainActionHandler(TextUmaGame game) {
        super(game, UserActionId.RUNNING_TRAIN);
        
        this.detailDescroptionConstPart = "Details of RunningTrain";
        this.descriptionPackage = UmaActionHandler.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
    
        TrainOutputComponent outputComponent = new TrainOutputComponent(this);
        outputComponent.setOutputCostPack(BuiltinConstructionsLoader.toPack(JavaHighVersionFeature.mapOf(
                ResourceType.COIN, 1
                )));
        outputComponent.setOutputGainPack(BuiltinConstructionsLoader.toPack(JavaHighVersionFeature.mapOf(
                ResourceType.HORSE_SPEED, 1
                )));
        this.setOutputComponent(outputComponent);
        
        TrainLevelComponent levelComponent = new TrainLevelComponent(this);
        this.setLevelComponent(levelComponent);
        
        this.lazyInitDescription();
        
        
    }

    @Override
    public long calculateModifiedOutput(long baseValue, int level) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long calculateModifiedOutputCost(long baseValue, int level) {
        // TODO Auto-generated method stub
        return 0;
    }

}