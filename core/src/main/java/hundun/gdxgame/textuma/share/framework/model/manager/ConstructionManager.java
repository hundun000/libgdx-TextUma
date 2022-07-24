package hundun.gdxgame.textuma.share.framework.model.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.model.ModelContext;
import hundun.gdxgame.textuma.share.framework.model.construction.base.BaseConstruction;

/**
 * @author hundun
 * Created on 2021/11/29
 */
public class ConstructionManager {

    BaseIdleGame game;


    public ConstructionManager(BaseIdleGame game) {
        this.game = game;
    }


    /**
     * 运行中的设施集合。key: constructionId
     */
    Map<String, BaseConstruction> runningConstructionModelMap = new HashMap<>();

    /**
     * 根据GameArea显示不同的Construction集合
     */
    Map<String, List<BaseConstruction>> areaControlableConstructions;

    public void lazyInit(Map<String, List<String>> areaControlableConstructionIds) {
        ModelContext modelContext = game.getModelContext();
        areaControlableConstructions = new HashMap<>();
        if (areaControlableConstructionIds != null) {
            for (Entry<String, List<String>> entry : areaControlableConstructionIds.entrySet()) {
                areaControlableConstructions.put(
                        entry.getKey(),
                        entry.getValue()
                            .stream()
                            .map(id -> modelContext.getConstructionFactory().getConstruction(id))
                            .collect(Collectors.toList())
                        );
            }
        }

//        areaControlableConstructions.put(String.BEE_FARM, Arrays.asList(
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.BEE_GATHER_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.SMALL_BEEHIVE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.MID_BEEHIVE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.BIG_BEEHIVE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.QUEEN_BEEHIVE)
//                ));
//        areaControlableConstructions.put(String.FOREST_FARM, Arrays.asList(
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_GATHER_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_KEEPING),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_BOARD_MAKER),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WIN_THE_GAME)
//                ));
//        areaControlableConstructions.put(String.SHOP, Arrays.asList(
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_SELL_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.WOOD_BOARD_SELL_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.BEE_SELL_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.HONEY_SELL_HOUSE),
//                modelContext.getConstructionFactory().getConstruction(ConstructionId.BEEWAX_SELL_HOUSE)
//                ));
        areaControlableConstructions.values().forEach(items -> items.forEach(item -> runningConstructionModelMap.putIfAbsent(item.getId(), item)));
    }

    public void logicFrameForAllConstructionModels() {
        runningConstructionModelMap.values().forEach(item -> item.onLogicFrame());
    }

    public List<BaseConstruction> getAreaShownConstructionsOrEmpty(String gameArea) {
        areaControlableConstructions.computeIfAbsent(gameArea, gameArea2 -> new ArrayList<>());
        List<BaseConstruction> constructions = areaControlableConstructions.get(gameArea);
        return constructions;
    }

    public Integer getConstructionLevelOrNull(String constructionId) {
        if (!runningConstructionModelMap.containsKey(constructionId)) {
            return null;
        }
        return runningConstructionModelMap.get(constructionId).getSaveData().getLevel();
    }







}
