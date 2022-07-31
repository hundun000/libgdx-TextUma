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
import hundun.simulationgame.umamusume.record.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.text.TextFrameData;

/**
 * @author hundun
 * Created on 2022/08/05
 */
public class UmaSaveData {
    public UmaState state;
    public HorsePrototype playerHorse;
    public Map<Integer, TurnConfig> turnConfigMap;
    public int currentRaceRecordNodeIndex;
    public List<RecordNode<TextFrameData>> currentRaceRecordNodes;
    
    public static class TurnConfig {
        public RacePrototype race;
        public List<HorsePrototype> rivalHorses;
    }
    
}
