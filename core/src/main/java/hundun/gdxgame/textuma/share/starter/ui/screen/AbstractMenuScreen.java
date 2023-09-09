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

    protected int BUTTON_WIDTH = 100;
    protected int BUTTON_BIG_HEIGHT = 100;
    protected int BUTTON_SMALL_HEIGHT = 75;

    protected final InputListener buttonContinueGameInputListener;
    protected final InputListener buttonNewGameInputListener;

    protected String titleText;
    protected Label titleLabel;
    protected Image backImage;
    protected TextSkinButton buttonContinueGame;
    protected TextSkinButton buttonNewGame;
    protected TextSkinButton buttonIntoSettingScreen;

    public AbstractMenuScreen(TextUmaGame game,
                              String titleText,
                              InputListener buttonContinueGameInputListener,
                              InputListener buttonNewGameInputListener) {
        super(game, game.getSharedViewport());
        this.titleText = titleText;
        this.buttonContinueGameInputListener = buttonContinueGameInputListener;
        this.buttonNewGameInputListener = buttonNewGameInputListener;
        

    }

    protected abstract void initScene2d();

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