package hundun.gdxgame.textuma.share.starter;

import java.util.List;

import hundun.gdxgame.textuma.share.framework.model.construction.base.DescriptionPackage;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePack;

/**
 * @author hundun
 * Created on 2021/12/17
 */
public class ConstructionsConfig {

    List<ConstructionNode> nodes;

    public static class ConstructionNode {
        String id;
        ConstructionClazz clazz;
        String detailDescroptionConstPart;
        DescriptionPackage descriptionPackage;
        ResourcePack upgradeCostPack;
        ResourcePack outputGainPack;
        ResourcePack outputCostPack;
        boolean workingLevelChangable;

        // ------ replace-lombok ------
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public ConstructionClazz getClazz() {
            return clazz;
        }
        public void setClazz(ConstructionClazz clazz) {
            this.clazz = clazz;
        }
        public String getDetailDescroptionConstPart() {
            return detailDescroptionConstPart;
        }
        public void setDetailDescroptionConstPart(String detailDescroptionConstPart) {
            this.detailDescroptionConstPart = detailDescroptionConstPart;
        }
        public DescriptionPackage getDescriptionPackage() {
            return descriptionPackage;
        }
        public void setDescriptionPackage(DescriptionPackage descriptionPackage) {
            this.descriptionPackage = descriptionPackage;
        }
        public ResourcePack getUpgradeCostPack() {
            return upgradeCostPack;
        }
        public void setUpgradeCostPack(ResourcePack upgradeCostPack) {
            this.upgradeCostPack = upgradeCostPack;
        }
        public ResourcePack getOutputGainPack() {
            return outputGainPack;
        }
        public void setOutputGainPack(ResourcePack outputGainPack) {
            this.outputGainPack = outputGainPack;
        }
        public ResourcePack getOutputCostPack() {
            return outputCostPack;
        }
        public void setOutputCostPack(ResourcePack outputCostPack) {
            this.outputCostPack = outputCostPack;
        }
        public boolean isWorkingLevelChangable() {
            return workingLevelChangable;
        }
        public void setWorkingLevelChangable(boolean workingLevelChangable) {
            this.workingLevelChangable = workingLevelChangable;
        }


    }


    public static enum ConstructionClazz {
        AUTO,
        CLICK,
        ;
    }


    // ------ replace-lombok ------
    public List<ConstructionNode> getNodes() {
        return nodes;
    }
    public void setNodes(List<ConstructionNode> nodes) {
        this.nodes = nodes;
    }


}
