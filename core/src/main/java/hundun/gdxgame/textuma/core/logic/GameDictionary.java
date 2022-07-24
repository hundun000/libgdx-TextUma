package hundun.gdxgame.textuma.core.logic;

import hundun.gdxgame.textuma.share.framework.util.text.IGameDictionary;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class GameDictionary implements IGameDictionary {

    
    public String constructionIdToShowName(String constructionId) {
        switch (constructionId) {
            case UserActionId.START_RACE:
                return "start race";
            case UserActionId.RUNNING_TRAIN:
                return "running train";
            default:
                return "[dic:" + constructionId + "]";
        }
    }
}
