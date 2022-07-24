package hundun.gdxgame.textuma.share.starter.ui.component.board.construction.impl.scroll;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hundun.gdxgame.textuma.share.starter.ui.component.board.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.board.construction.impl.ConstructionControlNode;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;




/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ScrollConstructionControlBoard extends AbstractConstructionControlBoard {



    public final int LR_BUTTON_HEIGHT;
    public final int LR_BUTTON_WIDTH = 10;


    static final int NUM_NODE_MIN = 1;

    ImageButton leftButton;
    ImageButton rightButton;
    Table childTable;




    public ScrollConstructionControlBoard(BasePlayScreen<?> parent) {
        super(parent);

        this.LR_BUTTON_HEIGHT = parent.getLayoutConst().CONSTRUCION_BOARD_BORDER_HEIGHT;


        leftButton = new ImageButton(BasePlayScreen.createBorderBoard(LR_BUTTON_WIDTH, LR_BUTTON_HEIGHT, 0.8f, 3));
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });
        rightButton = new ImageButton(BasePlayScreen.createBorderBoard(LR_BUTTON_WIDTH, LR_BUTTON_HEIGHT, 0.8f, 3));
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });

        childTable = new Table();
        childTable.setBackground(parent.getLayoutConst().simpleBoardBackgroundMiddle);
        ScrollPane scrollPane = new ScrollPane(childTable, parent.game.getButtonSkin());
        scrollPane.setScrollingDisabled(false, true);


        this.add(leftButton);
        this.add(scrollPane);
        this.add(rightButton);
        this.setBackground(parent.getLayoutConst().simpleBoardBackground);

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
            ConstructionControlNode constructionView = new ConstructionControlNode(parent, i, parent.getLayoutConst());
            constructionControlNodes.add(constructionView);
            childTable.add(constructionView).spaceRight(10).expand();
        }
        return childrenSize;
    }




}
