package hundun.gdxgame.textuma.share.framework.util.text;

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

    String gameWordToShowName(String gameWord);

    Map<Language, String> getLanguageShowNameMap();

    List<String> getMemuScreenTexts();
}
