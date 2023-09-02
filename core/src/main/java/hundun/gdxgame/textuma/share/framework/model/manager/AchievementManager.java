package hundun.gdxgame.textuma.share.framework.model.manager;
/**
 * @author hundun
 * Created on 2021/11/12
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.share.framework.listener.IBuffChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.IGameStartListener;
import hundun.gdxgame.textuma.share.framework.listener.IOneFrameResourceChangeListener;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;

import java.util.Set;

public class AchievementManager implements IBuffChangeListener, IOneFrameResourceChangeListener, IGameStartListener {
    TextUmaGame game;

    Map<String, AchievementPrototype> prototypes = new HashMap<>();

    Set<String> unlockedAchievementNames = new HashSet<>();
    // ------ replace-lombok ------
    public Set<String> getUnlockedAchievementNames() {
        return unlockedAchievementNames;
    }
    public void setUnlockedAchievementNames(Set<String> unlockedAchievementNames) {
        this.unlockedAchievementNames = unlockedAchievementNames;
    }


    public AchievementManager(TextUmaGame game) {
        this.game = game;
        game.getEventManager().registerListener(this);
    }

    public void addPrototype(AchievementPrototype prototype) {
        prototypes.put(prototype.getName(), prototype);
    }

    private boolean checkRequiredResources(Map<String, Integer> requiredResources) {
        if (requiredResources == null) {
            return true;
        }
        for (Entry<String, Integer> entry : requiredResources.entrySet()) {
            long own = game.getManagerContext().getStorageManager().getResourceNumOrZero(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRequiredBuffs(Map<String, Integer> map) {
        if (map == null) {
            return true;
        }
        for (Entry<String, Integer> entry : map.entrySet()) {
            int own = game.getManagerContext().getBuffManager().getBuffAmoutOrDefault(entry.getKey());
            if (own < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    private void checkAllAchievementUnlock() {
        //Gdx.app.log(this.getClass().getSimpleName(), "checkAllAchievementUnlock");
        for (AchievementPrototype prototype : prototypes.values()) {
            if (unlockedAchievementNames.contains(prototype.getName())) {
                continue;
            }
            boolean resourceMatched = checkRequiredResources(prototype.getRequiredResources());
            boolean buffMatched = checkRequiredBuffs(prototype.getRequiredBuffs());
            boolean allMatched = resourceMatched && buffMatched;
            if (allMatched) {
                unlockedAchievementNames.add(prototype.getName());
                game.getEventManager().notifyAchievementUnlock(prototype);
            }
        }
    }


    @Override
    public void onBuffChange() {
        checkAllAchievementUnlock();
    }

    public void lazyInit(List<AchievementPrototype> achievementPrototypes) {
        achievementPrototypes.forEach(item -> addPrototype(item));
    }

    @Override
    public void onResourceChange(Map<String, Long> changeMap) {
        checkAllAchievementUnlock();
    }

    @Override
    public void onGameStart() {
        checkAllAchievementUnlock();
    }



}
