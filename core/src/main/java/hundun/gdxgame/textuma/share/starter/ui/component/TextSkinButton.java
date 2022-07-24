package hundun.gdxgame.textuma.share.starter.ui.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class TextSkinButton extends Table {

    Label contentLabel;

    
    private static TextSkinButton type2(String content, BaseIdleGame game) {
        TextSkinButton result = new TextSkinButton();
        //LabelStyle styleOrSkin = new LabelStyle(TextNinePatchWrapper.boxBitmapFont, Color.RED);
        Skin styleOrSkin = game.getButtonSkin();
        String subType = "as-button";
        
        result.contentLabel = new Label("", styleOrSkin, subType);
        
        result.add(new Label("> ", styleOrSkin, subType));
        result.add(result.contentLabel);
        //result.add(new Label("", styleOrSkin));
        result.row();
        
        result.setText(content);
        
        if (game.debugMode) {
            result.debugAll();
        }
        return result;
    }
    
    public static TextSkinButton typeButton(String content, BaseIdleGame game) {
        return type2(content, game);
    }

    public void setText(String newText) {
        this.contentLabel.setText(newText);
    }


}
