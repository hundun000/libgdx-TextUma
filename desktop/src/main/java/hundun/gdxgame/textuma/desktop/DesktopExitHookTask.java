package hundun.gdxgame.textuma.desktop;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame.SaveDataHelper;

/**
 * @author hundun
 * Created on 2022/04/08
 */



public class DesktopExitHookTask extends Thread {

    BaseHundunGame game;

    public DesktopExitHookTask(BaseHundunGame game) {
        super();
        this.game = game;
    }
    
    @Override
    public void run() {
        game.getSaveTool().saveRootSaveData(SaveDataHelper.currentSituationToSaveData(game.getModelContext()));
        Gdx.app.log(this.getClass().getSimpleName(), "run done");
    }

}
