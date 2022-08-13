package hundun.gdxgame.textuma.share.starter.ui.component;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;

/**
 * @author hundun
 * Created on 2022/08/13
 */

public class BaseAmountPairNode<T_GAME extends BaseHundunGame> 
    extends HorizontalGroup {

    T_GAME game;
    Label image;
    Label label;
    
    public BaseAmountPairNode(T_GAME game) {
        super();
        this.game = game;
    }
    
    public BaseAmountPairNode<T_GAME> lazyInit(String typeText) {
        this.image = new Label(typeText, game.getButtonSkin(), TextUmaGame.GAME_WORD_SKIN_KEY);
        this.addActor(image);
        this.label = new Label("", game.getButtonSkin());
        this.addActor(label);
        return this;
    }
    
    
    public BaseAmountPairNode<T_GAME> update(long amout) {
        return update(String.valueOf(amout));
    }
    
    public BaseAmountPairNode<T_GAME> update(String valueText) {
        label.setText(valueText);
        return this;
    }


}
