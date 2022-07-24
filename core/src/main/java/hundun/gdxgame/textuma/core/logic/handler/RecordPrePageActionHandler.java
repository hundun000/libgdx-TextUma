package hundun.gdxgame.textuma.core.logic.handler;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2022/08/04
 */
public class RecordPrePageActionHandler extends BaseRecordActionHandler {

    public RecordPrePageActionHandler(TextUmaGame game) {
        super(game, UserActionId.RECORD_PRE_PAGE);
        
        this.descriptionPackage = UmaActionHandler.RECORD_DESCRIPTION_PACKAGE;
    }

    @Override
    public void onEffectableClick() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean canClickEffect() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getWorkingLevelDescroption() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPopupInfo() {
        return "go ot PrePage";
    }

}
