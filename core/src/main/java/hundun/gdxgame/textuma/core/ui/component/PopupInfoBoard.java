package hundun.gdxgame.textuma.core.ui.component;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.core.logic.handler.BaseRaceActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.BaseTrainActionHandler;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePack;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.starter.ui.component.ResourceAmountPairNode;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;


/**
 * @author hundun
 * Created on 2021/11/08
 */
public class PopupInfoBoard<T_GAME extends BaseHundunGame> extends Table {
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 70;

    BasePlayScreen<T_GAME> parent;

    public PopupInfoBoard(BasePlayScreen<T_GAME> parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        //this.setBackground(parent.getLayoutConst().blackBoard);
        //this.setVisible(true);
    }


    


    public void updateAsTrainInfo(BaseTrainActionHandler model) {
        this.clearChildren();

        TrainInfoHelper.work(this, model);

        if (parent.game.debugMode) {
            this.debug();
        }
    }


    public void updateAsIdleGuide(String text) {
        this.clearChildren();

        
        if (parent.game.debugMode) {
            this.debug();
        }
    }

    private static class TrainInfoHelper {
        
        public static void work(PopupInfoBoard<?> table, BaseTrainActionHandler model) {
            table.clearChildren();

            table.add(new Label(model.getPopupInfo(), table.parent.game.getButtonSkin()))
                ;

        }
    }

    public void updateAsRaceInfo(BaseRaceActionHandler model) {
        // TODO Auto-generated method stub
        
    }

}
