package hundun.gdxgame.textuma.core.ui.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.textuma.core.TextUmaGame;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class TextSkinButton extends Table {

    Label arrowLabel;
    Label contentLabel;
    public boolean fakeEnable;
    
    private static TextSkinButton type2(String content, TextUmaGame game) {
        TextSkinButton result = new TextSkinButton();
        //LabelStyle styleOrSkin = new LabelStyle(TextNinePatchWrapper.boxBitmapFont, Color.RED);
        Skin styleOrSkin = game.getMainSkin();
        //String subType = "as-button";
        
        result.contentLabel = new Label("", styleOrSkin);
        result.arrowLabel = new Label("> ", styleOrSkin);
        
        result.add(result.arrowLabel);
        result.add(result.contentLabel);
        //result.add(new Label("", styleOrSkin));
        result.row();
        
        result.contentSetText(content);
        result.contentSetEnable(true);
        
        if (game.debugMode) {
            result.debugAll();
        }
        return result;
    }
    
    public static TextSkinButton typeButton(String content, TextUmaGame game) {
        return type2(content, game);
    }

    public void contentSetText(String newText) {
        this.contentLabel.setText(newText);
    }
    
    public void contentSetEnable(Boolean enable) {
        this.fakeEnable = enable;
        if (enable) {
            this.arrowLabel.setColor(Color.GREEN);
            this.contentLabel.setColor(Color.GREEN);
        } else {
            this.arrowLabel.setColor(Color.GRAY);
            this.contentLabel.setColor(Color.GRAY);
        }
        
    }




}
