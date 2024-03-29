package hundun.gdxgame.textuma.core.logic.handler.race;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;

import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.simulationgame.umamusume.game.gameplay.data.AccountSaveData.OperationBoardState;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class StartRaceActionHandler extends BaseRaceActionHandler {

    public StartRaceActionHandler(TextUmaGame game) {
        super(game, UserActionId.START_RACE);
        
        this.descriptionPackage = UmaActionHandler.RACE_DESCRIPTION_PACKAGE;
        
    }


    @Override
    public void onEffectableClick() {
        game.getGameplayUIController().raceStart();
    }

    @Override
    public ClickEffectType canClickEffect() {
        if (game.getGameplayUIController().getOperationBoardState() != OperationBoardState.RACE_DAY_RACE_READY) {
            return ClickEffectType.CANNOT_BY_STATE;
        }
        return ClickEffectType.CAN;
    }


    


//    @Override
//    public String getPopupInfo() {
//        return this.getClass().getSimpleName() + " PopupInfo";
//    }

}
