package hundun.gdxgame.textuma.core.ui.component;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.handler.race.BaseRaceActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.train.BaseTrainActionHandler;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePack;
import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.starter.ui.component.ResourceAmountPairNode;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;
import hundun.simulationgame.umamusume.horse.HorsePrototype;
import hundun.simulationgame.umamusume.race.RacePrototype;
import hundun.simulationgame.umamusume.race.RaceSituation;
import hundun.simulationgame.umamusume.record.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.text.TextFrameData;


/**
 * @author hundun
 * Created on 2021/11/08
 */
public class MainInfoBoard extends Table {
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 70;

    BasePlayScreen<TextUmaGame> parent;

    public MainInfoBoard(BasePlayScreen<TextUmaGame> parent) {
        //super("GUIDE_TEXT", parent.game.getButtonSkin());
        this.parent = parent;
        //this.setBounds(5, GameAreaControlBoard.Y, GameAreaControlBoard.X - 10, 120);
        this.setTouchable(Touchable.disabled);
        //this.setBackground(parent.getLayoutConst().blackBoard);
        //this.setVisible(true);
    }


    private <T extends Actor> Container<T> wapperContainer(T content) {
        Container<T> container = new Container<>(content);
        //container.setBackground(BasePlayScreen.createBorderBoard(1, 1, 0.7f, 0));
        container.fill(true);
        return container;
    }





    public void updateAsTrainInfo(BaseTrainActionHandler model) {
        this.clearChildren();

        add(new Label("wait action", parent.game.getButtonSkin()));

        if (parent.game.debugMode) {
            this.debug();
        }
    }





/*
 * builder.append(horsePrototype.getName()).append(" ")
                .append("Speed: ").append(horsePrototype.getBaseSpeed()).append(", ")
                .append("Stamina: ").append(horsePrototype.getBaseStamina()).append(", ")
                .append("Power: ").append(horsePrototype.getBasePower()).append(", ")
                .append("Guts: ").append(horsePrototype.getBaseGuts()).append(", ")
                .append("Wisdom: ").append(horsePrototype.getBaseWisdom()).append("")
                .append("\n");
 */

    public void updateAsHorseStatus(HorsePrototype horsePrototype, String trainDescription, List<ResourcePair> gainList) {
        this.clearChildren();
        
        MainInfoHelper.buildHorseStatus(this, parent.game, horsePrototype, trainDescription, gainList);
        
        if (parent.game.debugMode) {
            this.debug();
        }
        
    }


    public void updateAsClear() {
        this.clearChildren();

        
        if (parent.game.debugMode) {
            this.debug();
        }
    }


    public void updateAsRaceReady(RacePrototype racePrototype, List<HorsePrototype> rivalHorses) {
        this.clearChildren();
        
        MainInfoHelper.buildRaceReady(this, parent.game, racePrototype, rivalHorses);
        
        if (parent.game.debugMode) {
            this.debug();
        }
    }


    public void updateAsText(String text) {
        this.clearChildren();

        add(new Label(text, parent.game.getButtonSkin()));

        if (parent.game.debugMode) {
            this.debug();
        }
    }


    public void updateAsRaceRecordNode(RecordNode<TextFrameData> recordNode) {
        this.clearChildren();

        this.add(new Label(recordNode.getTimeText(), parent.game.getButtonSkin()));
        this.row();
        
        if (recordNode.getContent().getRaceInfo() != null) {
            this.add(new Label(recordNode.getContent().getRaceInfo(), parent.game.getButtonSkin()));
            this.row();
        } 
        if (recordNode.getContent().getEventInfo() != null) {
            this.add(new Label(recordNode.getContent().getEventInfo(), parent.game.getButtonSkin()));
        }
        

        if (parent.game.debugMode) {
            this.debug();
        }
        
    }


    private static class MainInfoHelper {
        
        
        private static void buildOneHorseStatus(Table table, TextUmaGame game, 
                String key, Object value, ResourcePair gainPair) {
            table.add(new Label(key, game.getButtonSkin(), TextUmaGame.GAME_WORD_SKIN_KEY));
            table.add(new Label(": ", game.getButtonSkin()));
            table.add(new Label(value.toString(), game.getButtonSkin()));
            if (gainPair != null) {
                table.add(new Label(" -> ", game.getButtonSkin()));
                table.add(new Label(((int)value) + gainPair.getAmount() + "", game.getButtonSkin()));
            } else {
                table.add(new Image());
                table.add(new Image());
            }
            table.row();
        }
        
        private static void buildHorseStatus(Table table, TextUmaGame game,
                HorsePrototype horsePrototype, String trainDescription, List<ResourcePair> gainList) {
            
            if (trainDescription != null) {
                table.add(new Label(trainDescription, game.getButtonSkin())).colspan(5).row();
            }
            
            MainInfoHelper.buildOneHorseStatus(table, game, "Name", horsePrototype.getName(), null);
            MainInfoHelper.buildOneHorseStatus(table, game, 
                    game.getGameDictionary().resourceIdToShowName(ResourceType.HORSE_SPEED), 
                    horsePrototype.getBaseSpeed(),
                    gainList == null ? null : gainList.stream().filter(it -> it.getType().equals(ResourceType.HORSE_SPEED)).findFirst().orElseGet(() -> null)
                    );
            MainInfoHelper.buildOneHorseStatus(table, game, 
                    game.getGameDictionary().resourceIdToShowName(ResourceType.HORSE_STAMINA), 
                    horsePrototype.getBaseStamina(),
                    gainList == null ? null : gainList.stream().filter(it -> it.getType().equals(ResourceType.HORSE_STAMINA)).findFirst().orElseGet(() -> null)
                    );
            MainInfoHelper.buildOneHorseStatus(table, game, 
                    game.getGameDictionary().resourceIdToShowName(ResourceType.HORSE_POWER), 
                    horsePrototype.getBasePower(),
                    gainList == null ? null : gainList.stream().filter(it -> it.getType().equals(ResourceType.HORSE_POWER)).findFirst().orElseGet(() -> null)
                    );
        }

        public static void buildRaceReady(Table table, TextUmaGame game, RacePrototype racePrototype,
                List<HorsePrototype> rivalHorses) {
            
            table.add(new Label("RaceReady", game.getButtonSkin())).colspan(5).row();
            
            MainInfoHelper.buildOneHorseStatus(table, game, "Name", racePrototype.getName(), null);
            MainInfoHelper.buildOneHorseStatus(table, game, 
                    "Length", 
                    racePrototype.getLength(),
                    null
                    );
        }


    }
    
    

}
