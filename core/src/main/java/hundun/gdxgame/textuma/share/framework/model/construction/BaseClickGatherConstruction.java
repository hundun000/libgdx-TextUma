package hundun.gdxgame.textuma.share.framework.model.construction;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.BaseConstruction;


/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseClickGatherConstruction extends BaseConstruction {

    public BaseClickGatherConstruction(BaseIdleGame game, String id) {
        super(game, id);
    }

    @Override
    public void onClick() {
        if (!canClickEffect()) {
            return;
        }
        doGather();
    }
    
    private void doGather() {
        if (outputComponent.hasCost()) {
            game.getModelContext().getStorageManager().modifyAllResourceNum(outputComponent.getOutputCostPack().getModifiedValues(), false);
        }
        game.getModelContext().getStorageManager().modifyAllResourceNum(outputComponent.getOutputGainPack().getModifiedValues(), true);
    }

    @Override
    public boolean canClickEffect() {
        return canOutput();
    }


    @Override
    public void onLogicFrame() {
        // do nothing
    }

    @Override
    protected long calculateModifiedOutput(long baseValue, int level) {
        return baseValue;
    }

    @Override
    protected long calculateModifiedOutputCost(long baseValue, int level) {
        return baseValue;
    }


}
