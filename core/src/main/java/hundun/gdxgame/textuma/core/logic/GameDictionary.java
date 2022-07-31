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
            case UserActionId.NEXT_RACE_RECORD_NODE:
                return "skip";
            case UserActionId.END_RACE_RECORD:
                return "end";
            case UserActionId.REPLAY_RACE_RECORD:
                return "replay";
            case UserActionId.RUNNING_TRAIN:
                return "running train";
            default:
                return "[dic:" + constructionId + "]";
        }
    }

    @Override
    public String gameAreaIdToShowName(String gameAreaId) {
        switch (gameAreaId) {
            case GameArea.AREA_RACE:
                return "area race";
            case GameArea.AREA_TRAIN:
                return "area train";
            case GameArea.AREA_RECORD:
                return "area record";
            default:
                return "[dic:" + gameAreaId + "]";
        }
    }

    @Override
    public String resourceIdToShowName(String resourceId) {
        switch (resourceId) {
            case ResourceType.TURN:
                return "Day";
            case ResourceType.COIN:
                return "Coin";
            case ResourceType.HORSE_SPEED:
                return "Speed";
            case ResourceType.HORSE_STAMINA:
                return "Stamina";
            case ResourceType.HORSE_POWER:
                return "Power";
            default:
                return "[dic:" + resourceId + "]";
        }
    }
}
