package hundun.gdxgame.textuma.core.data;

import hundun.gdxgame.gamelib.base.IFrontend;
import hundun.gdxgame.gamelib.base.save.ISaveTool;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import hundun.gdxgame.gamelib.starter.save.PairChildrenSaveHandler;
import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.data.RootSaveData.MySystemSettingSaveData;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.core.logic.manager.LibgdxFrontEndSaveData;
import hundun.gdxgame.textuma.share.framework.util.text.Language;
import hundun.simulationgame.umamusume.game.gameplay.UmaSaveDataFactory;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author hundun
 * Created on 2023/01/13
 */
public class TextUmaSaveHandler extends PairChildrenSaveHandler<RootSaveData, MySystemSettingSaveData, MyGameplaySaveData>{

    public TextUmaSaveHandler(IFrontend frontend, ISaveTool<RootSaveData> saveTool) {
        super(frontend, RootSaveData.Factory.INSTANCE, saveTool);
    }
    
    @Override
    protected RootSaveData genereateStarterRootSaveData() {
        MyGameplaySaveData saveData = new MyGameplaySaveData();
        saveData.setBuffAmounts(new HashMap<>());
        saveData.setConstructionSaveDataMap(new HashMap<>());
        saveData.setUnlockedAchievementNames(new HashSet<>());
        saveData.setUnlockedResourceTypes(new HashSet<>(JavaFeatureForGwt.listOf(
                ResourceType.TURN,
                ResourceType.COIN
        )));
        saveData.setUmaSaveData(JavaFeatureForGwt.mapOf(
                TextUmaGame.SINGLETON_ID,
                UmaSaveDataFactory.forNewAccount(TextUmaGame.SINGLETON_ID)));
        saveData.setGameRuleData(UmaSaveDataFactory.forNewGameRuleData());
        saveData.setFrontEndSaveData(new LibgdxFrontEndSaveData());

        MySystemSettingSaveData systemSettingSaveData = MySystemSettingSaveData.builder()
                .language(Language.CN)
                .build();

        return new RootSaveData(systemSettingSaveData, saveData);
    }

}
