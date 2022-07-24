package hundun.gdxgame.textuma.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import hundun.gdxgame.textuma.core.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.textuma.core.logic.ConstructionId;
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.GameDictionary;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.ScreenId;
import hundun.gdxgame.textuma.core.logic.manager.TextureManager;
import hundun.gdxgame.textuma.core.ui.screen.PlayScreen;
import hundun.gdxgame.textuma.core.ui.screen.ScreenContext;
import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.data.ChildGameConfig;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;
import hundun.gdxgame.textuma.share.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.textuma.share.framework.util.save.ISaveTool;
import hundun.gdxgame.textuma.share.framework.util.text.TextFormatTool;
import hundun.gdxgame.textuma.share.starter.ui.screen.menu.MenuScreen;


public class TextUmaGame extends BaseIdleGame {


    private ScreenContext screenContext;
    // ------ replace-lombok ------
    public ScreenContext getScreenContext() {
        return screenContext;
    }
    
    
    public TextUmaGame(ISaveTool saveTool) {
        super(640, 480, saveTool);
        //this.skinFilePath = "skins/orange/skin/uiskin.json";
        desktopScale = 1;
        drawGameImageAndPlayAudio = true;
        debugMode = false;
    }
    
    @Override
    protected ChildGameConfig getChildGameConfig() {
        return new IdleDemoGameConfig(this);
    }
    
    @Override
    public void create () {
        super.create();
       
        setScreen(screenContext.getMenuScreen());
        getAudioPlayManager().intoScreen(screenContext.getMenuScreen().getScreenId());
    }
    
    @Override
    protected void initContexts() {
        super.initContexts();
        
        this.gameDictionary = new GameDictionary();
        this.textureManager = new TextureManager();
        
        this.screenContext = new ScreenContext();
        screenContext.setMenuScreen(new MenuScreen<>(
                this,
                ScreenId.MENU,
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        TextUmaGame.this.loadAndHookSave(true);
                        TextUmaGame.this.setScreen(TextUmaGame.this.getScreenContext().getGameBeeScreen());
                        TextUmaGame.this.getAudioPlayManager().intoScreen(ScreenId.PLAY);
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                },
                new InputListener(){
                    @Override
                    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                        TextUmaGame.this.loadAndHookSave(false);
                        TextUmaGame.this.setScreen(TextUmaGame.this.getScreenContext().getGameBeeScreen());
                        TextUmaGame.this.getAudioPlayManager().intoScreen(ScreenId.PLAY);
                    }
                    @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                }
        ));
        screenContext.setGameBeeScreen(new PlayScreen(this));
    }

    @Override
    public List<String> getGameAreaValues() {
        return GameArea.values;
    }
    
}
