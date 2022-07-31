package hundun.gdxgame.textuma.core.ui.component;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.handler.BaseTrainActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.race.BaseRaceActionHandler;
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
public class PopupInfoBoard extends Table {
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 70;

    BasePlayScreen<TextUmaGame> parent;

    public PopupInfoBoard(BasePlayScreen<TextUmaGame> parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        //this.setBackground(parent.getLayoutConst().blackBoard);
        //this.setVisible(true);
    }


    


    public void updateAsTrainActionHint(BaseTrainActionHandler model) {
        this.clearChildren();

        TrainInfoHelper.work(this, model);

        if (parent.game.debugMode) {
            this.debug();
        }
    }


    public void updateAsClear() {
        this.clearChildren();

        
        if (parent.game.debugMode) {
            this.debug();
        }
    }

    private static class TrainInfoHelper {
        
        private static void work(PopupInfoBoard table, BaseTrainActionHandler model) {
            table.clearChildren();

            table.add(new Label("Cost: ", table.parent.game.getButtonSkin()));
            model.getOutputComponent().getOutputCostPack().getModifiedValues().forEach(it -> 
                buildOneGain(table, table.parent.game, 
                        table.parent.game.getGameDictionary().resourceIdToShowName(it.getType()), 
                        it.getAmount()
                        )
            );
            table.row();
            table.add(new Label("Gain: ", table.parent.game.getButtonSkin()));
            model.getOutputComponent().getOutputGainPack().getModifiedValues().forEach(it -> 
                buildOneGain(table, table.parent.game, 
                        table.parent.game.getGameDictionary().resourceIdToShowName(it.getType()), 
                        it.getAmount()
                        )
            );

        }
        
        private static void buildOneGain(Table table, TextUmaGame game, String key, Object value) {
            table.add(new Label(key, game.getButtonSkin(), TextUmaGame.GAME_WORD_SKIN_KEY));
            table.add(new Label(": ", game.getButtonSkin()));
            table.add(new Label(value.toString(), game.getButtonSkin()));
        }
        
        
    }

    public void updateAsRaceActionHint(BaseRaceActionHandler model) {
        this.clearChildren();

        this.add(new Label(model.getPopupInfo(), parent.game.getButtonSkin()));

        if (parent.game.debugMode) {
            this.debug();
        }
        
    }





    public void updateAsText(String text) {
        this.clearChildren();

        this.add(new Label(text, parent.game.getButtonSkin()));

        if (parent.game.debugMode) {
            this.debug();
        }
        
    }

}
