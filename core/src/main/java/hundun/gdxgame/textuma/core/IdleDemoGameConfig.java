package hundun.gdxgame.textuma.core;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.logic.BuffId;
import hundun.gdxgame.textuma.core.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.textuma.core.logic.ConstructionId;
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.ScreenId;
import hundun.gdxgame.textuma.core.ui.screen.PlayScreen;
import hundun.gdxgame.textuma.share.framework.data.ChildGameConfig;
import hundun.gdxgame.textuma.share.framework.data.StarterData;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;
import hundun.gdxgame.textuma.share.framework.util.JavaHighVersionFeature;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class IdleDemoGameConfig extends ChildGameConfig {
    
    public IdleDemoGameConfig(TextUmaGame game) {

        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
        this.setConstructions(builtinConstructionsLoader.load());
        
        Map<String, List<String>> areaShownConstructionIds = new HashMap<>(); 
        areaShownConstructionIds.put(GameArea.AREA_COOKIE, JavaHighVersionFeature.arraysAsList(
                ConstructionId.COOKIE_CLICK_PROVIDER
        ));
        areaShownConstructionIds.put(GameArea.AREA_BUILDING, JavaHighVersionFeature.arraysAsList(
                ConstructionId.COOKIE_AUTO_PROVIDER,
                ConstructionId.COOKIE_SELLER
        ));
        areaShownConstructionIds.put(GameArea.AREA_WIN, JavaHighVersionFeature.arraysAsList(
                ConstructionId.WIN_PROVIDER
        ));

        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityByOwnAmountConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
        areaShownResourceIds.put(GameArea.AREA_COOKIE, JavaHighVersionFeature.arraysAsList(
            ResourceType.COIN
        ));
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        Map<String, List<String>> areaShowEntityByChangeAmountResourceIds = new HashMap<>(); 
        areaShowEntityByChangeAmountResourceIds.put(GameArea.AREA_COOKIE, JavaHighVersionFeature.arraysAsList(
            ResourceType.COOKIE
        ));
        this.setAreaShowEntityByChangeAmountResourceIds(areaShowEntityByChangeAmountResourceIds);
        
        StarterData starterData = new StarterData();
        Map<String, Integer> constructionStarterLevelMap = JavaHighVersionFeature.mapOf(ConstructionId.COOKIE_SELLER, 1);
        starterData.setConstructionStarterLevelMap(constructionStarterLevelMap);
        Map<String, Boolean> constructionStarterWorkingLevelMap = JavaHighVersionFeature.mapOf(ConstructionId.COOKIE_SELLER, Boolean.FALSE);
        starterData.setConstructionStarterWorkingLevelMap(constructionStarterWorkingLevelMap);
        this.setStarterData(starterData); 
        
        Map<String, String> screenIdToFilePathMap = JavaHighVersionFeature.mapOf(
                ScreenId.MENU, "audio/Loop-Menu.wav",
                ScreenId.PLAY, "audio/forest.mp3"
                );
        this.setScreenIdToFilePathMap(screenIdToFilePathMap);
        
        List<AchievementPrototype> achievementPrototypes = JavaHighVersionFeature.arraysAsList(
                new AchievementPrototype("Game win", "You win the game!",
                        JavaHighVersionFeature.mapOf(BuffId.WIN, 1),
                        null
                        )
                );
        this.setAchievementPrototypes(achievementPrototypes);
    }

    
}
