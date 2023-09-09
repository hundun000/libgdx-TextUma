package hundun.gdxgame.textuma.core.logic;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.share.framework.util.text.IGameDictionary;
import hundun.gdxgame.textuma.share.framework.util.text.Language;

import java.util.List;
import java.util.Map;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class GameDictionary implements IGameDictionary {

    TextUmaGame game;
    public GameDictionary(TextUmaGame game) {
        this.game = game;
    }

    public String constructionIdToShowName(String constructionId) {
        switch (constructionId) {
            case UserActionId.START_RACE:
                return "Start";
            case UserActionId.NEXT_RACE_RECORD_NODE:
                return "Skip";
            case UserActionId.END_RACE_RECORD:
                return "End";
            case UserActionId.REPLAY_RACE_RECORD:
                return "Replay";
            case UserActionId.RUNNING_TRAIN:
                return "Speed-train";
            case UserActionId.SWIMMING_TRAIN:
                return "Stamina-train";
            case UserActionId.POWER_TRAIN:
                return "Power-train";
            case UserActionId.FREE_TRAIN:
                return "Free-train";
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
//            case GameArea.AREA_RECORD:
//                return "area record";
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
                return "Money";
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
    
    @Override
    public String gameWordToShowName(String gameWord) {
        switch (gameWord) {
            case GameWord.RUN_STRATEGY:
                return "Strategy";
            default:
                return "[dic:" + gameWord + "]";
        }
    }
    
    public static class GameWord {
        public static final String RUN_STRATEGY = "ENUM_GW@RUN_STRATEGY";
    }

    @Override
    public Map<Language, String> getLanguageShowNameMap() {
        return JavaFeatureForGwt.mapOf(
                Language.CN, "中文",
                Language.EN, "English"
        );
    }

    @Override
    public List<String> getMemuScreenTexts() {
        return JavaFeatureForGwt.arraysAsList("IdleDemo", "New Game", "Continue", "Language", "Take effect after restart");
    }
}
