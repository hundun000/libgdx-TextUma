package hundun.gdxgame.textuma.core.ui.screen;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.share.starter.ui.screen.menu.MenuScreen;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class ScreenContext {
    MenuScreen<TextUmaGame> menuScreen;
    UmaPlayScreen gameBeeScreen;
    
    // ------ replace-lombok ------
    public MenuScreen<TextUmaGame> getMenuScreen() {
        return menuScreen;
    }
    public void setMenuScreen(MenuScreen<TextUmaGame> menuScreen) {
        this.menuScreen = menuScreen;
    }
    public UmaPlayScreen getGameBeeScreen() {
        return gameBeeScreen;
    }
    public void setGameBeeScreen(UmaPlayScreen gameBeeScreen) {
        this.gameBeeScreen = gameBeeScreen;
    }
    
    
}
