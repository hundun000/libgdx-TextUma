package hundun.gdxgame.textuma.share.starter.ui.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BaseScreen;



public class MenuScreen<T_GAME extends BaseIdleGame> extends BaseScreen<T_GAME> {


//    private MenuComponent<String> menu;
//
//    private GlyphLayout menuText;

    int BUTTON_WIDTH = 100;
    int BUTTON_BIG_HEIGHT = 100;
    int BUTTON_SMALL_HEIGHT = 75;

    final InputListener buttonContinueGameInputListener;
    final InputListener buttonNewGameInputListener;

    Button buttonContinueGame;
    Button buttonNewGame;
    Button buttonIntoSettingScreen;
    Table table;
    public MenuScreen(T_GAME game, String screenId, InputListener buttonContinueGameInputListener, InputListener buttonNewGameInputListener) {
        super(game, screenId);
        this.buttonContinueGameInputListener = buttonContinueGameInputListener;
        this.buttonNewGameInputListener = buttonNewGameInputListener;
        initScene2d();

    }

    private void initScene2d() {

        Image backImage = new Image(game.getTextureManager().getMenuTexture());
        if (game.drawGameImageAndPlayAudio) {
            uiStage.addActor(backImage);
        }

        table = new Table();
        table.setFillParent(true);
        //table.setBounds((game.LOGIC_WIDTH - BUTTON_WIDTH)/2, 0, BUTTON_WIDTH, game.LOGIC_HEIGHT / 2);
        uiStage.addActor(table);

        buttonContinueGame = new TextButton("Continue Game", game.getButtonSkin());
        //buttonContinueGame.setSize(100, 100);
        //buttonContinueGame.setPosition(0, 0);
        buttonContinueGame.addListener(buttonContinueGameInputListener);
        if (game.getSaveTool().hasSave()) {
            table.add(buttonContinueGame)
                .height(BUTTON_BIG_HEIGHT)
                .fill()
                .pad(10)
                .row();
        }



        buttonNewGame = new TextButton("New Game", game.getButtonSkin());
        buttonNewGame.addListener(buttonNewGameInputListener);
        table.add(buttonNewGame)
            .height(game.getSaveTool().hasSave() ? BUTTON_SMALL_HEIGHT : BUTTON_BIG_HEIGHT)
            .fill()
            .pad(10)
            .row();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiStage);

        game.getBatch().setProjectionMatrix(uiStage.getViewport().getCamera().combined);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);





        uiStage.act();
        uiStage.draw();
    }



    @Override
    public void dispose() {}
}