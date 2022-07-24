package hundun.gdxgame.textuma.core.logic.manager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.data.UmaSaveData;
import hundun.gdxgame.textuma.core.data.UmaSaveData.UmaSaveDataFactory;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.framework.util.JavaHighVersionFeature;
import hundun.simulationgame.umamusume.horse.HorsePrototypeFactory;

/**
 * @author hundun
 * Created on 2022/08/02
 */
public class UmaManager {
    
    private TextUmaGame game;
    UmaSaveData umaSaveData;
    
    // ------ replace-lombok ------
    public UmaState getState() {
        return umaSaveData.state;
    }
    
    public UmaManager(TextUmaGame game) {
        this.game = game;
    }
    
    

    
    public void applySaveData(UmaSaveData umaSaveData) {
        this.umaSaveData = umaSaveData;
    }
    
    public enum UmaState {
        TRAIN_DAY,
        RACE_DAY_RACE_READY
        ;
    }


    public void trainAndNextDay(List<ResourcePair> costList, List<ResourcePair> gainList) {
        // TODO Auto-generated method stub
    }

    

}
