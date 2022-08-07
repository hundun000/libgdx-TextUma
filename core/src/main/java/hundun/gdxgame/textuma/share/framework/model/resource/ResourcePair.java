package hundun.gdxgame.textuma.share.framework.model.resource;

import hundun.simulationgame.umamusume.util.JavaFeatureForGwt;

/**
 * ResourceType and amount
 * @author hundun
 * Created on 2021/12/02
 */
public class ResourcePair {
    String type;
    Long amount;

    // ------ replace-lombok ------
    public ResourcePair() {
    }
    public ResourcePair(String type, Long amount) {
        super();
        this.type = type;
        this.amount = amount;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    @Override
    public String toString() {
        return JavaFeatureForGwt.stringFormat("{type=%s, amount=%s}", type, amount);
    }
    


}
