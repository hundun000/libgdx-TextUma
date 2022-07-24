package hundun.gdxgame.textuma.share.starter.ui.component;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import hundun.gdxgame.textuma.core.ui.component.TextSkinButton;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;


/**
 * @author hundun
 * Created on 2021/12/06
 */
public class GameAreaControlNode<T_GAME extends BaseHundunGame> extends Table {

    BasePlayScreen<T_GAME> parent;
    //Image image;
    TextSkinButton content;
    boolean debugType;
    String gameAreaId;
    String text;
    
    public GameAreaControlNode(BasePlayScreen<T_GAME> parent, String gameAreaId, boolean longVersion, boolean debugType) {
        this.parent = parent;
        this.debugType = debugType;
        this.gameAreaId = gameAreaId;
        this.text = parent.game.getGameDictionary().gameAreaIdToShowName(gameAreaId);
        
        this.setTouchable(Touchable.enabled); 

        content = TextSkinButton.typeButton("", parent.game);
        this.add(content).expand().left();
        
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                parent.setAreaAndNotifyChildren(gameAreaId);
            }
        });
        
        changeVersion(longVersion);
    }

    private Drawable getNewImage(boolean longVersion) {
        if (longVersion) {
            Drawable drawable = BasePlayScreen.createBlackBoard(100, 50);
            return drawable;
        } else {
            Drawable drawable = BasePlayScreen.createBlackBoard(75, 50);
            return drawable;
        }
    }

    public void changeVersion(boolean enable) {
        //this.setBackground(getNewImage(enable));
        this.content.contentSetEnable(enable);
        if (enable) {
            this.content.contentSetText(text);
        } else {
            this.content.contentSetText(" ".repeat(3) + text);
        }
    }

}
