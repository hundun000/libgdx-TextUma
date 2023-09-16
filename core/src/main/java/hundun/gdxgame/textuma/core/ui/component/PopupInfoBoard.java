package hundun.gdxgame.textuma.core.ui.component;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.handler.race.BaseRaceActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.train.BaseTrainActionHandler;

import hundun.gdxgame.textuma.share.starter.ui.screen.play.BaseUmaPlayScreen;
import lombok.Getter;

import java.util.List;


/**
 * @author hundun
 * Created on 2021/11/08
 */
public class PopupInfoBoard extends Table {
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 70;

    @Getter
    BaseUmaPlayScreen playScreen;

    public PopupInfoBoard(BaseUmaPlayScreen parent) {
        //super("GUIDE_TEXT", parent.getGame().getMainSkin());
        this.playScreen = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        //this.setBackground(parent.getLayoutConst().blackBoard);
        //this.setVisible(true);
    }


    


    public void updateAsTrainActionHint(BaseTrainActionHandler model, boolean afford) {
        this.clearChildren();

        TrainInfoHelper.work(this, model, afford);

        if (playScreen.getGame().debugMode) {
            this.debug();
        }
    }


    public void updateAsClear() {
        this.clearChildren();

        
        if (playScreen.getGame().debugMode) {
            this.debug();
        }
    }

    private static class TrainInfoHelper {
        
        private static void work(PopupInfoBoard table, BaseTrainActionHandler model, boolean afford) {
            table.clearChildren();

            List<String> texts = table.getPlayScreen().getGame().getGameDictionary().getTrainBoardTexts();

            if (!afford) {
                table.add(new Label(texts.get(0), table.playScreen.getGame().getMainSkin()));
                table.row();
            }
            
            if (model.getOutputComponent().getOutputCostPack() != null && model.getOutputComponent().getOutputCostPack().getModifiedValues().size() > 0) {
                table.add(new Label(texts.get(1), table.playScreen.getGame().getMainSkin()));
                model.getOutputComponent().getOutputCostPack().getModifiedValues().forEach(it -> 
                    buildOneGain(table, table.playScreen.getGame(),
                            table.playScreen.getGame().getGameDictionary().resourceIdToShowName(it.getType()),
                            it.getAmount()
                            )
                );
                table.row();
            }
            
            table.add(new Label(texts.get(2), table.playScreen.getGame().getMainSkin()));
            model.getOutputComponent().getOutputGainPack().getModifiedValues().forEach(it -> 
                buildOneGain(table, table.playScreen.getGame(),
                        table.playScreen.getGame().getGameDictionary().resourceIdToShowName(it.getType()),
                        it.getAmount()
                        )
            );

        }
        
        private static void buildOneGain(Table table, TextUmaGame game, String key, Object value) {
            table.add(new Label(key, game.getMainSkin(), TextUmaGame.GAME_WORD_SKIN_KEY));
            table.add(new Label(value.toString(), game.getMainSkin()));
            table.add(new Label("; ", game.getMainSkin()));
        }
        
        
    }

    public void updateAsRaceActionHint(BaseRaceActionHandler model) {
        this.clearChildren();

        //this.add(new Label(model.getPopupInfo(), parent.getGame().getMainSkin()));

        if (playScreen.getGame().debugMode) {
            this.debug();
        }
        
    }





    public void updateAsText(String text) {
        this.clearChildren();

        this.add(new Label(text, playScreen.getGame().getMainSkin()));

        if (playScreen.getGame().debugMode) {
            this.debug();
        }
        
    }

}
