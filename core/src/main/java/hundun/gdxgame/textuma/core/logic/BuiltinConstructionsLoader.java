package hundun.gdxgame.textuma.core.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.handler.race.EndRaceRecordNodeActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.race.NextRaceRecordNodeActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.race.ReplayRaceRecordNodeActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.race.StartRaceActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.train.BaseTrainActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.train.FreeTrainActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.train.PowerTrainActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.train.RunningTrainActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.train.SwimmingTrainActionHandler;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.model.construction.BaseConstructionFactory;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainLevelComponent;
import hundun.gdxgame.textuma.share.framework.model.construction.base.TrainOutputComponent;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePack;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.framework.util.JavaHighVersionFeature;


public class BuiltinConstructionsLoader {
    TextUmaGame game;
    List<UmaActionHandler> constructions = new ArrayList<>();
    
    public BuiltinConstructionsLoader(TextUmaGame game) {
        this.game = game;
        
    }
    
    public List<UmaActionHandler> load() {
        initProviders();
        return constructions;
    }
    
    private void initProviders() {

        constructions.add(new StartRaceActionHandler(game));
        constructions.add(new NextRaceRecordNodeActionHandler(game));
        constructions.add(new ReplayRaceRecordNodeActionHandler(game));
        constructions.add(new EndRaceRecordNodeActionHandler(game));

        constructions.add(new RunningTrainActionHandler(game));
        constructions.add(new SwimmingTrainActionHandler(game));
        constructions.add(new PowerTrainActionHandler(game));
        constructions.add(new FreeTrainActionHandler(game));
    }
    
    public static ResourcePack toPack(Map<String, Integer> map) {
        ResourcePack pack = new ResourcePack();
        List<ResourcePair> pairs = new ArrayList<>(map.size());
        map.entrySet().forEach(entry -> pairs.add(new ResourcePair(entry.getKey(), (long)entry.getValue())));
        pack.setBaseValues(pairs);
        return pack;
    }
    
    

}
