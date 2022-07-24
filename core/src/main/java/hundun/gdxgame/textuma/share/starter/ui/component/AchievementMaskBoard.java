package hundun.gdxgame.textuma.share.starter.ui.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;

/**
 * @author hundun
 * Created on 2021/11/12
 */
public class AchievementMaskBoard<T_GAME extends BaseIdleGame> extends Table {

    BasePlayScreen<T_GAME> parent;
    Label label;

    public AchievementMaskBoard(BasePlayScreen<T_GAME> parent) {
        this.parent = parent;
        this.setBackground(new SpriteDrawable(new Sprite(parent.game.getTextureManager().getWinTexture())));
        this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        label = new Label("", parent.game.getButtonSkin());
        this.add(label).center().row();

        Button textButton = new TextButton("continue", parent.game.getButtonSkin());
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.hideAchievementMaskBoard();
            }
        });
        this.add(textButton).center();

        this.setVisible(false);

    }
    public void setAchievementPrototype(AchievementPrototype prototype) {
        label.setText(prototype.getDescription());
    }
}
