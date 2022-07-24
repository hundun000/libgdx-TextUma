package hundun.gdxgame.textuma.share.framework.util.save;

import hundun.gdxgame.textuma.share.framework.data.RootSaveData;
import hundun.gdxgame.textuma.share.framework.model.ManagerContext;

/**
 * @author hundun
 * Created on 2022/04/08
 */
public interface ISaveTool {
    void lazyInitOnGameCreate();
    boolean hasSave();
    void saveRootSaveData(RootSaveData saveData);
    RootSaveData loadRootSaveData();
}