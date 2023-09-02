package hundun.gdxgame.textuma.core.logic.handler;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;

import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public abstract class BaseRecordActionHandler extends UmaActionHandler {

    public BaseRecordActionHandler(TextUmaGame game, String userActionId) {
        super(game, userActionId);
    }


    @Override
    public void updateModifiedValues() {
        // TODO Auto-generated method stub
        
    }

    public abstract String getPopupInfo();
}
