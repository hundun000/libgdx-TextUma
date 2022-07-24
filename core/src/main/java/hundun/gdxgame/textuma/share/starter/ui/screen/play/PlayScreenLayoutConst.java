package hundun.gdxgame.textuma.share.starter.ui.screen.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author hundun
 * Created on 2022/01/22
 */
public class PlayScreenLayoutConst {
    public int CONSTRUCION_BOARD_ROOT_BOX_HEIGHT = 200;
    public int CONSTRUCION_CHILD_BOX_WIDTH = 120;
    public int CONSTRUCION_CHILD_BOX_BUTTON_HEIGHT = 30;
    public int CONSTRUCION_CHILD_BOX_NAME_HEIGHT = 50;

    public int STORAGE_BOARD_ROOT_BOX_HEIGHT = 80;
    public int AREA_BOARD_BORDER_WIDTH = 100;
    public int AREA_BOARD_CELL_HEIGHT = 50;
//    public static final int STORAGE_BOARD_CONTAIN_WIDTH = 100;
//    public static final int STORAGE_BOARD_CONTAIN_HEIGHT = 50;


    public int EXPECTED_DRAW_MIN_X;
    public int EXPECTED_DRAW_MAX_X;
    public int EXPECTED_DRAW_MIN_Y;
    public int EXPECTED_DRAW_MAX_Y;

    public PlayScreenLayoutConst(int gameLogicWidth, int gameLogicHeight) {
        this.EXPECTED_DRAW_MIN_X = 0;
        this.EXPECTED_DRAW_MAX_X = gameLogicWidth;
        this.EXPECTED_DRAW_MIN_Y = CONSTRUCION_BOARD_ROOT_BOX_HEIGHT;
        this.EXPECTED_DRAW_MAX_Y = gameLogicHeight - STORAGE_BOARD_ROOT_BOX_HEIGHT;
        Gdx.app.log(this.getClass().getSimpleName(), "EXPECTED_DRAW_MAX_X = " + EXPECTED_DRAW_MAX_X + ", EXPECTED_DRAW_MAX_Y = " + EXPECTED_DRAW_MAX_Y);

    }
}
