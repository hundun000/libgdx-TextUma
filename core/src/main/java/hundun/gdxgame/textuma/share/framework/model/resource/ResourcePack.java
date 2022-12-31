package hundun.gdxgame.textuma.share.framework.model.resource;

import java.util.List;

import lombok.Data;

/**
 * @author hundun
 * Created on 2021/12/02
 */
@Data
public class ResourcePack {
    String descriptionStart;
    List<ResourcePair> baseValues;
    List<ResourcePair> modifiedValues;
    String modifiedValuesDescription;
}
