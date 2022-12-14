package hundun.gdxgame.textuma.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import hundun.gdxgame.textuma.core.TextUmaGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
	    TextUmaGame game = new TextUmaGame(new PreferencesSaveTool("TextUmaGame-desktop-save.xml"));
	    //Runtime.getRuntime().addShutdownHook(new DesktopExitHookTask(game));
	    
	    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		new Lwjgl3Application(game, config);
	}
}
