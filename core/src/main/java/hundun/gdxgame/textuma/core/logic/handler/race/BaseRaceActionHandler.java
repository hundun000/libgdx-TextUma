package hundun.gdxgame.textuma.core.logic.handler.race;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.share.framework.BaseHundunGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public abstract class BaseRaceActionHandler extends UmaActionHandler {

    public BaseRaceActionHandler(TextUmaGame game, String userActionId) {
        super(game, userActionId);
    }


    @Override
    public void updateModifiedValues() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getWorkingLevelDescroption() {
        return "";
    }

    //public abstract String getPopupInfo();
}
