package hundun.gdxgame.textuma.core;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ray3k.stripe.FreeTypeSkin;
import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.textuma.core.data.RootSaveData;
import hundun.gdxgame.textuma.core.data.TextUmaSaveHandler;
import hundun.gdxgame.textuma.core.logic.GameDictionary;
import hundun.gdxgame.textuma.core.logic.manager.TextUmaGameplayUIController;
import hundun.gdxgame.textuma.core.logic.manager.TextureManager;
import hundun.gdxgame.textuma.core.ui.screen.ScreenContext;
import hundun.gdxgame.textuma.share.framework.data.ChildGameConfig;
import hundun.gdxgame.textuma.share.framework.model.ManagerContext;
import hundun.gdxgame.textuma.share.framework.model.manager.AudioPlayManager;
import hundun.gdxgame.textuma.share.framework.model.manager.EventManager;
import hundun.gdxgame.textuma.share.framework.util.text.IGameDictionary;
import hundun.gdxgame.textuma.share.framework.util.text.TextFormatTool;
import hundun.gdxgame.textuma.share.starter.ui.screen.menu.TextUmaMenuScreen;
import lombok.Getter;


public class TextUmaGame extends BaseHundunGame<RootSaveData> {

    public static final String GAME_WORD_SKIN_KEY = "game-word";
    public static final String GREEN_LABEL_STYLE_KEY = "misan-normal-green-small";

    public static final String NO_MORE_RACE_MESSAGE = "No more race-day. This is the end of the demo version.";
    public static final String SINGLETON_ID = "LIBGDX_GAMEPLAY_FRONTEND";

    @Getter
    private ScreenContext screenContext;
    @Getter
    private ManagerContext managerContext;
    @Getter
    private EventManager eventManager;
    @Getter
    protected TextFormatTool textFormatTool;
    @Getter
    private Viewport sharedViewport;
    @Getter
    protected IGameDictionary gameDictionary;
    @Getter
    protected TextureManager textureManager;
    @Getter
    private AudioPlayManager audioPlayManager;

    @Getter
    TextUmaGameplayUIController gameplayUIController;

    public TextUmaGame(ISaveTool<RootSaveData> saveTool) {
        super(640, 480);

        this.sharedViewport = new ScreenViewport();
        this.textFormatTool = new TextFormatTool();
        this.saveHandler = new TextUmaSaveHandler(this.frontend, saveTool);
        this.mainSkinFilePath = null;
        this.gameDictionary = new GameDictionary(this);
        this.textureManager = new TextureManager();
        this.screenContext = new ScreenContext();
        this.managerContext = new ManagerContext(this);
        this.eventManager = new EventManager();
        this.audioPlayManager = new AudioPlayManager(this);
    }

    @Override
    protected void createStage1() {
        super.createStage1();
        this.mainSkin = new FreeTypeSkin(Gdx.files.internal("skins/TextUma/skin.json"));

        this.gameplayUIController = new TextUmaGameplayUIController(this, screenContext);
        this.getSaveHandler().registerSubHandler(gameplayUIController);
        saveHandler.systemSettingLoadOrStarter();
    }

    @Override
    protected void createStage2() {
        textureManager.lazyInitOnGameCreateStage2();
        screenContext.lazyInit(this);
    }

    @Override
    protected void createStage3() {
        ChildGameConfig childGameConfig = new TextUmaGameConfig(this);
        managerContext.lazyInitOnGameCreate(childGameConfig);
        audioPlayManager.lazyInit(childGameConfig.getScreenIdToFilePathMap());


        screenManager.pushScreen(TextUmaMenuScreen.class.getSimpleName(), "blending_transition");
        getAudioPlayManager().intoScreen(TextUmaMenuScreen.class.getSimpleName());
    }

    @Override
    public void dispose() {
        super.dispose();

        saveHandler.gameSaveCurrent();
    }

}
