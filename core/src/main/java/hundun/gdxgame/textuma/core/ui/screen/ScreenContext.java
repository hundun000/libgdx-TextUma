package hundun.gdxgame.textuma.core.ui.screen;

import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.share.starter.ui.screen.menu.TextUmaMenuScreen;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hundun
 * Created on 2021/11/02
 */
@Getter
@Setter
public class ScreenContext {
    TextUmaMenuScreen menuScreen;
    UmaPlayScreen playScreen;


    public void lazyInit(TextUmaGame game) {
        this.menuScreen = new TextUmaMenuScreen(game);
        this.playScreen = new UmaPlayScreen(game);

        game.getScreenManager().addScreen(menuScreen.getClass().getSimpleName(), menuScreen);
        game.getScreenManager().addScreen(playScreen.getClass().getSimpleName(), playScreen);

        BlendingTransition blendingTransition = new BlendingTransition(game.getBatch(), 1F);
        game.getScreenManager().addScreenTransition("blending_transition", blendingTransition);
    }
}
