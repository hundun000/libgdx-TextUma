package hundun.gdxgame.textuma.share.starter.ui.component;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;


/**
 * @author hundun
 * Created on 2021/12/06
 */
public class GameAreaControlNode<T_GAME extends BaseIdleGame> extends Image {

    BasePlayScreen<T_GAME> parent;
    //Image image;
    Label label;
    boolean debugType;
    String gameArea;

    public GameAreaControlNode(BasePlayScreen<T_GAME> parent, String gameArea, boolean longVersion, boolean debugType) {
        this.parent = parent;
        this.debugType = debugType;
        this.gameArea = gameArea;

        rebuildImage(longVersion);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                parent.setAreaAndNotifyChildren(gameArea);
            }
        });

    }

    private Drawable rebuildImage(boolean longVersion) {
        if (!debugType) {
            Drawable drawable = new SpriteDrawable(new Sprite(parent.game.getTextureManager().getGameAreaTexture(gameArea, longVersion)));
            return drawable;
        } else {
            Drawable drawable = BasePlayScreen.createTwoColorBoard(5, 5, 0.8f, longVersion ? 0 : 2);
            return drawable;
        }
    }

    public void changeVersion(boolean longVersion) {
        this.setDrawable(rebuildImage(longVersion));
    }

}
