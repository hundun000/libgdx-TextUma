package hundun.gdxgame.textuma.share.starter.ui.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;

/**
 * @author hundun
 * Created on 2021/11/25
 */
public class ResourceAmountPairNode<T_GAME extends BaseHundunGame> extends BaseAmountPairNode<T_GAME> {

    

    String resourceType;
    // ------ replace-lombok ------
    public String getResourceType() {
        return resourceType;
    }

    public ResourceAmountPairNode(T_GAME game, String resourceType) {
        super(game);
        this.resourceType = resourceType;
        String textureRegion = game.getGameDictionary().resourceIdToShowName(resourceType);
        lazyInit(textureRegion);
    }
    
    @Override
    public BaseAmountPairNode<T_GAME> update(long amout) {
        return super.update(game.getTextFormatTool().format(amout));
    }

}
