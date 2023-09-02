package hundun.gdxgame.textuma.share.starter.ui.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BaseUmaPlayScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class StorageInfoBoard<T_GAME extends BaseHundunGame> extends Table {

    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 120;

    public static int NUM_NODE_PER_ROW = 5;

    List<String> shownOrders;
    Set<String> shownTypes = new HashSet<>();
    BaseUmaPlayScreen parent;

    List<TextUmaResourceAmountPairNode> nodes = new ArrayList<>();

    public void lazyInit(List<String> shownOrders) {
        this.shownOrders = shownOrders;
        rebuildCells();
    }

    //Label mainLabel;


    public StorageInfoBoard(BaseUmaPlayScreen parent) {
        this.parent = parent;
        //this.setBackground(BasePlayScreen.blackBoard);


        if (parent.getGame().debugMode) {
            this.debugAll();
        }
    }



    private void rebuildCells() {
        this.clearChildren();
        nodes.clear();

        for (int i = 0; i < shownOrders.size(); i++) {
            String resourceType = shownOrders.get(i);
            if (shownTypes.contains(resourceType)) {
                TextUmaResourceAmountPairNode node = new TextUmaResourceAmountPairNode(parent.getGame(), resourceType);
                nodes.add(node);
                shownTypes.add(resourceType);
                Cell<TextUmaResourceAmountPairNode> cell = this.add(node).width(NODE_WIDTH).height(NODE_HEIGHT);
                if ((i + 1) % NUM_NODE_PER_ROW == 0) {
                    cell.row();
                }
            }
        }

    }



    public void updateViewData() {
//        List<ResourceType> shownResources = areaShownResources.get(parent.getArea());
//        if (shownResources == null) {
//            mainLabel.setText("Unkonwn area");
//            return;
//        }

//        String text = shownResources.stream()
//                .map(shownResource -> parent.getGame().getManagerContext().getStorageManager().getResourceDescription(shownResource))
//                .collect(Collectors.joining("    "));
//        text += "\nBuffs = " + parent.getGame().getManagerContext().getBuffManager().getBuffAmounts();
//        mainLabel.setText(text);
        boolean needRebuildCells = !shownTypes.equals(parent.getGame().getManagerContext().getStorageManager().getUnlockedResourceTypes());
        if (needRebuildCells) {
            shownTypes.clear();
            shownTypes.addAll(parent.getGame().getManagerContext().getStorageManager().getUnlockedResourceTypes());
            rebuildCells();
        }

        nodes.stream().forEach(node -> node.update(parent.getGame().getManagerContext().getStorageManager().getResourceNumOrZero(node.getResourceType())));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateViewData();
        super.draw(batch, parentAlpha);
    }


}
