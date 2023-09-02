package hundun.gdxgame.textuma.share.framework.model.construction.base;

import java.util.List;
import java.util.stream.Collectors;

import hundun.gdxgame.textuma.core.logic.handler.train.BaseTrainActionHandler;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePack;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.simulationgame.umamusume.game.gameplay.data.TrainRuleConfig;
import lombok.Getter;
import lombok.Setter;


/**
 * @author hundun
 * Created on 2021/12/17
 */
public class TrainOutputComponent {
    private final BaseTrainActionHandler construction;

    /**
     * 对于Click型，即为基础点击收益；对于Auto型，即为基础自动收益；
     */
    @Getter
    @Setter
    protected ResourcePack outputGainPack;

    /**
     * output行为所需要支付的费用; 无费用时为null
     */
    @Getter
    @Setter
    protected ResourcePack outputCostPack;


    public TrainOutputComponent(BaseTrainActionHandler construction) {
        this.construction = construction;
    }

    public void lazyInitDescription() {
        if (outputCostPack != null) {
            outputCostPack.setDescriptionStart(construction.descriptionPackage.getOutputCostDescriptionStart());
        }
        if (outputGainPack != null) {
            outputGainPack.setDescriptionStart(construction.descriptionPackage.getOutputGainDescriptionStart());
        }
    }

    public void updateModifiedValues() {
     // --------------
        if (outputGainPack != null) {
            outputGainPack.setModifiedValues(
                    outputGainPack.getBaseValues()
            );
            this.outputGainPack.setModifiedValuesDescription(
                    outputGainPack.getModifiedValues().stream()
                    .map(pair -> {
                        return construction.game.getGameDictionary().resourceIdToShowName(pair.getType()) 
                                + "x" 
                                + pair.getAmount();
                    })
                    .collect(Collectors.joining(", "))
                    + "; "
            );
        }
        // --------------
        if (outputCostPack != null) {
            outputCostPack.setModifiedValues(
                    outputCostPack.getBaseValues()
            );
            this.outputCostPack.setModifiedValuesDescription(
                    outputCostPack.getModifiedValues().stream()
                    .map(pair -> {
                        return construction.game.getGameDictionary().resourceIdToShowName(pair.getType()) 
                                + "x" 
                                + pair.getAmount();
                    })
                    .collect(Collectors.joining(", "))
                    + "; "
            );
        }
    }

    public boolean hasCost() {
        return outputCostPack != null;
    }

    public boolean canOutput() {
        if (!hasCost()) {
            return true;
        }

        List<ResourcePair> compareTarget = outputCostPack.getModifiedValues();
        return construction.getGame().getManagerContext().getStorageManager().isEnough(compareTarget);
    }
}
