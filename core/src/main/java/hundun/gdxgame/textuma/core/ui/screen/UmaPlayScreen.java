package hundun.gdxgame.textuma.core.ui.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.ScreenId;
import hundun.gdxgame.textuma.core.logic.handler.race.BaseRaceActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.train.BaseTrainActionHandler;
import hundun.gdxgame.textuma.core.ui.component.MainInfoBoard;
import hundun.gdxgame.textuma.core.ui.component.PopupInfoBoard;
import hundun.gdxgame.textuma.core.ui.component.TextNinePatchWrapper;
import hundun.gdxgame.textuma.core.ui.component.construction.impl.scroll.ScrollConstructionControlBoard;
import hundun.gdxgame.textuma.core.ui.entity.GameEntityFactory;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.starter.ui.component.AchievementMaskBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.BackgroundImageBox;
import hundun.gdxgame.textuma.share.starter.ui.component.GameAreaControlBoard;
import hundun.gdxgame.textuma.share.starter.ui.component.GameImageDrawer;
import hundun.gdxgame.textuma.share.starter.ui.component.StorageInfoBoard;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.PlayScreenLayoutConst;
import hundun.simulationgame.umamusume.horse.HorsePrototype;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class UmaPlayScreen extends BasePlayScreen<TextUmaGame> {

    protected MainInfoBoard mainInfoBoard;
    public MainInfoBoard getMainInfoBoard() {
        return mainInfoBoard;
    }
    protected PopupInfoBoard secondaryInfoBoard;
    public PopupInfoBoard getSecondaryInfoBoard() {
        return secondaryInfoBoard;
    }
    
    public UmaPlayScreen(TextUmaGame game) {
        super(game, ScreenId.PLAY, GameArea.AREA_RACE, new PlayScreenLayoutConst(game.LOGIC_WIDTH, game.LOGIC_HEIGHT));
    }

    
    @Override
    protected void lazyInitLogicContext() {
        GameEntityFactory gameEntityFactory = new GameEntityFactory(this.layoutConst, this);
        gameImageDrawer = new GameImageDrawer<>(this, gameEntityFactory);
        
        logicFrameListeners.add(constructionControlBoard);
        logicFrameListeners.add(game.getModelContext().getUmaManager());
        gameAreaChangeListeners.add(backgroundImageBox);
        gameAreaChangeListeners.add(constructionControlBoard);
        gameAreaChangeListeners.add(gameAreaControlBoard);
        gameAreaChangeListeners.add(game.getModelContext().getUmaManager());
    }

    @Override
    protected void lazyInitUiRootContext() {
        
        storageInfoTable = new StorageInfoBoard<TextUmaGame>(this);
        storageInfoTable.lazyInit(ResourceType.VALUES_FOR_SHOW_ORDER);
        uiRootTable.add(TextNinePatchWrapper.build(this, storageInfoTable))
                .height(layoutConst.STORAGE_BOARD_ROOT_BOX_HEIGHT)
                .fill()
                .colspan(2)
                .row()
                ;
        
        mainInfoBoard = new MainInfoBoard(this); 
        Table infoBoadArea = new Table();
        infoBoadArea.add(mainInfoBoard)
                .expand()
                .fill()
                .row();
                ;
        infoBoadArea.add(new Image())
                .height(layoutConst.SECONDARY_INFO_BOARD_HEIGHT)
                .expandY()
                .fillY()
                ;
        uiRootTable.add(infoBoadArea)
                .expand()
                .fill()
                ;
        
        gameAreaControlBoard = new GameAreaControlBoard<TextUmaGame>(this, GameArea.values);
        uiRootTable.add(TextNinePatchWrapper.build(this, gameAreaControlBoard))
                .right()
                .row()
                ;
        
        
        
        // impl switchable
        constructionControlBoard = new ScrollConstructionControlBoard(this);
        uiRootTable.add(TextNinePatchWrapper.build(this, constructionControlBoard))
                .height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT)
                .bottom()
                .fill()
                .colspan(2)
                ;
        
        if (game.debugMode) {
            uiRootTable.debugCell();
            infoBoadArea.debugCell();
        }
    }
    
    @Override
    protected void lazyInitBackUiAndPopupUiContent() {
        
        this.backgroundImageBox = new BackgroundImageBox<TextUmaGame>(this);
        backUiStage.addActor(backgroundImageBox);
        
        secondaryInfoBoard = new PopupInfoBoard(this);
        popupRootTable.add(secondaryInfoBoard).bottom().expand().row();
        popupRootTable.add(new Image())
                .height(layoutConst.CONSTRUCION_BOARD_ROOT_BOX_HEIGHT);
        
        
        
        achievementMaskBoard = new AchievementMaskBoard<TextUmaGame>(this);
        popupUiStage.addActor(achievementMaskBoard);
        
        if (game.debugMode) {
            popupRootTable.debugCell();
        }
    }


    @Override
    public void dispose() {


    }
    
//    public void infoBoardAsRaceInfo(BaseRaceActionHandler model) {
//        mainInfoBoard.updateAsRaceInfo(model);
//        secondaryInfoBoard.updateAsRaceInfo(model);
//    }
//    
//    public void mainInfoBoardUpdate(HorsePrototype horsePrototype) {
//        mainInfoBoard.updateAsHorseStatus(horsePrototype);
//    }
//    
//    public void infoBoardAsTrainInfo(BaseTrainActionHandler model) {
//        secondaryInfoBoard.updateAsTrainInfo(model);
//    }
//
//    public void infoBoardAsIdle() {
//        String idleGuideText = "details of idle-guide";
//        secondaryInfoBoard.updateAsIdleGuide(idleGuideText);
//    }
//
//
//    public void mainInfoBoardClear() {
//        mainInfoBoard.updateAsClear();
//    }
    


}
