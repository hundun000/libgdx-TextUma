package hundun.gdxgame.textuma.share.starter.ui.screen.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.starter.ui.screen.AbstractMenuScreen;


public class TextUmaMenuScreen extends AbstractMenuScreen {


//    private MenuComponent<String> menu;
//
//    private GlyphLayout menuText;

    public TextUmaMenuScreen(TextUmaGame game) {
        super(
                game,
                "Text Uma 你好世界",
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        game.getSaveHandler().gameplayLoadOrStarter(true);
                        game.getScreenManager().pushScreen(UmaPlayScreen.class.getSimpleName(), "blending_transition");
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                },
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        game.getSaveHandler().gameplayLoadOrStarter(false);
                        game.getScreenManager().pushScreen(UmaPlayScreen.class.getSimpleName(), "blending_transition");
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                }
        );


    }


}