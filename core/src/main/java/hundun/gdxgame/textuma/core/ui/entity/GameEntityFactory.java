package hundun.gdxgame.textuma.core.ui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.model.entity.BaseGameEntityFactory;
import hundun.gdxgame.textuma.share.framework.model.entity.GameEntity;
import hundun.gdxgame.textuma.share.framework.model.entity.RandomMoveEntity;
import hundun.gdxgame.textuma.share.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.StorageInfoBoard;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/11/26
 */
public class GameEntityFactory extends BaseGameEntityFactory {
    
    private static final int HIDEN_FRAME_RANGE = 5;

    public float FLY_UNION_SPEED = 2;

    public float RESOURCE_MAX_DRAW_NUM = 15;
    
    public GameEntityFactory(PlayScreenLayoutConst layoutConst, BasePlayScreen<TextUmaGame> parent) {
        super(layoutConst, parent);
    }
    
    public GameEntity newConstructionEntity(String id, int index) {
        switch (id) {
            case UserActionId.START_RACE: 
                return null;
            case UserActionId.RUNNING_TRAIN:
                return this.rowStableConstructionEntity(id, index, 1);
            case UserActionId.COOKIE_SELLER:
                return this.rowStableConstructionEntity(id, index, 0); 
            default:
                // no need GameEntity
                return null;
        }
        
    }




    @Override
    public int calculateResourceDrawNum(String resourceId, long logicAmount) {
        return (int) Math.min(RESOURCE_MAX_DRAW_NUM, logicAmount);
    }

    @Override
    public int calculateConstructionDrawNum(String constructionid, long logicAmount, int max) {
        return (int) Math.min(max, logicAmount);
    }

    @Override
    public GameEntity newResourceEntity(String resourceId, int index) {
        switch (resourceId) {
//            case ResourceType.COOKIE:
//                return this.failingResourcEntity(resourceId, layoutConst.EXPECTED_DRAW_MIN_X, layoutConst.EXPECTED_DRAW_MAX_X, layoutConst.EXPECTED_DRAW_MAX_Y, layoutConst.EXPECTED_DRAW_MIN_Y, FLY_UNION_SPEED, FLY_UNION_SPEED * 0.2, HIDEN_FRAME_RANGE);
                //return this.randomMoveResourcEntity(resourceId, layoutConst.EXPECTED_DRAW_MIN_X, layoutConst.EXPECTED_DRAW_MAX_X, layoutConst.EXPECTED_DRAW_MIN_Y, layoutConst.EXPECTED_DRAW_MAX_Y, FLY_UNION_SPEED);
            default:
                return null;
        }
    }


    
    
}
