package hundun.gdxgame.textuma.core.logic.handler.race;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;

import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class NextRaceRecordNodeActionHandler extends BaseRaceActionHandler {

    public NextRaceRecordNodeActionHandler(TextUmaGame game) {
        super(game, UserActionId.NEXT_RACE_RECORD_NODE);
        this.descriptionPackage = UmaActionHandler.RACE_DESCRIPTION_PACKAGE;
    }


    @Override
    public void onEffectableClick() {
        game.getGameplayUIController().bigStepRaceRecordNode();
    }

    @Override
    public ClickEffectType canClickEffect() {
        if (!game.getGameplayUIController().isWaitingNextRaceRecordNode()) {
            return ClickEffectType.CANNOT_BY_STATE;
        }
        return ClickEffectType.CAN;
    }


    


//    @Override
//    public String getPopupInfo() {
//        return this.getClass().getSimpleName() + " PopupInfo";
//    }

}
