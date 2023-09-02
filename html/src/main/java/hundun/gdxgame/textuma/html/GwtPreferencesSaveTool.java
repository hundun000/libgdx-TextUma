package hundun.gdxgame.textuma.html;

import com.badlogic.gdx.Gdx;
import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;

import hundun.gdxgame.corelib.base.save.AbstractSaveDataSaveTool;
import hundun.gdxgame.textuma.core.data.RootSaveData;


/**
 * @author hundun
 * Created on 2021/11/10
 */
public class GwtPreferencesSaveTool extends AbstractSaveDataSaveTool<RootSaveData> {

    
    private SaveDataMapper objectMapper;

    public static interface SaveDataMapper extends ObjectMapper<RootSaveData> {}
    
    
    public GwtPreferencesSaveTool(String preferencesName) {
        super(preferencesName);
        this.objectMapper = GWT.create(SaveDataMapper.class);
    }


    @Override
    public void writeRootSaveData(RootSaveData saveData) {
        try {
            preferences.putString(ROOT_KEY, objectMapper.write(saveData));
            preferences.flush();
            Gdx.app.log(getClass().getSimpleName(), "save() done");
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), "save() error", e);
        }
    }



    @Override
    public RootSaveData readRootSaveData() {

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
