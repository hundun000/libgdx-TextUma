package hundun.gdxgame.textuma.share.starter.ui.screen.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.ui.component.TextNinePatchWrapper;
import hundun.gdxgame.textuma.core.ui.component.TextSkinButton;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.framework.util.text.Language;
import hundun.gdxgame.textuma.share.starter.ui.screen.AbstractMenuScreen;

import java.util.List;


public class TextUmaMenuScreen extends AbstractMenuScreen {


//    private MenuComponent<String> menu;
//
//    private GlyphLayout menuText;

    LanguageSwitchBoard languageSwitchBoardVM;

    public TextUmaMenuScreen(TextUmaGame game) {
        super(
                game,
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        game.getSaveHandler().gameplayLoadOrStarter(true);
                        game.getEventManager().notifyGameStart();
                        game.getScreenManager().pushScreen(UmaPlayScreen.class.getSimpleName(), "blending_transition");
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                },
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        game.getSaveHandler().gameplayLoadOrStarter(false);
                        game.getEventManager().notifyGameStart();
                        game.getScreenManager().pushScreen(UmaPlayScreen.class.getSimpleName(), "blending_transition");
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                }
        );


    }

    @Override
    protected void initScene2d() {
        List<String> menuScreenTexts = game.getGameDictionary().getMenuScreenTexts();
        this.titleLabel = new Label(
                JavaFeatureForGwt.stringFormat("     %s     ", menuScreenTexts.get(0)),
                game.getMainSkin());
        titleLabel.setFontScale(2.0f);
        this.backImage = new Image(game.getTextureManager().getMenuTexture());

        buttonContinueGame = TextSkinButton.typeButton(menuScreenTexts.get(2), game);
        buttonContinueGame.addListener(buttonContinueGameInputListener);

        buttonNewGame = TextSkinButton.typeButton(menuScreenTexts.get(1), game);
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


        this.languageSwitchBoardVM = new LanguageSwitchBoard(this,
                Language.values(),
                game.getGameplayUIController().getLanguage(),
                menuScreenTexts.get(3),
                menuScreenTexts.get(4),
                it -> game.getGameplayUIController().setLanguage(it)
        );
        uiRootTable.add(languageSwitchBoardVM)
                .padTop(10);

        if (game.debugMode) {
            uiRootTable.debugAll();
        }
    }

}