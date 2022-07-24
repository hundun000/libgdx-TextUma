package hundun.gdxgame.textuma.core;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.logic.BuffId;
import hundun.gdxgame.textuma.core.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.textuma.core.logic.UserActionId;
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
        areaShownConstructionIds.put(GameArea.AREA_RACE, JavaHighVersionFeature.arraysAsList(
                UserActionId.START_RACE
        ));
        areaShownConstructionIds.put(GameArea.AREA_TRAIN, JavaHighVersionFeature.arraysAsList(
                UserActionId.RUNNING_TRAIN
        ));
//        areaShownConstructionIds.put(GameArea.AREA_RECORD, JavaHighVersionFeature.arraysAsList(
//                UserActionId.RECORD_PRE_PAGE,
//                UserActionId.RECORD_NEXT_PAGE
//        ));

        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityByOwnAmountConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
        areaShownResourceIds.put(GameArea.AREA_RACE, JavaHighVersionFeature.arraysAsList(
            ResourceType.COIN
        ));
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        Map<String, List<String>> areaShowEntityByChangeAmountResourceIds = new HashMap<>(); 
        areaShowEntityByChangeAmountResourceIds.put(GameArea.AREA_RACE, JavaHighVersionFeature.arraysAsList(
            ResourceType.COIN
        ));
        this.setAreaShowEntityByChangeAmountResourceIds(areaShowEntityByChangeAmountResourceIds);
        
        StarterData starterData = new StarterData();
        Map<String, Integer> constructionStarterLevelMap = JavaHighVersionFeature.mapOf(UserActionId.COOKIE_SELLER, 1);
        starterData.setConstructionStarterLevelMap(constructionStarterLevelMap);
        Map<String, Boolean> constructionStarterWorkingLevelMap = JavaHighVersionFeature.mapOf(UserActionId.COOKIE_SELLER, Boolean.FALSE);
        starterData.setConstructionStarterWorkingLevelMap(constructionStarterWorkingLevelMap);
        this.setStarterData(starterData); 
        
//        Map<String, String> screenIdToFilePathMap = JavaHighVersionFeature.mapOf(
//                ScreenId.MENU, "audio/Loop-Menu.wav",
//                ScreenId.PLAY, "audio/forest.mp3"
//                );
//        this.setScreenIdToFilePathMap(screenIdToFilePathMap);
        
        List<AchievementPrototype> achievementPrototypes = JavaHighVersionFeature.arraysAsList(
                new AchievementPrototype("Game win", "You win the game!",
                        JavaHighVersionFeature.mapOf(BuffId.WIN, 1),
                        null
                        )
                );
        this.setAchievementPrototypes(achievementPrototypes);
    }

    
}
