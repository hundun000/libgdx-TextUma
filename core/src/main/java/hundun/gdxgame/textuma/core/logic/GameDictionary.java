package hundun.gdxgame.textuma.core.logic;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.share.framework.util.text.IGameDictionary;
import hundun.gdxgame.textuma.share.framework.util.text.Language;
import hundun.simulationgame.umamusume.core.horse.RunStrategyType;
import hundun.simulationgame.umamusume.game.gameplay.UmaSaveDataFactory;

import java.util.List;
import java.util.Map;

/**
 * @author hundun
 * Created on 2021/11/22
 */
public class GameDictionary implements IGameDictionary {
    public static String[] RACE_CN_NAMES = new String[] {
            "短距离赛",
            "英里距离赛",
            "中距离赛A",
            "中距离赛B",
            "长距离赛"
    };
    TextUmaGame game;
    public GameDictionary(TextUmaGame game) {
        this.game = game;
    }

    public String constructionIdToShowName(String constructionId) {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                switch (constructionId) {
                    case UserActionId.START_RACE:
                        return "开始";
                    case UserActionId.NEXT_RACE_RECORD_NODE:
                        return "跳过";
                    case UserActionId.END_RACE_RECORD:
                        return "离开";
                    case UserActionId.REPLAY_RACE_RECORD:
                        return "重放";
                    case UserActionId.RUNNING_TRAIN:
                        return "速度训练";
                    case UserActionId.SWIMMING_TRAIN:
                        return "耐力训练";
                    case UserActionId.POWER_TRAIN:
                        return "力量训练";
                    case UserActionId.FREE_TRAIN:
                        return "免费训练";
                    default:
                        return "口口";
                }
            default:
                switch (constructionId) {
                    case UserActionId.START_RACE:
                        return "Start";
                    case UserActionId.NEXT_RACE_RECORD_NODE:
                        return "Skip";
                    case UserActionId.END_RACE_RECORD:
                        return "Exit";
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

    }

    @Override
    public String gameAreaIdToShowName(String gameAreaId) {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                switch (gameAreaId) {
                    case GameArea.AREA_RACE:
                        return "比赛";
                    case GameArea.AREA_TRAIN:
                        return "训练";
                    default:
                        return "口口";
                }
            default:
                switch (gameAreaId) {
                    case GameArea.AREA_RACE:
                        return "area race";
                    case GameArea.AREA_TRAIN:
                        return "area train";
                    default:
                        return "[dic:" + gameAreaId + "]";
                }
        }
    }

    @Override
    public String resourceIdToShowName(String resourceId) {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                switch (resourceId) {
                    case ResourceType.TURN:
                        return "日期";
                    case ResourceType.COIN:
                        return "金钱";
                    case ResourceType.HORSE_SPEED:
                        return "速度";
                    case ResourceType.HORSE_STAMINA:
                        return "耐力";
                    case ResourceType.HORSE_POWER:
                        return "力量";
                    default:
                        return "口口";
                }
            default:
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

    }
    
    @Override
    public String runStrategyTypeToShowName(RunStrategyType type) {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                switch (type) {
                    case FIRST:
                        return "领跑";
                    case FRONT:
                        return "跟前";
                    case BACK:
                        return "居中";
                    case TAIL:
                        return "后追";
                    default:
                        return "口口";
                }
            default:
                switch (type) {
                    case FIRST:
                        return "first";
                    case FRONT:
                        return "front";
                    case BACK:
                        return "back";
                    case TAIL:
                        return "tail";
                    default:
                        return "[dic:" + type + "]";
                }
        }

    }
    
    public enum GameWord {
        RUN_STRATEGY,
        RACE_NAME,
        RACE_LENGTH
    }

    @Override
    public Map<Language, String> getLanguageShowNameMap() {
        return JavaFeatureForGwt.mapOf(
                Language.CN, "中文",
                Language.EN, "English"
        );
    }

    @Override
    public List<String> getMenuScreenTexts() {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                return JavaFeatureForGwt.arraysAsList(
                        "文本优俊",
                        "新游戏",
                        "继续存档",
                        "语言",
                        "重启后生效"
                );
            default:
                return JavaFeatureForGwt.arraysAsList(
                        "TextUma",
                        "New Game",
                        "Continue",
                        "Language",
                        "Take effect after restart"
                );
        }

    }

    @Override
    public List<String> getMainInfoBoardTexts() {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                return JavaFeatureForGwt.listOf(
                        "今天不是训练日。",
                        "今天不是比赛日。下一场比赛位于%s%s后。",
                        "天",
                        "天",
                        "你的优俊属性", // 4
                        "比赛准备开始", // 5
                        "对手优俊属性",
                        "比赛已结束",
                        "演示版本的全部比赛已结束。更多内容请期待后续版本。"
                );
            default:
                return JavaFeatureForGwt.listOf(
                        "Not train-day.",
                        "Not race-day now. The next race is in %s %s.",
                        "day",
                        "days",
                        // 4
                        "Your horse status",
                        // 5
                        "Race ready",
                        // 6
                        "Rivals status",
                        // 7
                        "Race end",
                        // 8
                        "No more race-day. This is the end of the demo version."
                );
        }
    }

    @Override
    public List<String> getGameAreaBoardTexts() {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                return JavaFeatureForGwt.arraysAsList(
                        "前往：",
                        "当前："
                );
            default:
                return JavaFeatureForGwt.arraysAsList(
                        "To ",
                        "In "
                );
        }
    }

    @Override
    public List<String> getTrainBoardTexts() {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                return JavaFeatureForGwt.arraysAsList(
                        "（花费不足！）",
                        "花费：",
                        "获得："
                );
            default:
                return JavaFeatureForGwt.arraysAsList(
                        "(Can't afford!)",
                        "Cost: ",
                        "Gain: "
                );
        }
    }

    @Override
    public String formatGameEventAndHorseShowName(String text) {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                text = text.replace(UmaSaveDataFactory.YOUR_HORSE_NAME, "你的优俊");
                text = text.replace(UmaSaveDataFactory.RIVAL_HORSE_NAME_START, "对手");
                return text;
            default:
                return text;
        }
    }

    @Override
    public String formatRaceShowName(String text) {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                for (int i = 0; i < UmaSaveDataFactory.RACE_NAMES.length; i++)
                    if (UmaSaveDataFactory.RACE_NAMES[i].equals(text)) {
                        return RACE_CN_NAMES[i];
                    }
                return text;
            default:
                return text;
        }
    }

    public String gameWordToShowName(GameWord gameWord) {
        Language language = game.getGameplayUIController().getLanguage();
        switch (language) {
            case CN:
                switch (gameWord) {
                    case RUN_STRATEGY:
                        return "跑法";
                    case RACE_NAME:
                        return "比赛名";
                    case RACE_LENGTH:
                        return "赛道长度";
                    default:
                        return "口口";
                }
            default:
                switch (gameWord) {
                    case RUN_STRATEGY:
                        return "Strategy";
                    case RACE_NAME:
                        return "Name";
                    case RACE_LENGTH:
                        return "Length";
                    default:
                        return "[dic:" + gameWord + "]";
                }
        }

    }
}
