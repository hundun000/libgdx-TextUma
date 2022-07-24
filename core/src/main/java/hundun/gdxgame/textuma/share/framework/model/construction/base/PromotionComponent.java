package hundun.gdxgame.textuma.share.framework.model.construction.base;

import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePack;

/**
 * @author hundun
 * Created on 2021/12/17
 */
public class PromotionComponent {

    public enum ConditionMeetStatus {
        MEET_NONE,
        MEET_LEVEL,
        MEET_ALL,
        ;
    }

    private final BaseConstruction construction;

    private int levelCondition;
    // ------ replace-lombok ------
    public void setLevelCondition(int levelCondition) {
        this.levelCondition = levelCondition;
    }

    private ResourcePack promotionCostPack;
    // ------ replace-lombok ------
    public void setPromotionCostPack(ResourcePack promotionCostPack) {
        this.promotionCostPack = promotionCostPack;
    }

    public PromotionComponent(BaseConstruction parent) {
        super();
        this.construction = parent;
    }

    public ConditionMeetStatus canPromotion() {
        boolean meetLevel = isMeetLevel();
        boolean meetResource = isMeetResource();

        int code = 0;
        int pos = 0;
        code += (meetLevel ? 1 : 0) * 10^(pos++);
        code += (meetResource ? 1 : 0) * 10^(pos++);

        switch (code) {
            case 10:
                return ConditionMeetStatus.MEET_LEVEL;
            case 11:
                return ConditionMeetStatus.MEET_ALL;
            default:
                return ConditionMeetStatus.MEET_NONE;
        }
    }

    public boolean isMeetLevel() {
        return construction.getSaveData().getLevel() >= levelCondition;
    }

    public boolean isMeetResource() {
        return construction.game.getModelContext().getStorageManager().isEnough(promotionCostPack.getModifiedValues());
    }

}
