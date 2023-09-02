package hundun.gdxgame.textuma.share.starter.ui.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hundun.gdxgame.corelib.base.BaseHundunScreen;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.data.RootSaveData;
import hundun.gdxgame.textuma.core.ui.component.TextNinePatchWrapper;
import hundun.gdxgame.textuma.core.ui.component.TextSkinButton;


public abstract class AbstractMenuScreen extends BaseHundunScreen<TextUmaGame, RootSaveData> {


//    private MenuComponent<String> menu;
//
//    private GlyphLayout menuText;

    int BUTTON_WIDTH = 100;
    int BUTTON_BIG_HEIGHT = 100;
    int BUTTON_SMALL_HEIGHT = 75;

    final InputListener buttonContinueGameInputListener;
    final InputListener buttonNewGameInputListener;

    String titleText;
    Label titleLabel;
    Image backImage;
    TextSkinButton buttonContinueGame;
    TextSkinButton buttonNewGame;
    TextSkinButton buttonIntoSettingScreen;

    public AbstractMenuScreen(TextUmaGame game,
                              String titleText,
                              InputListener buttonContinueGameInputListener,
                              InputListener buttonNewGameInputListener) {
        super(game, game.getSharedViewport());
        this.titleText = titleText;
        this.buttonContinueGameInputListener = buttonContinueGameInputListener;
        this.buttonNewGameInputListener = buttonNewGameInputListener;
        

    }

    private void initScene2d() {

        this.titleLabel = new Label(
                JavaFeatureForGwt.stringFormat("     %s     ", titleText), 
                game.getMainSkin());
        titleLabel.setFontScale(1.5f);
        this.backImage = new Image(game.getTextureManager().getMenuTexture());
        
        buttonContinueGame = TextSkinButton.typeButton("Continue game", game);
        buttonContinueGame.addListener(buttonContinueGameInputListener);

        buttonNewGame = TextSkinButton.typeButton("New game", game);
        buttonNewGame.addListener(buttonNewGameInputListener);
        
        
        backUiStage.clear();
        uiRootTable.clear();
        
        backUiStage.addActor(backImage);

        TextNinePatchWrapper titleTextNinePatchWrapper = TextNinePatchWrapper.build(game, titleLabel);
        uiRootTable.add(titleTextNinePatchWrapper)
                .row();
        
        if (game.getSaveHandler().hasContinuedGameplaySave()) {
            uiRootTable.add(buttonContinueGame)
                .height(BUTTON_BIG_HEIGHT)
                .fillY()
                .padTop(10)
                .row();
        }
        
        uiRootTable.add(buttonNewGame)
            .height(game.getSaveHandler().hasContinuedGameplaySave() ? BUTTON_SMALL_HEIGHT : BUTTON_BIG_HEIGHT)
            .fillY()
            .padTop(10)
            .row();
        
        uiRootTable.debugAll();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(uiStage);
        game.getBatch().setProjectionMatrix(uiStage.getViewport().getCamera().combined);
        
        initScene2d();
    }

    @Override
    public void dispose() {}

    @Override
    protected void create() {

    }
}