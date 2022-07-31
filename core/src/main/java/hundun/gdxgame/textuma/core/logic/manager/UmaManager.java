package hundun.gdxgame.textuma.core.logic.manager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.data.UmaSaveData;
import hundun.gdxgame.textuma.core.data.UmaSaveData.TurnConfig;
import hundun.gdxgame.textuma.core.data.UmaSaveData.UmaSaveDataFactory;
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.framework.listener.IGameAreaChangeListener;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.framework.util.JavaHighVersionFeature;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.horse.HorsePrototypeFactory;
import hundun.simulationgame.umamusume.race.RacePrototype;

/**
 * @author hundun
 * Created on 2022/08/02
 */
public class UmaManager implements IGameAreaChangeListener {
    TextUmaGame game;
    private UmaPlayScreen playScreen;
    UmaSaveData umaSaveData;
    
    TurnConfig currentTurnConfig;
    
    // ------ replace-lombok ------
    public UmaState getState() {
        return umaSaveData.state;
    }
    
    public UmaManager(TextUmaGame game, UmaPlayScreen playScreen) {
        this.playScreen = playScreen;
        this.game = game;
    }
    
    

    
    public void applySaveData(UmaSaveData umaSaveData) {
        this.umaSaveData = umaSaveData;
    }
    public UmaSaveData getUmaSaveData() {
        return umaSaveData;
    }
    
    public enum UmaState {
        TRAIN_DAY,
        RACE_DAY_RACE_READY
        ;
    }

    private void nextDay() {
        game.getModelContext().getStorageManager().modifyAllResourceNum(
                JavaHighVersionFeature.mapOf(ResourceType.TURN, 1L), 
                true
                );
        int currentTurn = (int) game.getModelContext().getStorageManager().getResourceNumOrZero(ResourceType.TURN);
        this.currentTurnConfig = umaSaveData.turnConfigMap.get(currentTurn);
        if (this.currentTurnConfig == null) {
            umaSaveData.state = UmaState.TRAIN_DAY;
        } else {
            umaSaveData.state = UmaState.RACE_DAY_RACE_READY;
        }
        checkUiByAreaAndState(playScreen.getArea());
    }

    public void trainAndNextDay(String trainDescription, List<ResourcePair> costList, List<ResourcePair> gainList) {
        HorsePrototype horsePrototype = umaSaveData.playerHorse;
        playScreen.getMainInfoBoard().updateAsHorseStatus(horsePrototype, trainDescription, gainList);
        gainList.forEach(resourcePair -> {
            switch (resourcePair.getType()) {
                case ResourceType.HORSE_SPEED:
                    horsePrototype.setBaseSpeed(horsePrototype.getBaseSpeed() + resourcePair.getAmount().intValue());
                    break;
                default:
                    break;
            }
        });
        
        
        Gdx.app.log(this.getClass().getSimpleName(), "train done, gain = " + gainList.toString());
        nextDay();
    }
    
    private void checkUiByAreaAndState(String currentArea) {
        switch (currentArea) {
            case GameArea.AREA_TRAIN:
                if (getState() == UmaState.TRAIN_DAY) {
                    playScreen.getMainInfoBoard().updateAsHorseStatus(umaSaveData.playerHorse, null, null);
                } else {
                    playScreen.getMainInfoBoard().updateAsText("Not train-day.");
                }
                break;
            case GameArea.AREA_RACE:
                if (getState() == UmaState.RACE_DAY_RACE_READY) {
                    RacePrototype racePrototype = this.currentTurnConfig.race;
                    List<HorsePrototype> rivalHorses = this.currentTurnConfig.rivalHorses;
                    playScreen.getMainInfoBoard().updateAsRaceReady(racePrototype, rivalHorses);
                } else {
                    playScreen.getMainInfoBoard().updateAsText("Not race-day.");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onGameAreaChange(String last, String current) {
        checkUiByAreaAndState(current);
        //playScreen.getMainInfoBoard().updateAsClear();
    }

    

}
