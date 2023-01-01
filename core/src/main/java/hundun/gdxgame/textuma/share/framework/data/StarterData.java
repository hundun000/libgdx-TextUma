package hundun.gdxgame.textuma.share.framework.data;

import java.util.Map;

import lombok.Data;

/**
 * @author hundun
 * Created on 2022/04/08
 */
@Data
public class StarterData {
    Map<String, Long> resourceStarterMap;
    Map<String, Integer> constructionStarterLevelMap;
    Map<String, Boolean> constructionStarterWorkingLevelMap;
}
