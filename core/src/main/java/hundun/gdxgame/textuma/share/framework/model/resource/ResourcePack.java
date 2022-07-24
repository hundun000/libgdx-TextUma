package hundun.gdxgame.textuma.share.framework.model.resource;

import java.util.List;

/**
 * @author hundun
 * Created on 2021/12/02
 */
public class ResourcePack {
    String descriptionStart;
    List<ResourcePair> baseValues;
    List<ResourcePair> modifiedValues;
    String modifiedValuesDescription;
    
    // ------ replace-lombok ------
    public String getDescriptionStart() {
        return descriptionStart;
    }
    public void setDescriptionStart(String descriptionStart) {
        this.descriptionStart = descriptionStart;
    }
    public List<ResourcePair> getBaseValues() {
        return baseValues;
    }
    public void setBaseValues(List<ResourcePair> baseValues) {
        this.baseValues = baseValues;
    }
    public List<ResourcePair> getModifiedValues() {
        return modifiedValues;
    }
    public void setModifiedValues(List<ResourcePair> modifiedValues) {
        this.modifiedValues = modifiedValues;
    }
    public String getModifiedValuesDescription() {
        return modifiedValuesDescription;
    }
    public void setModifiedValuesDescription(String modifiedValuesDescription) {
        this.modifiedValuesDescription = modifiedValuesDescription;
    }


}
