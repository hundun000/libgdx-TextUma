package hundun.gdxgame.textuma.share.starter.ui.screen.play;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.gamelib.base.LogicFrameHelper;
import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.data.RootSaveData;
import hundun.gdxgame.textuma.core.ui.component.construction.AbstractConstructionControlBoard;

import hundun.gdxgame.textuma.share.framework.listener.IAchievementUnlockListener;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;
import hundun.gdxgame.textuma.share.starter.ui.component.AchievementMaskBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.BackgroundImageBox;
import hundun.gdxgame.textuma.share.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.StorageInfoBoard;
import lombok.Getter;
import lombok.Setter;


/**
 * @author hundun
 * Created on 2021/12/06
 */
public abstract class BaseUmaPlayScreen
        extends BaseHundunScreen<TextUmaGame, RootSaveData>
        implements IAchievementUnlockListener {

    public static final int LOGIC_FRAME_PER_SECOND = 10;

    @Getter
    private String area;


    // ====== need child construct-init start ======
    @Getter
    private final String startArea;
    @Getter
    protected final PlayScreenLayoutConst layoutConst;
    // ====== need child construct-init end ======


    // ====== need child lazy-init start ======
    protected AchievementMaskBoard<TextUmaGame> achievementMaskBoard;

    protected StorageInfoBoard<TextUmaGame> storageInfoTable;
    protected AbstractConstructionControlBoard constructionControlBoard;
    protected BackgroundImageBox backgroundImageBox;
    protected GameAreaControlBoard gameAreaControlBoard;
    // ====== need child lazy-init end ======

    protected List<ILogicFrameListener> logicFrameListeners = new ArrayList<>();
    protected List<IGameAreaChangeListener> gameAreaChangeListeners = new ArrayList<>();


    public BaseUmaPlayScreen(TextUmaGame game, String screenId, String startArea, PlayScreenLayoutConst layoutConst) {
        super(game, game.getSharedViewport());
        this.startArea = startArea;
        this.layoutConst = layoutConst;
        this.logicFrameHelper = new LogicFrameHelper(LOGIC_FRAME_PER_SECOND);
        game.getEventManager().registerListener(this);

    }

    @Override
    protected void onLogicFrame() {
        super.onLogicFrame();

        for (ILogicFrameListener logicFrameListener : logicFrameListeners) {
            logicFrameListener.onLogicFrame();
        }
        game.getManagerContext().getStorageManager().frameDeltaAmountClear();
    }

    public void setAreaAndNotifyChildren(String current) {
        String last = this.area;
        this.area = current;

        for (IGameAreaChangeListener gameAreaChangeListener : gameAreaChangeListeners) {
            gameAreaChangeListener.onGameAreaChange(last, current);
        }

    }

    public void hideAchievementMaskBoard() {
        Gdx.app.log(this.getClass().getSimpleName(), "hideAchievementMaskBoard called");
        achievementMaskBoard.setVisible(false);
        Gdx.input.setInputProcessor(uiStage);
        logicFrameHelper.setLogicFramePause(false);
    }

    @Override
    public void onAchievementUnlock(AchievementPrototype prototype) {
        Gdx.app.log(this.getClass().getSimpleName(), "onAchievementUnlock called");
        achievementMaskBoard.setAchievementPrototype(prototype);
        achievementMaskBoard.setVisible(true);
        Gdx.input.setInputProcessor(popupUiStage);
        logicFrameHelper.setLogicFramePause(true);
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(uiStage);
        game.getBatch().setProjectionMatrix(uiStage.getViewport().getCamera().combined);

        backUiStage.clear();
        popupRootTable.clear();
        lazyInitBackUiAndPopupUiContent();

        uiRootTable.clear();
        lazyInitUiRootContext();

        lazyInitLogicContext();

        // start area
        setAreaAndNotifyChildren(startArea);
        Gdx.app.log(this.getClass().getSimpleName(), "show done");
    }

    protected abstract void lazyInitLogicContext();

    protected abstract void lazyInitUiRootContext();

    protected abstract void lazyInitBackUiAndPopupUiContent();

}
