package hundun.gdxgame.textuma.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;

import hundun.gdxgame.textuma.share.framework.data.UmaUserActionHandlerSaveData;
import hundun.gdxgame.textuma.share.framework.data.SaveData;
import hundun.gdxgame.textuma.share.framework.model.ManagerContext;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.framework.util.save.ISaveTool;




/**
 * @author hundun
 * Created on 2021/11/10
 */
public class GwtPreferencesSaveTool implements ISaveTool {
    String preferencesName;
    Preferences preferences;
    private static final String ROOT_KEY = "root";
    private SaveDataMapper objectMapper;
    
    public static interface SaveDataMapper extends ObjectMapper<SaveData> {}
    
    
    public GwtPreferencesSaveTool(String preferencesName) {
        this.preferencesName = preferencesName;
        this.objectMapper = GWT.create(SaveDataMapper.class);
    }
    

    
    private static void appendConstructionSaveData(Map<String, UmaUserActionHandlerSaveData> map, UmaActionHandler construction) {
        map.put(construction.getSaveDataKey(), construction.getSaveData());
    }
    
    private static void loadConstructionSaveData(Map<String, UmaUserActionHandlerSaveData> map, UmaActionHandler construction) {
        construction.setSaveData(map.getOrDefault(construction.getSaveDataKey(), new UmaUserActionHandlerSaveData()));
        construction.updateModifiedValues();
    }
    
    
    @Override
    public void save(ManagerContext modelContext) {
        
        SaveData saveData = new SaveData();
        saveData.setOwnResoueces(modelContext.getStorageManager().getOwnResoueces());
        saveData.setUnlockedResourceTypes(modelContext.getStorageManager().getUnlockedResourceTypes());
        saveData.setBuffAmounts(modelContext.getBuffManager().getBuffAmounts());
        saveData.setUnlockedAchievementNames(modelContext.getAchievementManager().getUnlockedAchievementNames());
        Map<String, UmaUserActionHandlerSaveData> map = new HashMap<>();
        Collection<UmaActionHandler> constructions = modelContext.getConstructionFactory().getConstructions();
        for (UmaActionHandler construction : constructions) {
            appendConstructionSaveData(map, construction);
        }
        saveData.setConstructionSaveDataMap(map);
        
        
        try {
            preferences.putString(ROOT_KEY, objectMapper.write(saveData));
            preferences.flush();
            Gdx.app.log(getClass().getSimpleName(), "save() done");
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), "save() error", e);
        }
        
    }
    
    @Override
    public boolean hasSave() {
        return preferences != null && preferences.contains(ROOT_KEY);
    }
    

    
    @Override
    public void load(ManagerContext modelContext) {
        
        if (!hasSave()) {
            Gdx.app.log(getClass().getSimpleName(), "no savefile, load() do nothing");
            return;
        }
        
        SaveData saveData;
        try {
            String date = preferences.getString(ROOT_KEY);
            saveData = objectMapper.read(date);
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), "load() error", e);
            return;
        }
        
        modelContext.getStorageManager().setOwnResoueces(saveData.getOwnResoueces());
        modelContext.getStorageManager().setUnlockedResourceTypes(saveData.getUnlockedResourceTypes());
        modelContext.getBuffManager().setBuffAmounts(saveData.getBuffAmounts());
        modelContext.getAchievementManager().setUnlockedAchievementNames(saveData.getUnlockedAchievementNames());
        Map<String, UmaUserActionHandlerSaveData> map = saveData.getConstructionSaveDataMap();
        Collection<UmaActionHandler> constructions = modelContext.getConstructionFactory().getConstructions();
        for (UmaActionHandler construction : constructions) {
            loadConstructionSaveData(map, construction);
        }

        Gdx.app.log(getClass().getSimpleName(), "load() done");
    }



    @Override
    public void lazyInitOnGameCreate() {
        this.preferences = Gdx.app.getPreferences(preferencesName);
    }
}
