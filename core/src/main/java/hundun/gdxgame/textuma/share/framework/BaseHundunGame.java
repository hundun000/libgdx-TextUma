package hundun.gdxgame.textuma.share.framework;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.manager.LibgdxFrontEndSaveData;
import hundun.gdxgame.textuma.core.logic.manager.LibgdxGameplayFrontend;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame.SaveDataHelper;
import hundun.gdxgame.textuma.share.framework.data.ChildGameConfig;
import hundun.gdxgame.textuma.share.framework.data.RootSaveData;
import hundun.gdxgame.textuma.share.framework.data.UmaUserActionHandlerSaveData;
import hundun.gdxgame.textuma.share.framework.model.ManagerContext;
import hundun.gdxgame.textuma.share.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.framework.model.manager.AbstractTextureManager;
import hundun.gdxgame.textuma.share.framework.model.manager.AchievementManager;
import hundun.gdxgame.textuma.share.framework.model.manager.AudioPlayManager;
import hundun.gdxgame.textuma.share.framework.model.manager.BuffManager;
import hundun.gdxgame.textuma.share.framework.model.manager.ConstructionManager;
import hundun.gdxgame.textuma.share.framework.model.manager.EventManager;
import hundun.gdxgame.textuma.share.framework.model.manager.GameEntityManager;
import hundun.gdxgame.textuma.share.framework.model.manager.StorageManager;
import hundun.gdxgame.textuma.share.framework.util.save.ISaveTool;
import hundun.gdxgame.textuma.share.framework.util.text.IGameDictionary;
import hundun.gdxgame.textuma.share.framework.util.text.TextFormatTool;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.game.gameplay.UmaSaveDataFactory;


public abstract class BaseHundunGame extends Game {
    public boolean debugMode = false;
    public boolean drawGameImageAndPlayAudio = true;
    public int desktopScale = 1;
    public final int LOGIC_WIDTH;
    public final int LOGIC_HEIGHT;
    protected String DEFAULT_SKIN_FILA_PATH = "skins/default/skin/uiskin.json";
    protected String skinFilePath;

    private SpriteBatch batch;
    // ------ replace-lombok ------
    public SpriteBatch getBatch() {
        return batch;
    }

    private ManagerContext modelContext;
    // ------ replace-lombok ------
    public ManagerContext getModelContext() {
        return modelContext;
    }

    private EventManager eventManager;
    // ------ replace-lombok ------
    public EventManager getEventManager() {
        return eventManager;
    }

    private AudioPlayManager audioPlayManager;
    // ------ replace-lombok ------
    public AudioPlayManager getAudioPlayManager() {
        return audioPlayManager;
    }

    protected AbstractTextureManager textureManager;
    // ------ replace-lombok ------
    public AbstractTextureManager getTextureManager() {
        return textureManager;
    }

    protected IGameDictionary gameDictionary;
    // ------ replace-lombok ------
    public IGameDictionary getGameDictionary() {
        return gameDictionary;
    }

    private Skin buttonSkin;
    // ------ replace-lombok ------
    public Skin getButtonSkin() {
        return buttonSkin;
    }

    private ISaveTool saveTool;
    // ------ replace-lombok ------
    public ISaveTool getSaveTool() {
        return saveTool;
    }

    protected TextFormatTool textFormatTool;
    // ------ replace-lombok ------
    public TextFormatTool getTextFormatTool() {
        return textFormatTool;
    }

    public BaseHundunGame(int LOGIC_WIDTH, int LOGIC_HEIGHT, ISaveTool saveTool) {
        this.LOGIC_WIDTH = LOGIC_WIDTH;
        this.LOGIC_HEIGHT = LOGIC_HEIGHT;
        this.saveTool = saveTool;
        this.textFormatTool = new TextFormatTool();
    }

	@Override
	public void create() {

		this.batch = new SpriteBatch();

		saveTool.lazyInitOnGameCreate();
		initContexts();
		contextsLazyInit();
	}

	public abstract List<String> getGameAreaValues();

	public void loadOrNewGame(boolean load) {

	    RootSaveData saveData;
	    if (load) {
	        saveData = saveTool.loadRootSaveData();
	    } else {
	        saveData = SaveDataHelper.getNewGameSaveData(modelContext);
	    }

	    SaveDataHelper.applySaveData(saveData, modelContext);
	    Gdx.app.log(this.getClass().getSimpleName(), load ? "load game done" : "new game done");
	    eventManager.notifyGameStart();
	}

	public static class SaveDataHelper {
        public static void applySaveData(RootSaveData saveData, ManagerContext modelContext) {
            modelContext.getGameplayFrontend().subApplySaveData(saveData.getUmaSaveData(), saveData.getGameRuleData(), saveData.getFrontEndSaveData()); 
            
            modelContext.getStorageManager().setOwnResoueces(modelContext.getGameplayFrontend().getOwnResoueces());
            modelContext.getStorageManager().setUnlockedResourceTypes(saveData.getUnlockedResourceTypes());
            modelContext.getBuffManager().setBuffAmounts(saveData.getBuffAmounts());
            modelContext.getAchievementManager().setUnlockedAchievementNames(saveData.getUnlockedAchievementNames());
            Map<String, UmaUserActionHandlerSaveData> map = saveData.getConstructionSaveDataMap();
            Collection<UmaActionHandler> constructions = modelContext.getConstructionFactory().getConstructions();
            for (UmaActionHandler construction : constructions) {
                loadConstructionSaveData(map, construction);
            }
        }
        
        private static void loadConstructionSaveData(Map<String, UmaUserActionHandlerSaveData> map, UmaActionHandler construction) {
            construction.setSaveData(map.getOrDefault(construction.getSaveDataKey(), new UmaUserActionHandlerSaveData()));
            construction.updateModifiedValues();
        }
        
	    public static RootSaveData currentSituationToSaveData(ManagerContext modelContext) {
	        RootSaveData saveData = new RootSaveData();
	        saveData.setUnlockedResourceTypes(modelContext.getStorageManager().getUnlockedResourceTypes());
	        saveData.setBuffAmounts(modelContext.getBuffManager().getBuffAmounts());
	        saveData.setUnlockedAchievementNames(modelContext.getAchievementManager().getUnlockedAchievementNames());
	        Map<String, UmaUserActionHandlerSaveData> map = new HashMap<>();
	        Collection<UmaActionHandler> constructions = modelContext.getConstructionFactory().getConstructions();
	        for (UmaActionHandler construction : constructions) {
	            appendConstructionSaveData(map, construction);
	        }
	        saveData.setConstructionSaveDataMap(map);
	        
	        modelContext.getGameplayFrontend().subCurrentSituationToSaveData(saveData);
	        
	        return saveData;
	    }
	    
	    private static void appendConstructionSaveData(Map<String, UmaUserActionHandlerSaveData> map, UmaActionHandler construction) {
	        map.put(construction.getSaveDataKey(), construction.getSaveData());
	    }
	    
	    public static RootSaveData getNewGameSaveData(ManagerContext modelContext) {
	        RootSaveData saveData = new RootSaveData();
	        saveData.setBuffAmounts(new HashMap<>());
	        saveData.setConstructionSaveDataMap(new HashMap<>());
	        saveData.setUnlockedAchievementNames(new HashSet<>());
	        saveData.setUnlockedResourceTypes(new HashSet<>());
	        saveData.setUmaSaveData(JavaFeatureForGwt.mapOf(
	                LibgdxGameplayFrontend.SINGLETON_ID, 
	                UmaSaveDataFactory.forNewAccount(LibgdxGameplayFrontend.SINGLETON_ID)));
	        saveData.setGameRuleData(UmaSaveDataFactory.forNewGameRuleData());
	        saveData.setFrontEndSaveData(new LibgdxFrontEndSaveData());
	        saveData.getUnlockedResourceTypes().add(ResourceType.TURN);
	        saveData.getUnlockedResourceTypes().add(ResourceType.COIN);
	        return saveData;
	    }
	}
	
	


	protected void initContexts() {

	    if (drawGameImageAndPlayAudio && skinFilePath != null) {
            this.buttonSkin = new Skin(Gdx.files.internal(skinFilePath));
        } else {
            this.buttonSkin = new Skin(Gdx.files.internal(DEFAULT_SKIN_FILA_PATH));
        }

        this.modelContext = new ManagerContext();
        this.eventManager = new EventManager();

        modelContext.setStorageManager(new StorageManager(this));
        modelContext.setBuffManager(new BuffManager(this));

        modelContext.setConstructionFactory(new BaseConstructionFactory());
        modelContext.setAchievementManager(new AchievementManager(this));
        modelContext.setConstructionManager(new ConstructionManager(this));
        modelContext.setGameEntityManager(new GameEntityManager(this));

        this.audioPlayManager = new AudioPlayManager(this);


	}



	@Override
	public void dispose () {
		batch.dispose();
		audioPlayManager.dispose();
	}


    private void contextsLazyInit() {
        ChildGameConfig childGameConfig = getChildGameConfig();

        modelContext.getConstructionFactory().lazyInit(childGameConfig.getConstructions());
        modelContext.getConstructionManager().lazyInit(childGameConfig.getAreaControlableConstructionIds());
        modelContext.getGameEntityManager().lazyInit(childGameConfig.getAreaShowEntityByOwnAmountConstructionIds(), childGameConfig.getAreaShowEntityByOwnAmountResourceIds(), childGameConfig.getAreaShowEntityByChangeAmountResourceIds());
        modelContext.getAchievementManager().lazyInit(childGameConfig.getAchievementPrototypes());
        audioPlayManager.lazyInit(childGameConfig.getScreenIdToFilePathMap());


    }
    
    protected abstract ChildGameConfig getChildGameConfig();

    public void saveCurrent() {
        Gdx.app.log(this.getClass().getSimpleName(), "saveCurrent called");
        getSaveTool().saveRootSaveData(SaveDataHelper.currentSituationToSaveData(this.getModelContext()));
    }
}
