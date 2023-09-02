package hundun.gdxgame.textuma.share.framework.model.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;

import java.util.Set;

/**
 * @author hundun
 * Created on 2021/11/02
 */
public class StorageManager {

    TextUmaGame game;

    Map<String, Long> ownResoueces = new HashMap<>();
    // ------ replace-lombok ------
    public Map<String, Long> getOwnResoueces() {
        return ownResoueces;
    }
    public void setOwnResoueces(Map<String, Long> ownResoueces) {
        this.ownResoueces = ownResoueces;
    }

    Set<String> unlockedResourceTypes = new HashSet<>();
    // ------ replace-lombok ------
    public Set<String> getUnlockedResourceTypes() {
        return unlockedResourceTypes;
    }
    public void setUnlockedResourceTypes(Set<String> unlockedResourceTypes) {
        this.unlockedResourceTypes = unlockedResourceTypes;
    }

    Map<String, Long> oneFrameDeltaResoueces = new HashMap<>();

    public StorageManager(TextUmaGame game) {
        this.game = game;
    }

    public String getResourceDescription(String key) {
        long amount = getResourceNumOrZero(key);
        return key + ": " + amount;
    }

    public long getResourceNumOrZero(String key) {
        return ownResoueces.getOrDefault(key, 0L);
    }



    /**
     * @param plus ture: plus the map; false: minus the map;
     */
    public void modifyAllResourceNum(Map<String, Long> map, boolean plus) {
        //Gdx.app.log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + ": " + map);
        for (Entry<String, Long> entry : map.entrySet()) {
            unlockedResourceTypes.add(entry.getKey());
            ownResoueces.merge(entry.getKey(), (plus ? 1 : -1 ) * entry.getValue(), (oldValue, newValue) -> oldValue + newValue);
            oneFrameDeltaResoueces.merge(entry.getKey(), (plus ? 1 : -1 ) * entry.getValue(), (oldValue, newValue) -> oldValue + newValue);
        }
        //game.getEventManager().notifyResourceAmountChange(false);
    }

    public void modifyAllResourceNum(List<ResourcePair> packs, boolean plus) {
        //Gdx.app.log(this.getClass().getSimpleName(), (plus ? "plus" : "minus") + ": " + packs);
        for (ResourcePair pack : packs) {
            unlockedResourceTypes.add(pack.getType());
            ownResoueces.merge(pack.getType(), (plus ? 1 : -1 ) * pack.getAmount(), (oldValue, newValue) -> oldValue + newValue);
            oneFrameDeltaResoueces.merge(pack.getType(), (plus ? 1 : -1 ) * pack.getAmount(), (oldValue, newValue) -> oldValue + newValue);
        }
        //game.getEventManager().notifyResourceAmountChange(false);
    }

    public boolean isEnough(List<ResourcePair> pairs) {
        for (ResourcePair pair : pairs) {
            long own = this.getResourceNumOrZero(pair.getType());
            if (own < pair.getAmount()) {
                return false;
            }
        }
        return true;
    }

    public Map<String, Long> frameDeltaAmountClear() {
        Map<String, Long> temp = new HashMap<>(oneFrameDeltaResoueces);
        oneFrameDeltaResoueces.clear();
        //Gdx.app.log(this.getClass().getSimpleName(), "frameDeltaAmountClear: " + temp);
        game.getEventManager().notifyOneFrameResourceChange(temp);
        return temp;
    }

//    public void addResourceNum(String key, int add) {
//        Gdx.app.log(this.getClass().getSimpleName(), "addResourceNum:" + key + " " + add);
//        ownResoueces.merge(key, add, (oldValue, newValue) -> oldValue + newValue);
//        game.getEventManager().notifyResourceAmountChange(false);
//    }

}
