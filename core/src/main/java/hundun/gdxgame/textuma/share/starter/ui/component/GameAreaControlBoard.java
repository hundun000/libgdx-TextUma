package hundun.gdxgame.textuma.share.starter.ui.component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;

/**
 * @author hundun
 * Created on 2021/11/20
 */
public class GameAreaControlBoard<T_GAME extends BaseHundunGame> extends Table implements IGameAreaChangeListener {

    BasePlayScreen<T_GAME> parent;
    Map<String, GameAreaControlNode<T_GAME>> nodes = new LinkedHashMap<>();


    public GameAreaControlBoard(BasePlayScreen<T_GAME> parent, List<String> gameAreas) {
        super();
        this.parent = parent;
//        this.setSize(
//                WIDTH,
//                HEIGHT);

        for (String gameArea : gameAreas) {
            initButtonMap(gameArea, false);
        }

        rebuildChild(null);
        if (parent.game.debugMode) {
            this.debugAll();
        }
    }

    private void initButtonMap(String gameArea, boolean longVersion) {
        GameAreaControlNode<T_GAME> node = new GameAreaControlNode<>(parent, gameArea, longVersion, !parent.game.drawGameImageAndPlayAudio);
        nodes.put(gameArea, node);
        this.add(node).width(parent.getLayoutConst().AREA_BOARD_BORDER_WIDTH).height(parent.getLayoutConst().AREA_BOARD_CELL_HEIGHT).row();
    }



    @Override
    public void onGameAreaChange(String last, String current) {
        rebuildChild(current);
    }

    private void rebuildChild(String current) {

        nodes.entrySet().forEach(entry -> {
            if (entry.getKey() == current) {
                entry.getValue().changeVersion(true);
            } else {
                entry.getValue().changeVersion(false);
            }

        });

    }


}
