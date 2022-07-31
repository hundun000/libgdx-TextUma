package hundun.gdxgame.textuma.share.starter.ui.screen.play;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import hundun.gdxgame.textuma.core.logic.handler.BaseTrainActionHandler;
import hundun.gdxgame.textuma.core.ui.component.MainInfoBoard;
import hundun.gdxgame.textuma.core.ui.component.construction.AbstractConstructionControlBoard;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.listener.IAchievementUnlockListener;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.starter.ui.component.AchievementMaskBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.BackgroundImageBox;
import hundun.gdxgame.textuma.share.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.GameImageDrawer;
import hundun.gdxgame.textuma.share.starter.ui.component.StorageInfoBoard;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData.RaceInfo;


/**
 * @author hundun
 * Created on 2021/12/06
 * @param <T_GAME>
 */
public abstract class BasePlayScreen<T_GAME extends BaseHundunGame>
        extends BaseScreen<T_GAME>
        implements IAchievementUnlockListener {

    public static final int LOGIC_FRAME_PER_SECOND = 10;
    private static final float LOGIC_FRAME_LENGTH = 1f / LOGIC_FRAME_PER_SECOND;
    private int clockCount = 0;
    private float logicFramAccumulator;


    private boolean logicFramePause;
    // ====== replace-lombok ======
    public boolean isLogicFramePause() {
        return logicFramePause;
    }
    public void setLogicFramePause(boolean logicFramePause) {
        this.logicFramePause = logicFramePause;
    }

    private String area;
    // ------ replace-lombok ------
    public String getArea() {
        return area;
    }

    // ====== need child construct-init start ======
    private final String startArea;
    // ------ replace-lombok ------
    public String getStartArea() {
        return startArea;
    }

    protected final PlayScreenLayoutConst layoutConst;
    // ------ replace-lombok ------
    public PlayScreenLayoutConst getLayoutConst() {
        return layoutConst;
    }
    // ====== need child construct-init end ======


    // ====== need child lazy-init start ======
    protected AchievementMaskBoard<T_GAME> achievementMaskBoard;
    
    protected GameImageDrawer<T_GAME> gameImageDrawer;
    protected StorageInfoBoard<T_GAME> storageInfoTable;
    protected AbstractConstructionControlBoard constructionControlBoard;
    protected BackgroundImageBox<T_GAME> backgroundImageBox;
    protected GameAreaControlBoard<T_GAME> gameAreaControlBoard;
    // ====== need child lazy-init end ======

    protected List<ILogicFrameListener> logicFrameListeners = new ArrayList<>();
    protected List<IGameAreaChangeListener> gameAreaChangeListeners = new ArrayList<>();

    protected Stage popupUiStage;
    protected Stage backUiStage;
    protected Table uiRootTable;
    protected Table popupRootTable;

    public BasePlayScreen(T_GAME game, String screenId,  String startArea, PlayScreenLayoutConst layoutConst) {
        super(game, screenId);
        this.startArea = startArea;
        this.layoutConst = layoutConst;
        game.getEventManager().registerListener(this);
        popupUiStage = new Stage(new FitViewport(game.LOGIC_WIDTH, game.LOGIC_HEIGHT, uiStage.getCamera()));
        backUiStage = new Stage(new FitViewport(game.LOGIC_WIDTH, game.LOGIC_HEIGHT, uiStage.getCamera()));

    }

    protected void logicFrameCheck(float delta) {
        logicFramAccumulator += delta;
        if (logicFramAccumulator >= LOGIC_FRAME_LENGTH) {
            logicFramAccumulator -= LOGIC_FRAME_LENGTH;
            if (!logicFramePause) {
                clockCount++;

                for (ILogicFrameListener logicFrameListener : logicFrameListeners) {
                    logicFrameListener.onLogicFrame();
                }
                game.getModelContext().getStorageManager().frameDeltaAmountClear();
            }
        }
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
        setLogicFramePause(false);
    }

    @Override
    public void onAchievementUnlock(AchievementPrototype prototype) {
        Gdx.app.log(this.getClass().getSimpleName(), "onAchievementUnlock called");
        achievementMaskBoard.setAchievementPrototype(prototype);
        achievementMaskBoard.setVisible(true);
        Gdx.input.setInputProcessor(popupUiStage);
        setLogicFramePause(true);
    }


    public static Drawable createTwoColorBoard(int width, int height, float grayColor, int colorStartX) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB565);
        pixmap.setColor(grayColor, grayColor, grayColor, 1.0f);
        pixmap.fillRectangle(colorStartX , 0, width - colorStartX, height);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        return drawable;
    }

    public static Drawable createBorderBoard(int width, int height, float grayColor, int borderWidth) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB565);
        pixmap.setColor(grayColor + 0.1f, grayColor + 0.1f, grayColor + 0.1f, 1.0f);
        pixmap.fill();
        pixmap.setColor(grayColor, grayColor, grayColor, 1.0f);
        pixmap.fillRectangle(borderWidth, borderWidth, width - borderWidth * 2, height - borderWidth * 2);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        return drawable;
    }
    
    public static Drawable createOneColorBoard(int width, int height, float grayColor) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB565);
        pixmap.setColor(grayColor, grayColor, grayColor, 1.0f);
        pixmap.fill();
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        return drawable;
    }
    
    public static Drawable createBlackBoard(int width, int height) {
        return BasePlayScreen.createOneColorBoard(width, height, 1.0f);
    }
    
    public static Drawable blackBoard = BasePlayScreen.createBlackBoard(1, 1);

    

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        logicFrameCheck(delta);

        uiStage.act();

        // ====== be careful of draw order ======
        backUiStage.draw();
        if (game.drawGameImageAndPlayAudio) {
            gameImageDrawer.allEntitiesMoveForFrameAndDraw();
        }
        uiStage.draw();
        popupUiStage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiStage);
        game.getBatch().setProjectionMatrix(uiStage.getViewport().getCamera().combined);

        popupRootTable = new Table();
        popupRootTable.setFillParent(true);
        popupUiStage.addActor(popupRootTable);
        lazyInitBackUiAndPopupUiContent();

        uiRootTable = new Table();
        uiRootTable.setFillParent(true);
        uiStage.addActor(uiRootTable);
        lazyInitUiRootContext();

        lazyInitLogicContext();

        // start area
        setAreaAndNotifyChildren(startArea);
    }

    protected abstract void lazyInitLogicContext();

    protected abstract void lazyInitUiRootContext();

    protected abstract void lazyInitBackUiAndPopupUiContent();

}
