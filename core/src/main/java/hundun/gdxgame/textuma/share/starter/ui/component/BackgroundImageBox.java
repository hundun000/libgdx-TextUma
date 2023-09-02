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


import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BaseUmaPlayScreen;

public class BackgroundImageBox extends Container<Image> implements IGameAreaChangeListener{
    BaseUmaPlayScreen parent;
    Map<String, Drawable> imageMap = new HashMap<>();

    public BackgroundImageBox(BaseUmaPlayScreen parent) {
        this.parent = parent;
        this.setFillParent(true);
        //this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        for (String gameArea : GameArea.values) {
            imageMap.put(gameArea, new SpriteDrawable(new Sprite(parent.getGame().getTextureManager().getBackgroundTexture(gameArea))));
        }

    }

    @Override
    public void onGameAreaChange(String last, String current) {
        Drawable image = imageMap.get(current);
        this.setBackground(image);
    }

}
