package hundun.gdxgame.textuma.core.ui.component.construction;
/**
 * @author hundun
 * Created on 2022/02/09
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.core.ui.component.construction.impl.BaseUserActionControlNode;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

public abstract class AbstractConstructionControlBoard extends Table implements ILogicFrameListener, IGameAreaChangeListener {
    protected UmaPlayScreen parent;
    /**
     * 显示在当前screen的Construction集合。以ConstructionView形式存在。
     */
    protected List<BaseUserActionControlNode> constructionControlNodes = new ArrayList<>();



    public AbstractConstructionControlBoard(UmaPlayScreen parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void onLogicFrame() {
        constructionControlNodes.forEach(item -> item.onLogicFrame());
        //parent.getGame().getManagerContext().getConstructionManager().logicFrameForAllConstructionModels();

    }

    @Override
    public void onGameAreaChange(String last, String current) {


        List<UmaActionHandler> newConstructions = parent.getGame().getManagerContext().getConstructionManager().getAreaShownConstructionsOrEmpty(current);

        int childrenSize = initChild(newConstructions.size());

        for (int i = 0; i < childrenSize && i < newConstructions.size(); i++) {
            constructionControlNodes.get(i).setModel(newConstructions.get(i));
        }
        for (int i = newConstructions.size(); i < childrenSize; i++) {
            constructionControlNodes.get(i).setModel(null);
        }
        Gdx.app.log("ConstructionInfoBorad", "Constructions change to: " + newConstructions.stream().map(construction -> construction.getName()).collect(Collectors.joining(",")));

    }

    /**
     * 初始化某个数量的Children。该数量不一定等于areaShownConstructionsSize，由实现决定。
     * @return childrenSize
     */
    protected abstract int initChild(int areaShownConstructionsSize);
}
