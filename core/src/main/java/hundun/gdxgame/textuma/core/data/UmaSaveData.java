package hundun.gdxgame.textuma.core.data;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.textuma.core.logic.manager.UmaManager.UmaState;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.horse.RunStrategyType;
import hundun.simulationgame.umamusume.race.RaceLengthType;
import hundun.simulationgame.umamusume.race.RacePrototype;
import hundun.simulationgame.umamusume.race.TrackGroundType;

/**
 * @author hundun
 * Created on 2022/08/05
 */
public class UmaSaveData {
    public UmaState state;
    public HorsePrototype playerHorse;
    public Map<Integer, TurnConfig> turnConfigMap;
    
    public static class TurnConfig {
        public RacePrototype race;
        public List<HorsePrototype> rivalHorses;
    }
    
    public static class UmaSaveDataFactory {
        public static UmaSaveData forNewGame() {
            NumberFormat formatter = new DecimalFormat("#000");
            UmaSaveData umaSaveData = new UmaSaveData();
            umaSaveData.state = UmaState.TRAIN_DAY;
            
            {
                HorsePrototype horsePrototype;
                horsePrototype = new HorsePrototype();
                horsePrototype.setName("特别周001");
                horsePrototype.setBaseSpeed((int) (600* 1.05));
                horsePrototype.setBaseStamina((int) (600* 1.05));
                horsePrototype.setBasePower((int) (600* 1.05));
                horsePrototype.setBaseGuts((int) (600* 1.05));
                horsePrototype.setBaseWisdom((int) (200* 1.05));
                horsePrototype.setDefaultRunStrategyType(RunStrategyType.FRONT);
                HorsePrototypeFactory.fillDefaultFields(horsePrototype);
                umaSaveData.playerHorse = horsePrototype;
            }    
            
            umaSaveData.turnConfigMap = new HashMap<>();
            {
                HorsePrototype horsePrototype;
                TurnConfig turnConfig = new TurnConfig();
                turnConfig.rivalHorses = new ArrayList<>();
                
                RacePrototype racePrototype;
                racePrototype = new RacePrototype();
                racePrototype.setName("短距离训练场");
                racePrototype.setGroundType(TrackGroundType.TURF);
                racePrototype.setLength(1200);
                racePrototype.setLengthType(RaceLengthType.MILE);
                racePrototype.setDefaultHorseNum(4);
                turnConfig.race = racePrototype;
                
                for (int i = 1; i <= 5; i++) {
                    horsePrototype = new HorsePrototype();
                    horsePrototype.setName("无声铃鹿" + formatter.format(i));
                    horsePrototype.setBaseSpeed(600 + 20 * i);
                    horsePrototype.setBaseStamina(600 - 10 * i);
                    horsePrototype.setBasePower(600 - 10 * i);
                    horsePrototype.setBaseGuts(600);
                    horsePrototype.setBaseWisdom(200);
                    horsePrototype.setDefaultRunStrategyType(RunStrategyType.FIRST);
                    HorsePrototypeFactory.fillDefaultFields(horsePrototype);
                    horsePrototype.setCharImage("鹿");
                    turnConfig.rivalHorses.add(horsePrototype);
                }
                umaSaveData.turnConfigMap.put(3, turnConfig);
            } 
            
            return umaSaveData;
        }
    }
}
