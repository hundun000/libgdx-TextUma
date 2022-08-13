package hundun.gdxgame.textuma.core.ui.component;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class TextNinePatchWrapper extends Table {

    //public static BitmapFont boxBitmapFont = new BitmapFont(Gdx.files.internal("fonts/Amble-Regular-26.fnt"));
    //public static BitmapFont boxBitmapFont = new BitmapFont(Gdx.files.internal("fonts/BoxDrawing-26.fnt"));
    //static String[] corners = new String[]{"+", "+", "+", "+"};
    static String[] corners = new String[]{"┌", "┐", "└", "┘"};
    static String vertical = "│";
    static String horizontal = "─";
    
    public static TextNinePatchWrapper build(BaseHundunGame game, Actor content) {
        return type1(game, content);
    }
    
    private static TextNinePatchWrapper type1(BaseHundunGame game, Actor content) {
        TextNinePatchWrapper result = new TextNinePatchWrapper();
        //LabelStyle styleOrSkin = new LabelStyle(boxBitmapFont, Color.RED);
        Skin styleOrSkin = game.getButtonSkin();
        
        result.add(new Label(corners[0], styleOrSkin));
        result.add(new Label(horizontal, styleOrSkin)).left().expandX();
        result.add(new Label(horizontal, styleOrSkin)).right().expandX();
        result.add(new Label(corners[1], styleOrSkin)).row();
        
        Table left = new Table();
        left.add(new Label(vertical, styleOrSkin)).top().expandY().row();
        left.add(new Label(vertical, styleOrSkin)).bottom().expandY();
        result.add(left).fillY();
        result.add(content).colspan(2);
        Table right = new Table();
        right.add(new Label(vertical, styleOrSkin)).top().expandY().row();
        right.add(new Label(vertical, styleOrSkin)).bottom().expandY();
        result.add(right).fillY();
        result.row();
        
        result.add(new Label(corners[2], styleOrSkin));
        result.add(new Label(horizontal, styleOrSkin)).left().expandX();
        result.add(new Label(horizontal, styleOrSkin)).right().expandX();
        result.add(new Label(corners[3], styleOrSkin)).row();
        
        if (game.debugMode) {
            result.debugAll();
        }
        return result;
    }


    
}
