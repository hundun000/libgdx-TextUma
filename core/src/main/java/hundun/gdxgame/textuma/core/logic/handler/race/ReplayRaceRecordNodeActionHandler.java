package hundun.gdxgame.textuma.core.logic.handler.race;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;

import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class ReplayRaceRecordNodeActionHandler extends BaseRaceActionHandler {

    public ReplayRaceRecordNodeActionHandler(TextUmaGame game) {
        super(game, UserActionId.REPLAY_RACE_RECORD);
        this.descriptionPackage = UmaActionHandler.RACE_DESCRIPTION_PACKAGE;
    }


    @Override
    public void onEffectableClick() {
        game.getGameplayUIController().replayRaceRecord();
    }

    @Override
    public ClickEffectType canClickEffect() {
        if (!game.getGameplayUIController().isWaitingEndRaceRecord()) {
            return ClickEffectType.CANNOT_BY_STATE;
        }
        return ClickEffectType.CAN;
    }

//    @Override
//    public String getPopupInfo() {
//        return this.getClass().getSimpleName() + " PopupInfo";
//    }

}
