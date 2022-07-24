package hundun.gdxgame.textuma.share.starter.ui.component.board.construction.impl.fixed;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.share.starter.ui.component.board.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.board.construction.impl.ConstructionControlNode;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;



/**
 * @author hundun
 * Created on 2021/11/05
 */
public class FixedConstructionControlBoard extends AbstractConstructionControlBoard {


    public static int FIXED_NODE_NUM = 5;

    Table childTable;


    public FixedConstructionControlBoard(BasePlayScreen<?> parent) {

        super(parent);

        childTable = new Table();
        childTable.setBackground(parent.getLayoutConst().simpleBoardBackgroundMiddle);

        this.add(childTable);

        this.setBackground(parent.getLayoutConst().simpleBoardBackground);

        if (parent.game.debugMode) {
            this.debugCell();
        }
    }

    @Override
    protected int initChild(int areaShownConstructionsSize) {
        int childrenSize = FIXED_NODE_NUM;

        constructionControlNodes.clear();
        childTable.clearChildren();

        for (int i = 0; i < childrenSize; i++) {
            ConstructionControlNode constructionView = new ConstructionControlNode(parent, i, parent.getLayoutConst());
            constructionControlNodes.add(constructionView);
            childTable.add(constructionView).spaceRight(10).expand();
        }

        return childrenSize;

    }





}
