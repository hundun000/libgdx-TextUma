package hundun.gdxgame.textuma.share.framework.model.construction;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public class BaseConstructionFactory {

    Map<String, UmaActionHandler> constructions = new HashMap<>();
    protected BaseHundunGame game;

    public void lazyInit(List<UmaActionHandler> constructions) {
        constructions.forEach(item -> register(item));
    }

    protected void register(UmaActionHandler construction) {
        constructions.put(construction.getId(), construction);
    }

    public UmaActionHandler getConstruction(String id) {
        UmaActionHandler result = constructions.get(id);
        if (result == null) {
            throw new RuntimeException("getConstruction " + id + " not found");
        }
        return result;
    }

    public Collection<UmaActionHandler> getConstructions() {
        return constructions.values();
    }
}
