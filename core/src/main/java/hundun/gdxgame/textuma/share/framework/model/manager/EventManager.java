package hundun.gdxgame.textuma.share.framework.model.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.share.framework.listener.IAchievementUnlockListener;
import hundun.gdxgame.textuma.share.framework.listener.IBuffChangeListener;
import hundun.gdxgame.textuma.share.framework.listener.IGameStartListener;
import hundun.gdxgame.textuma.share.framework.listener.IOneFrameResourceChangeListener;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;



/**
 * @author hundun
 * Created on 2021/11/12
 */
public class EventManager {
    List<IGameStartListener> gameStartListeners = new ArrayList<>();
    List<IBuffChangeListener> buffChangeListeners = new ArrayList<>();
    List<IAchievementUnlockListener> achievementUnlockListeners = new ArrayList<>();
    List<IOneFrameResourceChangeListener> oneFrameResourceChangeListeners = new ArrayList<>();

    public void registerListener(Object listener) {
        if (listener instanceof IGameStartListener && !gameStartListeners.contains(listener)) {
            gameStartListeners.add((IGameStartListener) listener);
        }
        if (listener instanceof IBuffChangeListener && !buffChangeListeners.contains(listener)) {
            buffChangeListeners.add((IBuffChangeListener) listener);
        }
        if (listener instanceof IAchievementUnlockListener && !achievementUnlockListeners.contains(listener)) {
            achievementUnlockListeners.add((IAchievementUnlockListener) listener);
        }
        if (listener instanceof IOneFrameResourceChangeListener && !oneFrameResourceChangeListeners.contains(listener)) {
            oneFrameResourceChangeListeners.add((IOneFrameResourceChangeListener) listener);
        }
    }

    public void notifyGameStart() {
        Gdx.app.log(this.getClass().getSimpleName(), "notifyGameStart");
        for (IGameStartListener listener : gameStartListeners) {
            listener.onGameStart();
        }
    }
    
    public void notifyBuffChange() {
        Gdx.app.log(this.getClass().getSimpleName(), "notifyBuffChange");
        for (IBuffChangeListener listener : buffChangeListeners) {
            listener.onBuffChange();
        }
    }

//    public void notifyResourceAmountChange(boolean fromLoad) {
//        Gdx.app.log(this.getClass().getSimpleName(), "notifyResourceAmountChange");
//        for (IAmountChangeEventListener listener : amountChangeEventListeners) {
//            listener.onResourceChange(fromLoad);
//        }
//    }

    public void notifyOneFrameResourceChange(Map<String, Long> changeMap) {
        //Gdx.app.log(this.getClass().getSimpleName(), "notifyOneFrameResourceChange");
        for (IOneFrameResourceChangeListener listener : oneFrameResourceChangeListeners) {
            listener.onResourceChange(changeMap);
        }
    }

    public void notifyAchievementUnlock(AchievementPrototype prototype) {
        Gdx.app.log(this.getClass().getSimpleName(), "notifyAchievementUnlock");
        for (IAchievementUnlockListener listener : achievementUnlockListeners) {
            listener.onAchievementUnlock(prototype);
        }
    }
}
