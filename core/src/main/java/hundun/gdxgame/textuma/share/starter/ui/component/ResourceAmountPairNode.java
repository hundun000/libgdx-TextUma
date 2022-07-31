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
public class ResourceAmountPairNode<T_GAME extends BaseHundunGame> extends HorizontalGroup {

    T_GAME game;

    String resourceType;
    // ------ replace-lombok ------
    public String getResourceType() {
        return resourceType;
    }

    Label image;
    Label label;

    public ResourceAmountPairNode(T_GAME game, String resourceType) {
        super();
        this.game = game;
        this.resourceType = resourceType;
        String textureRegion = game.getGameDictionary().resourceIdToShowName(resourceType);
        this.image = new Label(textureRegion, game.getButtonSkin(), TextUmaGame.GAME_WORD_SKIN_KEY);
        this.addActor(image);
        this.label = new Label("", game.getButtonSkin());
        this.addActor(label);
    }

    public void update(long amout) {
        label.setText(
                game.getTextFormatTool().format(amout)
                );
    }




}
