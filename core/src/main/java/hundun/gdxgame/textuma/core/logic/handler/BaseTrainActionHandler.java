package hundun.gdxgame.textuma.core.logic.handler;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.core.logic.manager.UmaManager.UmaState;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainLevelComponent;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainOutputComponent;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public abstract class BaseTrainActionHandler extends UmaActionHandler {

    public BaseTrainActionHandler(TextUmaGame game, String userActionId) {
        super(game, userActionId);
    }

    
    protected void quickSetTrainOutputComponent(
            int costCoinNum,
            int gain
            ) {
        
    }
    
    
    /**
     * NotNull
     */
    protected TrainOutputComponent outputComponent;
    // ------ replace-lombok ------
    public TrainOutputComponent getOutputComponent() {
        return outputComponent;
    }
    public void setOutputComponent(TrainOutputComponent outputComponent) {
        this.outputComponent = outputComponent;
    }
    
    /**
     * NotNull
     */
    protected TrainLevelComponent levelComponent;
    // ------ replace-lombok ------
    public TrainLevelComponent getLevelComponent() {
        return levelComponent;
    }
    public void setLevelComponent(TrainLevelComponent levelComponent) {
        this.levelComponent = levelComponent;
    }

    public void lazyInitDescription() {
        outputComponent.lazyInitDescription();
    }
    
    public abstract long calculateModifiedOutput(long baseValue, int level);
    public abstract long calculateModifiedOutputCost(long baseValue, int level);
    
    /**
     * 重新计算各个数值的加成后的结果
     */
    @Override
    public void updateModifiedValues() {
        Gdx.app.log(this.name, "updateCurrentCache called");
        // --------------
//        boolean reachMaxLevel = this.getSaveData().getLevel() == this.getMaxLevel();
//        upgradeComponent.updateModifiedValues(reachMaxLevel);
        outputComponent.updateModifiedValues();

    }

    @Override
    public void onEffectableClick() {
        
        game.getModelContext().getUmaManager().trainAndNextDay(
                outputComponent.getOutputCostPack().getModifiedValues(),
                outputComponent.getOutputGainPack().getModifiedValues()
                );
        Gdx.app.log(this.name, "train done");
    }

    @Override
    public boolean canClickEffect() {
        return game.getModelContext().getUmaManager().getState() == UmaState.TRAIN_DAY;
    }
    
    @Override
    public String getWorkingLevelDescroption() {
        return levelComponent.getWorkingLevelDescroption();
    }


    @Override
    public String getPopupInfo() {
        return String.format("Train cost: %s, gain: %s",
                outputComponent.getOutputCostPack().getModifiedValuesDescription(),
                outputComponent.getOutputGainPack().getModifiedValuesDescription()
                );
    }


}
