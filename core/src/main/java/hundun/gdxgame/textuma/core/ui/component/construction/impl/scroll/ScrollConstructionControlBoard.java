package hundun.gdxgame.textuma.core.ui.component.construction.impl.scroll;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.core.ui.component.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.textuma.core.ui.component.construction.impl.BaseUserActionControlNode;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ScrollConstructionControlBoard extends AbstractConstructionControlBoard {





    static final int NUM_NODE_MIN = 1;

    Table childTable;




    public ScrollConstructionControlBoard(UmaPlayScreen parent) {
        super(parent);

        

        childTable = new Table();
        //childTable.setBackground(parent.getLayoutConst().blackBoard);
        


        this.add(childTable);
        //this.setBackground(parent.getLayoutConst().blackBoard);

        if (parent.getGame().debugMode) {
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
