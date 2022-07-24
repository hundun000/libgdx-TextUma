package hundun.gdxgame.textuma.share.starter.ui.component;
/**
 * @author hundun
 * Created on 2021/11/29
 */

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;

public class BackgroundImageBox<T_GAME extends BaseIdleGame> extends Container<Image> implements IGameAreaChangeListener{
    BasePlayScreen<T_GAME> parent;
    Map<String, Drawable> imageMap = new HashMap<>();

    public BackgroundImageBox(BasePlayScreen<T_GAME> parent) {
        this.parent = parent;
        this.setFillParent(true);
        //this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        for (String gameArea : parent.game.getGameAreaValues()) {
            imageMap.put(gameArea, new SpriteDrawable(new Sprite(parent.game.getTextureManager().getBackgroundTexture(gameArea))));
        }

    }

    @Override
    public void onGameAreaChange(String last, String current) {
        if (parent.game.drawGameImageAndPlayAudio) {
            Drawable image = imageMap.get(current);
            this.setBackground(image);
        }
    }

}
