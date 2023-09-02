package hundun.gdxgame.textuma.core.logic.handler.train;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.BuiltinConstructionsLoader;

import hundun.gdxgame.textuma.share.framework.listener.IGameStartListener;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainOutputComponent;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainActionType;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainRuleConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData.OperationBoardState;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public abstract class BaseTrainActionHandler extends UmaActionHandler implements IGameStartListener {

    protected final TrainActionType trainActionType;
    public BaseTrainActionHandler(TextUmaGame game, String userActionId, TrainActionType trainActionType) {
        super(game, userActionId);
        this.trainActionType = trainActionType;
        
        this.descriptionPackage = UmaActionHandler.TRAIN_DESCRIPTION_PACKAGE;
        
        TrainOutputComponent outputComponent = new TrainOutputComponent(this);
        this.setOutputComponent(outputComponent);
    }

    
    protected void quickSetTrainOutputComponent(
            int costCoinNum,
            int gain
            ) {
        
    }
    
    @Override
    public void onGameStart() {
        // ----- lazy ------
        TrainRuleConfig trainRuleConfig = game.getManagerContext().getGameplayUIController().getTrainOutputComponentConfig(trainActionType);
        outputComponent.setOutputCostPack(BuiltinConstructionsLoader.toPack(
                game.getManagerContext().getGameplayUIController().gameResourceTypeToInner(trainRuleConfig.getCostList())
                ));
        outputComponent.setOutputGainPack(BuiltinConstructionsLoader.toPack(
                game.getManagerContext().getGameplayUIController().gameResourceTypeToInner(trainRuleConfig.getGainList())
                ));
        
        this.lazyInitDescription();
        this.updateModifiedValues();
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
        
        game.getManagerContext().getGameplayUIController().trainAndNextDay(
                trainActionType
                );
        
    }

    @Override
    public ClickEffectType canClickEffect() {
        if (game.getManagerContext().getGameplayUIController().getOperationBoardState() != OperationBoardState.TRAIN_DAY) {
            return ClickEffectType.CANNOT_BY_STATE;
        } else if (!outputComponent.canOutput()) {
            return ClickEffectType.CANNOT_BY_COST;
        }
        return ClickEffectType.CAN;
    }
    
    @Override
    public String getWorkingLevelDescroption() {
        return "";
    }




//    public long calculateModifiedOutput(long baseValue, int level) {
//        return (long) (baseValue * (1 + 0.2 * level));
//    }
//
//    public long calculateModifiedOutputCost(long baseValue, int level) {
//        return (long) (baseValue * (1 + 0.2 * level));
//    }


//    public ILevelDescroptionProvider getLevelDescroptionProvider() {
//        return levelDescroptionProvider;
//    }

}
