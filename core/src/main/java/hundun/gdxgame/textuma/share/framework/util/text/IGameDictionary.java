package hundun.gdxgame.textuma.share.framework.util.text;

import hundun.gdxgame.textuma.core.logic.GameDictionary.GameWord;
import hundun.simulationgame.umamusume.core.horse.RunStrategyType;

import java.util.List;
import java.util.Map;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public interface IGameDictionary {
    String constructionIdToShowName(String constructionId);

    String gameAreaIdToShowName(String gameAreaId);

    String resourceIdToShowName(String type);

    String formatRaceShowName(String text);

    String gameWordToShowName(GameWord gameWord);

    String runStrategyTypeToShowName(RunStrategyType type);

    Map<Language, String> getLanguageShowNameMap();

    List<String> getMenuScreenTexts();

    List<String> getMainInfoBoardTexts();

    List<String> getGameAreaBoardTexts();

    List<String> getTrainBoardTexts();

    String formatGameEventAndHorseShowName(String name);
}
