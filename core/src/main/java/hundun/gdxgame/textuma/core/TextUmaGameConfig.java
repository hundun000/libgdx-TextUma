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
import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.core.horse.RunStrategyType;
import hundun.simulationgame.umamusume.core.race.RaceLengthType;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.race.TrackGroundType;
import hundun.simulationgame.umamusume.core.util.JavaFeatureForGwt.NumberFormat;

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
                UserActionId.RUNNING_TRAIN,
                UserActionId.SWIMMING_TRAIN,
                UserActionId.POWER_TRAIN,
                UserActionId.FREE_TRAIN
        ));
//        areaShownConstructionIds.put(GameArea.AREA_RECORD, JavaHighVersionFeature.arraysAsList(
//                UserActionId.RECORD_PRE_PAGE,
//                UserActionId.RECORD_NEXT_PAGE
//        ));

        this.setAreaControlableConstructionIds(areaShownConstructionIds);
        this.setAreaShowEntityByOwnAmountConstructionIds(areaShownConstructionIds);
        
        Map<String, List<String>> areaShownResourceIds = new HashMap<>(); 
//        areaShownResourceIds.put(GameArea.AREA_RACE, JavaHighVersionFeature.arraysAsList(
//                ResourceType.TURN,
//                ResourceType.COIN
//        ));
        this.setAreaShowEntityByOwnAmountResourceIds(areaShownResourceIds);
        
        Map<String, List<String>> areaShowEntityByChangeAmountResourceIds = new HashMap<>(); 
//        areaShowEntityByChangeAmountResourceIds.put(GameArea.AREA_RACE, JavaHighVersionFeature.arraysAsList(
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
        static final int SAME_GUTS = 400;
        static final int SAME_WISDOM = 200;
        static RunStrategyType[] rivalRunStrategyTypes = new RunStrategyType[] {
                RunStrategyType.FIRST, 
                RunStrategyType.FRONT, 
                RunStrategyType.BACK, 
                RunStrategyType.TAIL
                };
        
        public static RacePrototype raceTemplate(int rand) {
            RacePrototype racePrototype;
            racePrototype = new RacePrototype();
            racePrototype.setGroundType(TrackGroundType.TURF);
            racePrototype.setDefaultHorseNum(5);
            
            switch (rand) {
                default:
                case 0:
                    racePrototype.setName("ShortRace");
                    racePrototype.setLength(1200);
                    racePrototype.setLengthType(RaceLengthType.SHORT);
                    break;
                case 1:
                    racePrototype.setName("MileRace");
                    racePrototype.setLength(1600);
                    racePrototype.setLengthType(RaceLengthType.MILE);
                    break;
                case 2:
                    racePrototype.setName("MediumRace");
                    racePrototype.setLength(2000);
                    racePrototype.setLengthType(RaceLengthType.MEDIUM);
                    break;
                case 3:
                    racePrototype.setName("MediumRace");
                    racePrototype.setLength(2400);
                    racePrototype.setLengthType(RaceLengthType.MEDIUM);
                    break;
                case 4:
                    racePrototype.setName("LongRace");
                    racePrototype.setLength(2800);
                    racePrototype.setLengthType(RaceLengthType.LONG);
                    break;
            }
            return racePrototype;
        }
        
        public static TurnConfig turnConfigTemplate(int raceRand, int rivalValueAddition) {
            HorsePrototype horsePrototype;
            TurnConfig turnConfig = new TurnConfig();

            
            RacePrototype racePrototype = raceTemplate(raceRand);
            turnConfig.setRace(racePrototype);
            
            int numRival = turnConfig.getRace().getDefaultHorseNum() - 1;
            turnConfig.setRivalHorses(new ArrayList<>());
            
            for (int i = 0; i < numRival; i++) {
                horsePrototype = new HorsePrototype();
                horsePrototype.setName("rival" + i);
                horsePrototype.setBaseSpeed(500 + (int)(rivalValueAddition * (1)));
                horsePrototype.setBaseStamina(400 + (int)(rivalValueAddition * (1)));
                horsePrototype.setBasePower(400 + (int)(rivalValueAddition * (1)));
                horsePrototype.setBaseGuts(SAME_GUTS);
                horsePrototype.setBaseWisdom(SAME_WISDOM);
                horsePrototype.setDefaultRunStrategyType(rivalRunStrategyTypes[i]);
                HorsePrototypeFactory.fillDefaultFields(horsePrototype);
                horsePrototype.setCharImage("Rival" + (i + 1));
                turnConfig.getRivalHorses().add(horsePrototype);
            }
            
            int maxAward = 100 + 50 * racePrototype.getDefaultHorseNum();
            turnConfig.setRankToAwardMap(new HashMap<>());
            for (int i = 0; i< racePrototype.getDefaultHorseNum(); i++) {
                turnConfig.getRankToAwardMap().put(i, maxAward - (50 * i));
            }
            return turnConfig;
        }
        
        
        
        public static UmaSaveData forNewGame() {

            UmaSaveData umaSaveData = new UmaSaveData();
            umaSaveData.state = UmaState.TRAIN_DAY;
            
            {
                HorsePrototype horsePrototype;
                horsePrototype = new HorsePrototype();
                horsePrototype.setName("playerHorse");
                horsePrototype.setBaseSpeed((int) (700));
                horsePrototype.setBaseStamina((int) (500));
                horsePrototype.setBasePower((int) (500));
                horsePrototype.setBaseGuts(SAME_GUTS);
                horsePrototype.setBaseWisdom(SAME_WISDOM);
                horsePrototype.setDefaultRunStrategyType(RunStrategyType.FRONT);
                HorsePrototypeFactory.fillDefaultFields(horsePrototype);
                horsePrototype.setCharImage("Your");
                umaSaveData.playerHorse = horsePrototype;
            }    
            
            umaSaveData.turnConfigMap = new HashMap<>();
            {
                // first race not random
                umaSaveData.turnConfigMap.put(3, turnConfigTemplate(0, 0));
                
                int numRace = 5;
                int raceTurn = 3;
                for (int i = 1; i < numRace; i++) {
                    raceTurn += 4 + (int)(Math.random() * 3);
                    int raceRand = (int) (Math.random() * 5);
                    int rivalValueAddition = i * 40;
                    umaSaveData.turnConfigMap.put(raceTurn, turnConfigTemplate(raceRand, rivalValueAddition));
                }
            } 
            
            return umaSaveData;
        }
    }

}
