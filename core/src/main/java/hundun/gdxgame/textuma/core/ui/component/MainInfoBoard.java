package hundun.gdxgame.textuma.core.ui.component;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.GameDictionary.GameWord;

import hundun.gdxgame.textuma.share.framework.model.resource.ResourcePair;
import hundun.gdxgame.textuma.share.starter.ui.component.BaseAmountPairNode;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BaseUmaPlayScreen;
import hundun.simulationgame.umamusume.core.horse.HorsePrototype;
import hundun.simulationgame.umamusume.core.race.RacePrototype;
import hundun.simulationgame.umamusume.record.base.RecordPackage.RecordNode;
import hundun.simulationgame.umamusume.record.base.RecordPackage.EndRecordNode.EndRecordHorseInfo;
import hundun.simulationgame.umamusume.record.text.TextFrameData;


/**
 * @author hundun
 * Created on 2021/11/08
 */
public class MainInfoBoard extends Table {
    private static int NODE_HEIGHT = 25;
    private static int NODE_WIDTH = 70;

    BaseUmaPlayScreen parent;

    public MainInfoBoard(BaseUmaPlayScreen parent) {
        //super("GUIDE_TEXT", parent.getGame().getMainSkin());
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

/*
 * builder.append(horsePrototype.getName()).append(" ")
                .append("Speed: ").append(horsePrototype.getBaseSpeed()).append(", ")
                .append("Stamina: ").append(horsePrototype.getBaseStamina()).append(", ")
                .append("Power: ").append(horsePrototype.getBasePower()).append(", ")
                .append("Guts: ").append(horsePrototype.getBaseGuts()).append(", ")
                .append("Wisdom: ").append(horsePrototype.getBaseWisdom()).append("")
                .append("\n");
 */

    public void updateAsHorseStatus(HorsePrototype horsePrototype, String title, List<ResourcePair> gainList) {
        this.clearChildren();
        
        MainInfoHelper.buildHorseStatus(this, parent.getGame(), horsePrototype, title, gainList);
        
        if (parent.getGame().debugMode) {
            this.debug();
        }
        
    }


    public void updateAsClear() {
        this.clearChildren();

        
        if (parent.getGame().debugMode) {
            this.debug();
        }
    }


    public void updateAsRaceReady(RacePrototype racePrototype, Map<HorsePrototype, String> rivalHorsesToRunStrategyTextMap) {
        this.clearChildren();
        
        MainInfoHelper.buildRaceReady(this, parent.getGame(), racePrototype, rivalHorsesToRunStrategyTextMap);
        
        if (parent.getGame().debugMode) {
            this.debug();
        }
    }


    public void updateAsText(String text) {
        this.clearChildren();

        add(new Label(text, parent.getGame().getMainSkin()));

        if (parent.getGame().debugMode) {
            this.debug();
        }
    }


    public void updateAsRaceRecordNode(RecordNode<TextFrameData> recordNode) {
        this.clearChildren();

        this.add(new Label(recordNode.getTimeText(), parent.getGame().getMainSkin()));
        this.row();
        
        if (recordNode.getContent().getRaceInfo() != null) {
            this.add(new Label(recordNode.getContent().getRaceInfo(), parent.getGame().getMainSkin()));
            this.row();
        } 
        if (recordNode.getContent().getEventInfo() != null) {
            this.add(new Label(recordNode.getContent().getEventInfo(), parent.getGame().getMainSkin()));
        }
        

        if (parent.getGame().debugMode) {
            this.debug();
        }
        
    }


    private static class MainInfoHelper {

        
        private static void buildOneInfoOneLine(Table table, TextUmaGame game, 
                String key, Object value, ResourcePair gainPair) {
            table.add(new Label(key, game.getMainSkin(), TextUmaGame.GAME_WORD_SKIN_KEY));
            table.add(new Label(": ", game.getMainSkin()));
            if (gainPair != null) {
                table.add(new Label(value.toString(), game.getMainSkin()));
                table.add(new Label(" -> ", game.getMainSkin()));
                table.add(new Label(((int)value) + gainPair.getAmount() + "", game.getMainSkin()));
            } else {
                table.add(new Label(value.toString(), game.getMainSkin())).colspan(3);
            }
            table.row();
        }
        
        private static void buildHorseStatus(Table table, TextUmaGame game,
                HorsePrototype horsePrototype, String trainDescription, List<ResourcePair> gainList) {
            
            if (trainDescription != null) {
                table.add(new Label(trainDescription, game.getMainSkin())).colspan(5).row();
            }
            
            table.add(new Label("Your horse status", game.getMainSkin())).colspan(5).row();
            //MainInfoHelper.buildOneInfoOneLine(table, game, "Name", horsePrototype.get(), null);
            MainInfoHelper.buildOneInfoOneLine(table, game, 
                    game.getGameDictionary().resourceIdToShowName(ResourceType.HORSE_SPEED), 
                    horsePrototype.getBaseSpeed(),
                    gainList == null ? null : gainList.stream().filter(it -> it.getType().equals(ResourceType.HORSE_SPEED)).findFirst().orElseGet(() -> null)
                    );
            MainInfoHelper.buildOneInfoOneLine(table, game, 
                    game.getGameDictionary().resourceIdToShowName(ResourceType.HORSE_STAMINA), 
                    horsePrototype.getBaseStamina(),
                    gainList == null ? null : gainList.stream().filter(it -> it.getType().equals(ResourceType.HORSE_STAMINA)).findFirst().orElseGet(() -> null)
                    );
            MainInfoHelper.buildOneInfoOneLine(table, game, 
                    game.getGameDictionary().resourceIdToShowName(ResourceType.HORSE_POWER), 
                    horsePrototype.getBasePower(),
                    gainList == null ? null : gainList.stream().filter(it -> it.getType().equals(ResourceType.HORSE_POWER)).findFirst().orElseGet(() -> null)
                    );
            MainInfoHelper.buildOneInfoOneLine(table, game, 
                    game.getGameDictionary().gameWordToShowName(GameWord.RUN_STRATEGY), 
                    horsePrototype.getDefaultRunStrategyType(),
                    null
                    );
        }

        public static void buildRaceReady(Table table, TextUmaGame game, RacePrototype racePrototype,
                Map<HorsePrototype, String> rivalHorsesToRunStrategyTextMap) {
            
            table.add(new Label("Race ready", game.getMainSkin())).colspan(7).row();
            
            MainInfoHelper.buildOneInfoOneLine(table, game, "Name", racePrototype.getName(), null);
            MainInfoHelper.buildOneInfoOneLine(table, game, 
                    "Length", 
                    racePrototype.getLength(),
                    null
                    );
            
            table.add(new Label("Rivals status", game.getMainSkin())).colspan(7).padTop(10).row();
            rivalHorsesToRunStrategyTextMap.forEach((horsePrototype, runStrategyText) -> {
                MainInfoHelper.buildAllHorseInfoOneLine(table, game, 
                        horsePrototype.getCharImage(),
                        game.getGameDictionary().resourceIdToShowName(ResourceType.HORSE_SPEED), 
                        horsePrototype.getBaseSpeed(),
                        game.getGameDictionary().resourceIdToShowName(ResourceType.HORSE_STAMINA), 
                        horsePrototype.getBaseStamina(),
                        game.getGameDictionary().resourceIdToShowName(ResourceType.HORSE_POWER), 
                        horsePrototype.getBasePower(),
                        runStrategyText
                        );
            });
        }

        private static void buildAllHorseInfoOneLine(Table table, TextUmaGame game, 
                String name,
                String baseSpeedShowName, int baseSpeed, 
                String baseStaminaShowName, int baseStamina, 
                String basePowerShowName, int basePower, 
                String runStrategyText
                ) {
            
            table.add(new Label(name, game.getMainSkin()));
            table.add(new Label(": ", game.getMainSkin()));
            table.add(new BaseAmountPairNode<>(game)
                    .lazyInit(baseSpeedShowName)
                    .update(baseSpeed)
                    ).padRight(10);
            table.add(new BaseAmountPairNode<>(game)
                    .lazyInit(baseStaminaShowName)
                    .update(baseStamina)
                    ).padRight(10);
            table.add(new BaseAmountPairNode<>(game)
                    .lazyInit(basePowerShowName)
                    .update(basePower)
                    ).padRight(10);
            table.add(new BaseAmountPairNode<>(game)
                    .lazyInit(game.getGameDictionary().gameWordToShowName(GameWord.RUN_STRATEGY))
                    .update(runStrategyText)
                    );
            table.row();
        }

        public static void buildRaceEnd(Table table, TextUmaGame game,
                List<EndRecordHorseInfo> sortedRaceEndRecordNode, Map<Integer, Integer> rankToAwardMap) {
            
            table.add(new Label("Race end", game.getMainSkin())).colspan(5).row();
            for (int i = 0; i < sortedRaceEndRecordNode.size(); i++) {
                EndRecordHorseInfo horseInfo = sortedRaceEndRecordNode.get(i);
                int prize = rankToAwardMap.get(i);
                buildOnePrize(table, game, horseInfo, prize);
            }

            
        }

        private static void buildOnePrize(Table table, TextUmaGame game, EndRecordHorseInfo horseInfo, int prize) {
            table.add(new Label(horseInfo.getHorseName(), game.getMainSkin())).padRight(5);
            table.add(new Label(horseInfo.getReachTimeText(), game.getMainSkin())).padRight(5);
            table.add(new BaseAmountPairNode<>(game)
                    .lazyInit(game.getGameDictionary().resourceIdToShowName(ResourceType.COIN))
                    .update(prize)
                    );
            table.row();
        }


    }


    public void updateAsRaceEndResult(List<EndRecordHorseInfo> sortedRaceEndRecordNode, Map<Integer, Integer> rankToAwardMap) {
        this.clearChildren();
        
        MainInfoHelper.buildRaceEnd(this, parent.getGame(), sortedRaceEndRecordNode, rankToAwardMap);
        
        if (parent.getGame().debugMode) {
            this.debug();
        }
        
    }
    
    

}
