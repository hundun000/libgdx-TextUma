package hundun.gdxgame.textuma.core.logic.handler.train;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.DescriptionPackage.ILevelDescroptionProvider;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainLevelComponent;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainOutputComponent;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.simulationgame.umamusume.gameplay.AccountSaveData.OperationBoardState;

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
    
    ILevelDescroptionProvider levelDescroptionProvider;
    
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
        
        game.getModelContext().getGameplayFrontend().trainAndNextDay(
                "Train done.",
                outputComponent.getOutputCostPack() != null ?
                        outputComponent.getOutputCostPack().getModifiedValues() : null,
                        outputComponent.getOutputGainPack().getModifiedValues()
                );
        
    }

    @Override
    public ClickEffectType canClickEffect() {
        if (game.getModelContext().getGameplayFrontend().getOperationBoardState() != OperationBoardState.TRAIN_DAY) {
            return ClickEffectType.CANNOT_BY_STATE;
        } else if (!outputComponent.canOutput()) {
            return ClickEffectType.CANNOT_BY_COST;
        }
        return ClickEffectType.CAN;
    }
    
    @Override
    public String getWorkingLevelDescroption() {
        return levelComponent.getWorkingLevelDescroption();
    }




    public long calculateModifiedOutput(long baseValue, int level) {
        return (long) (baseValue * (1 + 0.2 * level));
    }

    public long calculateModifiedOutputCost(long baseValue, int level) {
        return (long) (baseValue * (1 + 0.2 * level));
    }


    public ILevelDescroptionProvider getLevelDescroptionProvider() {
        return levelDescroptionProvider;
    }

}
