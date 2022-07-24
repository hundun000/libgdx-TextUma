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
import hundun.gdxgame.textuma.share.framework.data.RootSaveData;
import hundun.gdxgame.textuma.share.framework.model.ManagerContext;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.framework.util.save.AbstractSaveDataSaveTool;
import hundun.gdxgame.textuma.share.framework.util.save.ISaveTool;




/**
 * @author hundun
 * Created on 2021/11/10
 */
public class GwtPreferencesSaveTool extends AbstractSaveDataSaveTool {

    
    private SaveDataMapper objectMapper;
    
    public static interface SaveDataMapper extends ObjectMapper<RootSaveData> {}
    
    
    public GwtPreferencesSaveTool(String preferencesName) {
        super(preferencesName);
        this.objectMapper = GWT.create(SaveDataMapper.class);
    }


    @Override
    public void saveRootSaveData(RootSaveData saveData) {
        try {
            preferences.putString(ROOT_KEY, objectMapper.write(saveData));
            preferences.flush();
            Gdx.app.log(getClass().getSimpleName(), "save() done");
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), "save() error", e);
        }
    }



    @Override
    public RootSaveData loadRootSaveData() {

        try {
            String date = preferences.getString(ROOT_KEY);
            RootSaveData saveData = objectMapper.read(date);
            return saveData;
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), "load() error", e);
            return null;
        }
        
    }
}
