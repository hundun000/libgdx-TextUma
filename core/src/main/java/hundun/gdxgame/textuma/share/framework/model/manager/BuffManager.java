package hundun.gdxgame.textuma.share.framework.model.manager;

import hundun.gdxgame.corelib.base.BaseHundunGame;
import hundun.gdxgame.textuma.core.TextUmaGame;

import java.util.HashMap;
import java.util.Map;



/**
 * @author hundun
 * Created on 2021/11/10
 */
public class BuffManager {

    private TextUmaGame game;

    private Map<String, Integer> buffAmounts = new HashMap<>();
    // ------ replace-lombok ------
    public Map<String, Integer> getBuffAmounts() {
        return buffAmounts;
    }
    public void setBuffAmounts(Map<String, Integer> buffAmounts) {
        this.buffAmounts = buffAmounts;
    }

    public BuffManager(TextUmaGame game) {
        this.game = game;
    }



    public int getBuffAmoutOrDefault(String id) {
        return buffAmounts.getOrDefault(id, 0);
    }

    public void addBuffAmout(String id, int amount) {
        int oldValue = getBuffAmoutOrDefault(id);
        buffAmounts.put(id, oldValue + amount);
        game.getEventManager().notifyBuffChange();
    }


    public int modifyResourceGain(String resourceType, int oldAmout) {
        int newAmout = oldAmout;
        // TODO
        return newAmout;
    }

}
