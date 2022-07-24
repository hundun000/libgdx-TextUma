package hundun.gdxgame.textuma.desktop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import hundun.gdxgame.textuma.share.framework.data.SaveData;
import hundun.gdxgame.textuma.share.framework.model.ModelContext;
import hundun.gdxgame.textuma.share.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.textuma.share.framework.util.save.ISaveTool;
import hundun.gdxgame.textuma.share.framework.data.ConstructionSaveData;


/**
 * @author hundun
 * Created on 2021/11/10
 */
public class PreferencesSaveTool implements ISaveTool {
    String preferencesName;
    Preferences preferences;
    private static final String ROOT_KEY = "root";
    private ObjectMapper objectMapper;
    
    public PreferencesSaveTool(String preferencesName) {
        this.preferencesName = preferencesName;
        this.objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                ;
        
    }
    

    
    private static void appendConstructionSaveData(Map<String, ConstructionSaveData> map, BaseConstruction construction) {
        map.put(construction.getSaveDataKey(), construction.getSaveData());
    }
    
    private static void loadConstructionSaveData(Map<String, ConstructionSaveData> map, BaseConstruction construction) {
        construction.setSaveData(map.getOrDefault(construction.getSaveDataKey(), new ConstructionSaveData()));
        construction.updateModifiedValues();
    }
    
    
    @Override
    public void save(ModelContext modelContext) {
        
        SaveData saveData = new SaveData();
        saveData.setOwnResoueces(modelContext.getStorageManager().getOwnResoueces());
        saveData.setUnlockedResourceTypes(modelContext.getStorageManager().getUnlockedResourceTypes());
        saveData.setBuffAmounts(modelContext.getBuffManager().getBuffAmounts());
        saveData.setUnlockedAchievementNames(modelContext.getAchievementManager().getUnlockedAchievementNames());
        Map<String, ConstructionSaveData> map = new HashMap<>();
        Collection<BaseConstruction> constructions = modelContext.getConstructionFactory().getConstructions();
        for (BaseConstruction construction : constructions) {
            appendConstructionSaveData(map, construction);
        }
        saveData.setConstructionSaveDataMap(map);
        
        
        try {
            preferences.putString(ROOT_KEY, objectMapper.writeValueAsString(saveData));
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
    public void load(ModelContext modelContext) {
        
        if (!hasSave()) {
            Gdx.app.log(getClass().getSimpleName(), "no savefile, load() do nothing");
            return;
        }
        
        SaveData saveData;
        try {
            String date = preferences.getString(ROOT_KEY);
            saveData = objectMapper.readValue(date, SaveData.class);
        } catch (IOException e) {
            Gdx.app.error(getClass().getSimpleName(), "load() error", e);
            return;
        }
        
        modelContext.getStorageManager().setOwnResoueces(saveData.getOwnResoueces());
        modelContext.getStorageManager().setUnlockedResourceTypes(saveData.getUnlockedResourceTypes());
        modelContext.getBuffManager().setBuffAmounts(saveData.getBuffAmounts());
        modelContext.getAchievementManager().setUnlockedAchievementNames(saveData.getUnlockedAchievementNames());
        Map<String, ConstructionSaveData> map = saveData.getConstructionSaveDataMap();
        Collection<BaseConstruction> constructions = modelContext.getConstructionFactory().getConstructions();
        for (BaseConstruction construction : constructions) {
            loadConstructionSaveData(map, construction);
        }

        Gdx.app.log(getClass().getSimpleName(), "load() done");
    }



    @Override
    public void lazyInitOnGameCreate() {
        this.preferences = Gdx.app.getPreferences(preferencesName);
    }
}
