package hundun.gdxgame.textuma.core.data;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hundun.gdxgame.textuma.core.logic.manager.UmaManager.UmaState;
import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.core.horse.RunStrategyType;
import hundun.simulationgame.umamusume.core.race.RaceLengthType;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.core.race.TrackGroundType;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode.EndRecordHorseInfo;
import hundun.simulationgame.umamusume.record.gui.GuiFrameData;
import hundun.simulationgame.umamusume.record.text.TextFrameData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public List<EndRecordHorseInfo> sortedRaceEndRecordNode;
    

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TurnConfig {
        private RacePrototype race;
        private List<HorsePrototype> rivalHorses;
        private Map<Integer, Integer> rankToAwardMap;
    }
    
}
