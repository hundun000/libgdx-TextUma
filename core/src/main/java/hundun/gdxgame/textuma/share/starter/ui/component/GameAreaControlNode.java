package hundun.gdxgame.textuma.share.starter.ui.component;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hundun.gdxgame.textuma.core.ui.component.TextSkinButton;

import hundun.gdxgame.textuma.share.starter.ui.screen.play.BaseUmaPlayScreen;


/**
 * @author hundun
 * Created on 2021/12/06
 */
public class GameAreaControlNode extends Table {

    BaseUmaPlayScreen parent;
    //Image image;
    TextSkinButton content;
    String gameAreaId;
    String text;
    
    public GameAreaControlNode(BaseUmaPlayScreen parent, String gameAreaId, boolean longVersion) {
        this.parent = parent;
        this.gameAreaId = gameAreaId;
        this.text = parent.getGame().getGameDictionary().gameAreaIdToShowName(gameAreaId);
        
        this.setTouchable(Touchable.enabled); 

        content = TextSkinButton.typeButton("", parent.getGame());
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


    public void changeVersion(boolean enable) {
        //this.setBackground(getNewImage(enable));
        this.content.contentSetEnable(enable);
        if (enable) {
            this.content.contentSetText("To " + text);
        } else {
            this.content.contentSetText("In " + text);
        }
    }

}
