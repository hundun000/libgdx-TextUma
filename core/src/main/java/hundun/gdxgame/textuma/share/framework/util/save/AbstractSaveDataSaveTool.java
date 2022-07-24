package hundun.gdxgame.textuma.share.framework.util.save;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import hundun.gdxgame.textuma.share.framework.data.RootSaveData;
import hundun.gdxgame.textuma.share.framework.data.UmaUserActionHandlerSaveData;
import hundun.gdxgame.textuma.share.framework.model.ManagerContext;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2022/08/04
 */
public abstract class AbstractSaveDataSaveTool implements ISaveTool {
    
    protected String preferencesName;
    protected Preferences preferences;
    protected static final String ROOT_KEY = "root";
    
    public AbstractSaveDataSaveTool(String preferencesName) {
        this.preferencesName = preferencesName;
    }
    
    @Override
    public boolean hasSave() {
        return preferences != null && preferences.contains(ROOT_KEY);
    }
    
    @Override
    public void lazyInitOnGameCreate() {
        this.preferences = Gdx.app.getPreferences(preferencesName);
    }
    
    
    
    
}
