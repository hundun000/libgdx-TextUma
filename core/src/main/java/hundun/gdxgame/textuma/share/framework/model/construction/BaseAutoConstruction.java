package hundun.gdxgame.textuma.share.framework.model.construction;

import java.util.List;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;

/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BaseAutoConstruction extends BaseConstruction {

    protected int autoOutputProgress = 0;


    public BaseAutoConstruction(BaseIdleGame game, String id
            ) {
        super(game, id);
    }

    @Override
    protected void printDebugInfoAfterConstructed() {
        super.printDebugInfoAfterConstructed();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < maxLevel; i++) {
            builder.append(i).append("->").append(upgradeComponent.getCalculateCostFunction().apply(1L, i)).append(",");
        }
        Gdx.app.log(this.id, "getUpgradeCost=[" + builder.toString() + "]");
    }


    @Override
    public void onClick() {
        if (!canClickEffect()) {
            return;
        }
        doUpgrade();
    }

    private void doUpgrade() {
        List<ResourcePair> upgradeCostRule = upgradeComponent.getUpgradeCostPack().getModifiedValues();
        game.getModelContext().getStorageManager().modifyAllResourceNum(upgradeCostRule, false);
        saveData.setLevel(saveData.getLevel() + 1);
        if (!levelComponent.isWorkingLevelChangable()) {
            saveData.setWorkingLevel(saveData.getLevel());
        }
        updateModifiedValues();
    }
    
    @Override
    public boolean canClickEffect() {
        return canUpgrade();
    }


    @Override
    public void onLogicFrame() {
        autoOutputProgress++;
        int logicFrameCountMax = outputComponent.getAutoOutputSecondCountMax() * BasePlayScreen.LOGIC_FRAME_PER_SECOND;
        if (autoOutputProgress >= logicFrameCountMax) {
            autoOutputProgress = 0;
            tryAutoOutputOnce();
        }
    }

    private void tryAutoOutputOnce() {
        if (!canOutput()) {
            Gdx.app.log(this.id, "canOutput");
            return;
        }
        Gdx.app.log(this.id, "AutoOutput");
        if (outputComponent.hasCost()) {
            game.getModelContext().getStorageManager().modifyAllResourceNum(outputComponent.getOutputCostPack().getModifiedValues(), false);
        }
        if (outputComponent.getOutputGainPack() != null) {
            game.getModelContext().getStorageManager().modifyAllResourceNum(outputComponent.getOutputGainPack().getModifiedValues(), true);
        }
    }

    @Override
    protected long calculateModifiedOutput(long baseValue, int level) {
        return baseValue * level;
    }

    @Override
    protected long calculateModifiedOutputCost(long baseValue, int level) {
        return baseValue * level;
    }




}
