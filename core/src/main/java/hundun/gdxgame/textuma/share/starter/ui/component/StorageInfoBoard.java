package hundun.gdxgame.textuma.share.starter.ui.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;

/**
 * @author hundun
 * Created on 2021/11/05
 */
public class StorageInfoBoard<T_GAME extends BaseIdleGame> extends Table {

    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 120;

    public static int NUM_NODE_PER_ROW = 5;

    List<String> shownOrders;
    Set<String> shownTypes = new HashSet<>();
    BasePlayScreen<T_GAME> parent;

    List<ResourceAmountPairNode<T_GAME>> nodes = new ArrayList<>();

    public void lazyInit(List<String> shownOrders) {
        this.shownOrders = shownOrders;
        rebuildCells();
    }

    //Label mainLabel;


    public StorageInfoBoard(BasePlayScreen<T_GAME> parent) {
        this.parent = parent;
        this.setBackground(BasePlayScreen.createBorderBoard(
                25,
                10,
                0.7f, 1));


        if (parent.game.debugMode) {
            this.debugAll();
        }
    }



    private void rebuildCells() {
        this.clearChildren();
        nodes.clear();

        for (int i = 0; i < shownOrders.size(); i++) {
            String resourceType = shownOrders.get(i);
            if (shownTypes.contains(resourceType)) {
                ResourceAmountPairNode<T_GAME> node = new ResourceAmountPairNode<>(parent.game, resourceType);
                nodes.add(node);
                shownTypes.add(resourceType);
                Cell<ResourceAmountPairNode<T_GAME>> cell = this.add(node).width(NODE_WIDTH).height(NODE_HEIGHT);
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
//                .map(shownResource -> parent.game.getModelContext().getStorageManager().getResourceDescription(shownResource))
//                .collect(Collectors.joining("    "));
//        text += "\nBuffs = " + parent.game.getModelContext().getBuffManager().getBuffAmounts();
//        mainLabel.setText(text);
        boolean needRebuildCells = !shownTypes.equals(parent.game.getModelContext().getStorageManager().getUnlockedResourceTypes());
        if (needRebuildCells) {
            shownTypes.clear();
            shownTypes.addAll(parent.game.getModelContext().getStorageManager().getUnlockedResourceTypes());
            rebuildCells();
        }

        nodes.stream().forEach(node -> node.update(parent.game.getModelContext().getStorageManager().getResourceNumOrZero(node.getResourceType())));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateViewData();
        super.draw(batch, parentAlpha);
    }


}
