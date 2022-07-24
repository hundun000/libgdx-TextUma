package hundun.gdxgame.textuma.share.starter.ui.component.board.construction.impl.scroll;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hundun.gdxgame.textuma.share.starter.ui.component.TextNinePatchWrapper;
import hundun.gdxgame.textuma.share.starter.ui.component.board.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.board.construction.impl.BaseUserActionControlNode;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;




/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ScrollConstructionControlBoard extends AbstractConstructionControlBoard {





    static final int NUM_NODE_MIN = 1;

    Table childTable;




    public ScrollConstructionControlBoard(BasePlayScreen<?> parent) {
        super(parent);

        

        childTable = new Table();
        //childTable.setBackground(parent.getLayoutConst().blackBoard);
        


        this.add(childTable);
        //this.setBackground(parent.getLayoutConst().blackBoard);

        if (parent.game.debugMode) {
            this.debugCell();
        }
    }

    @Override
    protected int initChild(int areaShownConstructionsSize) {
        int childrenSize = areaShownConstructionsSize;

        constructionControlNodes.clear();
        childTable.clearChildren();

        for (int i = 0; i < childrenSize; i++) {
            BaseUserActionControlNode constructionView = new BaseUserActionControlNode(parent, i, parent.getLayoutConst());
            constructionControlNodes.add(constructionView);
            childTable.add(constructionView).spaceRight(10).expand();
        }
        return childrenSize;
    }




}
