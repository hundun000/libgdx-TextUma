package hundun.gdxgame.textuma.html.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.html.GwtPreferencesSaveTool;

public class HtmlLauncher extends GwtApplication {

    private final TextUmaGame game;
    
    public HtmlLauncher() {
        game = new TextUmaGame(new GwtPreferencesSaveTool("TextUmaGame-html-save"));
    }
    
    @Override
    public GwtApplicationConfiguration getConfig () {
        return new GwtApplicationConfiguration(game.getWidth(), game.getHeight());
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return game;
    }
}