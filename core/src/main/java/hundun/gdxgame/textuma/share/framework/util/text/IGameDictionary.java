package hundun.gdxgame.textuma.share.framework.util.text;
/**
 * @author hundun
 * Created on 2022/01/11
 */
public interface IGameDictionary {
    String constructionIdToShowName(String constructionId);

    String gameAreaIdToShowName(String gameAreaId);

    String resourceIdToShowName(String type);

    String gameWordToShowName(String gameWord);
}
