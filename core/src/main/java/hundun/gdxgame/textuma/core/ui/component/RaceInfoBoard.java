package hundun.gdxgame.textuma.core.ui.component;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.core.logic.handler.BaseTrainActionHandler;
import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePack;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.starter.ui.component.ResourceAmountPairNode;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;


/**
 * @author hundun
 * Created on 2021/11/08
 */
public class RaceInfoBoard<T_GAME extends BaseIdleGame> extends Table {
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 70;

    BasePlayScreen<T_GAME> parent;

    public RaceInfoBoard(BasePlayScreen<T_GAME> parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        //this.setBackground(parent.getLayoutConst().blackBoard);
        //this.setVisible(true);
    }


    private <T extends Actor> Container<T> wapperContainer(T content) {
        Container<T> container = new Container<>(content);
        //container.setBackground(BasePlayScreen.createBorderBoard(1, 1, 0.7f, 0));
        container.fill(true);
        return container;
    }



    private void buildOnePack(ResourcePack pack) {
        if (pack != null && pack.getModifiedValues() != null) {
            this.add(wapperContainer(new Label(pack.getDescriptionStart(), parent.game.getButtonSkin())));
            for (ResourcePair entry : pack.getModifiedValues()) {
                ResourceAmountPairNode<T_GAME> node = new ResourceAmountPairNode<>(parent.game, entry.getType());
                node.update(entry.getAmount());
                this.add(wapperContainer(node)).height(NODE_HEIGHT).width(NODE_WIDTH);
            }
            this.row();
        }
    }


    public void updateAsTrainInfo(BaseTrainActionHandler model) {
        this.clearChildren();

        add(wapperContainer(new Label(model.getDetailDescroptionConstPart(), parent.game.getButtonSkin())))
            .colspan(3)
            .left()
            .row();

        buildOnePack(model.getOutputComponent().getOutputCostPack());

        buildOnePack(model.getOutputComponent().getOutputGainPack());

        if (parent.game.debugMode) {
            this.debug();
        }
    }


    public void updateAsIdleGuide(String text) {
        this.clearChildren();
        
        add(new Label(text, parent.game.getButtonSkin()));
        
        if (parent.game.debugMode) {
            this.debug();
        }
    }


}
