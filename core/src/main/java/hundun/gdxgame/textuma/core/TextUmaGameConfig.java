package hundun.gdxgame.textuma.core;

import java.util.ArrayList;
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
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.framework.data.ChildGameConfig;
import hundun.gdxgame.textuma.share.framework.data.StarterData;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;
import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.RunStrategyType;
import hundun.simulationgame.umamusume.core.race.RaceLengthType;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.race.TrackGroundType;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt.NumberFormat;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData;
import hundun.simulationgame.umamusume.game.gameplay.data.GameRuleData;
import hundun.simulationgame.umamusume.game.gameplay.data.TurnConfig;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData.OperationBoardState;
import hundun.simulationgame.umamusume.game.nogameplay.HorsePrototypeFactory;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class TextUmaGameConfig extends ChildGameConfig {
    
    public TextUmaGameConfig(TextUmaGame game) {

        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
        this.setConstructions(builtinConstructionsLoader.load());
        
        Map<String, List<String>> areaShownConstructionIds = new HashMap<>(); 
        areaShownConstructionIds.put(GameArea.AREA_RACE, JavaFeatureForGwt.arraysAsList(
                UserActionId.START_RACE,
                UserActionId.NEXT_RACE_RECORD_NODE,
                UserActionId.REPLAY_RACE_RECORD,
                UserActionId.END_RACE_RECORD
        ));
        areaShownConstructionIds.put(GameArea.AREA_TRAIN, JavaFeatureForGwt.arraysAsList(
                UserActionId.RUNNING_TRAIN,
                UserActionId.SWIMMING_TRAIN,
                UserActionId.POWER_TRAIN,
                UserActionId.FREE_TRAIN
        ));
//        areaShownConstructionIds.put(GameArea.AREA_RECORD, JavaFeatureForGwt.arraysAsList(
//                UserActionId.RECORD_PRE_PAGE,
//                UserActionId.RECORD_NEXT_PAGE
//        ));

        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityByOwnAmountConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
//        areaShownResourceIds.put(GameArea.AREA_RACE, JavaFeatureForGwt.arraysAsList(
//                ResourceType.TURN,
//                ResourceType.COIN
//        ));
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        Map<String, List<String>> areaShowEntityByChangeAmountResourceIds = new HashMap<>(); 
//        areaShowEntityByChangeAmountResourceIds.put(GameArea.AREA_RACE, JavaFeatureForGwt.arraysAsList(
//                ResourceType.TURN,
//                ResourceType.COIN
//        ));
        this.setAreaShowEntityByChangeAmountResourceIds(areaShowEntityByChangeAmountResourceIds);
        
        StarterData starterData = new StarterData();
        Map<String, Integer> constructionStarterLevelMap = new HashMap<>();
        starterData.setConstructionStarterLevelMap(constructionStarterLevelMap);
        Map<String, Boolean> constructionStarterWorkingLevelMap = new HashMap<>();
        starterData.setConstructionStarterWorkingLevelMap(constructionStarterWorkingLevelMap);
        this.setStarterData(starterData); 
        
//        Map<String, String> screenIdToFilePathMap = JavaFeatureForGwt.mapOf(
//                ScreenId.MENU, "audio/Loop-Menu.wav",
//                ScreenId.PLAY, "audio/forest.mp3"
//                );
//        this.setScreenIdToFilePathMap(screenIdToFilePathMap);
        
        List<AchievementPrototype> achievementPrototypes = JavaFeatureForGwt.arraysAsList(
                new AchievementPrototype("Game win", "You win the game!",
                        JavaFeatureForGwt.mapOf(BuffId.WIN, 1),
                        null
                        )
                );
        this.setAchievementPrototypes(achievementPrototypes);
    }


}
