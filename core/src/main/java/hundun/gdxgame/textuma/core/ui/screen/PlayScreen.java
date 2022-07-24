package hundun.gdxgame.textuma.core.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.ScreenId;
import hundun.gdxgame.textuma.core.ui.entity.GameEntityFactory;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;
import hundun.gdxgame.textuma.share.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.textuma.share.starter.ui.component.AchievementMaskBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.BackgroundImageBox;
import hundun.gdxgame.textuma.share.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.GameImageDrawer;
import hundun.gdxgame.textuma.share.starter.ui.component.PopupInfoBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.StorageInfoBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.board.construction.impl.fixed.FixedConstructionControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.board.construction.impl.scroll.ScrollConstructionControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.PlayScreenLayoutConst;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class PlayScreen extends BasePlayScreen<TextUmaGame> {

    public PlayScreen(TextUmaGame game) {
        super(game, ScreenId.PLAY, GameArea.AREA_COOKIE, customLayoutConst(game));
    }
    
    private static PlayScreenLayoutConst customLayoutConst(TextUmaGame game) {
        PlayScreenLayoutConst layoutConst = new PlayScreenLayoutConst(game.LOGIC_WIDTH, game.LOGIC_HEIGHT);
        NinePatch ninePatch = new NinePatch(game.getTextureManager().getDefaultBoardNinePatchTexture(), 
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(), 
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(), 
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize(), 
                game.getTextureManager().getDefaultBoardNinePatchEdgeSize()
                );
        layoutConst.simpleBoardBackground = new NinePatchDrawable(ninePatch);
        layoutConst.simpleBoardBackgroundMiddle = new TextureRegionDrawable(game.getTextureManager().getDefaultBoardNinePatchMiddle());
        return layoutConst;
    }
    
    @Override
    protected void lazyInitLogicContext() {
        GameEntityFactory gameEntityFactory = new GameEntityFactory(this.layoutConst, this);
        gameImageDrawer = new GameImageDrawer<>(this, gameEntityFactory);
        
        logicFrameListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(gameAreaControlBoard);
    }

    @Override
    protected void lazyInitUiRootContext() {
        
        storageInfoTable = new StorageInfoBoard<TextUmaGame>(this);
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
        uiRootTable.add(storageInfoTable).height(layoutConst.STORAGE_BOARD_BORDER_HEIGHT).fill().row();
        
        gameAreaControlBoard = new GameAreaControlBoard<TextUmaGame>(this, GameArea.values);
        uiRootTable.add(gameAreaControlBoard).expand().right().row();
        // impl switchable
        constructionControlBoard = new FixedConstructionControlBoard(this);
        uiRootTable.add(constructionControlBoard).height(layoutConst.CONSTRUCION_BOARD_BORDER_HEIGHT).fill();
        
        if (game.debugMode) {
            uiRootTable.debugCell();
        }
    }
    
    @Override
    protected void lazyInitBackUiAndPopupUiContent() {
        
        this.backgroundImageBox = new BackgroundImageBox<TextUmaGame>(this);
        backUiStage.addActor(backgroundImageBox);
        
        
        popUpInfoBoard = new PopupInfoBoard<TextUmaGame>(this);
        popupRootTable.add(popUpInfoBoard).bottom().expand().row();
        // empty image for hold the space
        popupRootTable.add(new Image()).height(game.LOGIC_HEIGHT / 4);
        
        achievementMaskBoard = new AchievementMaskBoard<TextUmaGame>(this);
        popupUiStage.addActor(achievementMaskBoard);
    }


    @Override
    public void dispose() {


    }


}
