package hundun.gdxgame.textuma.core.logic.handler.race;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;

import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class EndRaceRecordNodeActionHandler extends BaseRaceActionHandler {

    public EndRaceRecordNodeActionHandler(TextUmaGame game) {
        super(game, UserActionId.END_RACE_RECORD);
        this.descriptionPackage = UmaActionHandler.RACE_DESCRIPTION_PACKAGE;
    }


    @Override
    public void onEffectableClick() {
        game.getGameplayUIController().endRaceRecord();
    }

    @Override
    public ClickEffectType canClickEffect() {
        if (!game.getGameplayUIController().isWaitingEndRaceRecord()) {
            return ClickEffectType.CANNOT_BY_STATE;
        }
        return ClickEffectType.CAN;
    }


    


//    @OVERRIDE
//    PUBLIC STRING GETPOPUPINFO() {
//        RETURN THIS.GETCLASS().GETSIMPLENAME() + " POPUPINFO";
//    }

}
