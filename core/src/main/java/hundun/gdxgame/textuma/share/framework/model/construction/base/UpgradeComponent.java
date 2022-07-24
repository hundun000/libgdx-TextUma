package hundun.gdxgame.textuma.share.framework.model.construction.base;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePack;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;

/**
 * @author hundun
 * Created on 2021/12/17
 */
public class UpgradeComponent {
    private final BaseConstruction construction;

    private ResourcePack upgradeCostPack;
    // ------ replace-lombok ------
    public ResourcePack getUpgradeCostPack() {
        return upgradeCostPack;
    }
    public void setUpgradeCostPack(ResourcePack upgradeCostPack) {
        this.upgradeCostPack = upgradeCostPack;
    }
    
    public static enum UpgradeState {
        NO_UPGRADE,
        HAS_NEXT_UPGRADE,
        REACHED_MAX_UPGRADE,
        ;
    }
    private UpgradeState upgradeState;
    // ------ replace-lombok ------
    public UpgradeState getUpgradeState() {
        return upgradeState;
    }
    
    /**
     * 影响升级后下一级费用，详见具体公式
     */
    private static final double upgradeCostLevelUpArg = 1.05;
    private static final BiFunction<Long, Integer, Long> DEFAULT_CALCULATE_COST_FUNCTION = (baseValue, level) -> {
        return (long)(
                baseValue
                * (1 + 1 * level)
                * Math.pow(upgradeCostLevelUpArg, level)
                );
    };
    private BiFunction<Long, Integer, Long> calculateCostFunction = DEFAULT_CALCULATE_COST_FUNCTION;
    // ------ replace-lombok ------
    public BiFunction<Long, Integer, Long> getCalculateCostFunction() {
        return calculateCostFunction;
    }
    public void setCalculateCostFunction(BiFunction<Long, Integer, Long> calculateCostFunction) {
        this.calculateCostFunction = calculateCostFunction;
    }
    
    public UpgradeComponent(BaseConstruction construction) {
        super();
        this.construction = construction;
        // default value
        upgradeState = UpgradeState.NO_UPGRADE;
    }

    public void lazyInitDescription() {
        if (upgradeCostPack != null) {
            upgradeState = UpgradeState.HAS_NEXT_UPGRADE;
            upgradeCostPack.setDescriptionStart(construction.descriptionPackage.getUpgradeCostDescriptionStart());
        }
    }

    public void updateModifiedValues(boolean reachMaxLevel) {
        if (upgradeCostPack != null) {
            if (reachMaxLevel) {
                upgradeState = UpgradeState.REACHED_MAX_UPGRADE;
                this.upgradeCostPack.setModifiedValues(null);
                this.upgradeCostPack.setModifiedValuesDescription(null);
            } else {
                this.upgradeCostPack.setModifiedValues(
                        upgradeCostPack.getBaseValues().stream()
                            .map(pair -> {
                                    long newAmout = calculateCostFunction.apply(pair.getAmount(), construction.saveData.getLevel());
                                    return new ResourcePair(pair.getType(), newAmout);
                                })
                            .collect(Collectors.toList())
                );
                this.upgradeCostPack.setModifiedValuesDescription(
                        upgradeCostPack.getModifiedValues().stream()
                        .map(pair -> pair.getType() + "x" + pair.getAmount())
                        .collect(Collectors.joining(", "))
                        + "; "
                );
            }
        }
    }

    protected boolean canUpgrade() {
        if (construction.saveData.getLevel() >= construction.maxLevel || upgradeCostPack == null) {
            return false;
        }

        List<ResourcePair> compareTarget = upgradeCostPack.getModifiedValues();
        return construction.game.getModelContext().getStorageManager().isEnough(compareTarget);
    }

}
