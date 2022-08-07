package hundun.gdxgame.textuma.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.data.UmaSaveData;
import hundun.gdxgame.textuma.core.data.UmaSaveData.TurnConfig;
import hundun.gdxgame.textuma.core.logic.BuffId;
import hundun.gdxgame.textuma.core.logic.BuiltinConstructionsLoader;
import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.core.logic.manager.UmaManager.UmaState;
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.ScreenId;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.framework.data.ChildGameConfig;
import hundun.gdxgame.textuma.share.framework.data.StarterData;
import hundun.gdxgame.textuma.share.framework.model.AchievementPrototype;
import hundun.gdxgame.textuma.share.framework.util.JavaHighVersionFeature;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.horse.RunStrategyType;
import hundun.simulationgame.umamusume.race.RaceLengthType;
import hundun.simulationgame.umamusume.race.RacePrototype;
import hundun.simulationgame.umamusume.race.TrackGroundType;
import hundun.simulationgame.umamusume.util.JavaFeatureForGwt.NumberFormat;

/**
 * @author hundun
 * Created on 2022/01/11
 */
public class TextUmaGameConfig extends ChildGameConfig {
    
    public TextUmaGameConfig(TextUmaGame game) {

        BuiltinConstructionsLoader builtinConstructionsLoader = new BuiltinConstructionsLoader(game);
        this.setConstructions(builtinConstructionsLoader.load());
        
        Map<String, List<String>> areaShownConstructionIds = new HashMap<>(); 
        areaShownConstructionIds.put(GameArea.AREA_RACE, JavaHighVersionFeature.arraysAsList(
                UserActionId.START_RACE,
                UserActionId.NEXT_RACE_RECORD_NODE,
                UserActionId.REPLAY_RACE_RECORD,
                UserActionId.END_RACE_RECORD
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
                ResourceType.TURN,
                ResourceType.COIN
        ));
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        Map<String, List<String>> areaShowEntityByChangeAmountResourceIds = new HashMap<>(); 
        areaShowEntityByChangeAmountResourceIds.put(GameArea.AREA_RACE, JavaHighVersionFeature.arraysAsList(
                ResourceType.TURN,
                ResourceType.COIN
        ));
        this.setAreaShowEntityByChangeAmountResourceIds(areaShowEntityByChangeAmountResourceIds);
        
        StarterData starterData = new StarterData();
        Map<String, Integer> constructionStarterLevelMap = new HashMap<>();
        starterData.setConstructionStarterLevelMap(constructionStarterLevelMap);
        Map<String, Boolean> constructionStarterWorkingLevelMap = new HashMap<>();
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

    public static class UmaSaveDataFactory {
        public static UmaSaveData forNewGame() {
            NumberFormat formatter = NumberFormat.getFormat(3, 0);
            UmaSaveData umaSaveData = new UmaSaveData();
            umaSaveData.state = UmaState.TRAIN_DAY;
            
            {
                HorsePrototype horsePrototype;
                horsePrototype = new HorsePrototype();
                horsePrototype.setName("SpecialWeek");
                horsePrototype.setBaseSpeed((int) (600* 1.05));
                horsePrototype.setBaseStamina((int) (600* 1.05));
                horsePrototype.setBasePower((int) (600* 1.05));
                horsePrototype.setBaseGuts((int) (600* 1.05));
                horsePrototype.setBaseWisdom((int) (200* 1.05));
                horsePrototype.setDefaultRunStrategyType(RunStrategyType.FRONT);
                HorsePrototypeFactory.fillDefaultFields(horsePrototype);
                horsePrototype.setCharImage("Your");
                umaSaveData.playerHorse = horsePrototype;
            }    
            
            umaSaveData.turnConfigMap = new HashMap<>();
            {
                HorsePrototype horsePrototype;
                TurnConfig turnConfig = new TurnConfig();
                turnConfig.rivalHorses = new ArrayList<>();
                
                RacePrototype racePrototype;
                racePrototype = new RacePrototype();
                racePrototype.setName("ShortDemoRace");
                racePrototype.setGroundType(TrackGroundType.TURF);
                racePrototype.setLength(1200);
                racePrototype.setLengthType(RaceLengthType.MILE);
                racePrototype.setDefaultHorseNum(4);
                turnConfig.race = racePrototype;
                
                for (int i = 1; i <= 5; i++) {
                    horsePrototype = new HorsePrototype();
                    horsePrototype.setName("SilenceSuzuka" + formatter.format(i));
                    horsePrototype.setBaseSpeed(600 + 20 * i);
                    horsePrototype.setBaseStamina(600 - 10 * i);
                    horsePrototype.setBasePower(600 - 10 * i);
                    horsePrototype.setBaseGuts(600);
                    horsePrototype.setBaseWisdom(200);
                    horsePrototype.setDefaultRunStrategyType(RunStrategyType.FIRST);
                    HorsePrototypeFactory.fillDefaultFields(horsePrototype);
                    horsePrototype.setCharImage("Rival" + i);
                    turnConfig.rivalHorses.add(horsePrototype);
                }
                umaSaveData.turnConfigMap.put(3, turnConfig);
            } 
            
            return umaSaveData;
        }
    }

}
