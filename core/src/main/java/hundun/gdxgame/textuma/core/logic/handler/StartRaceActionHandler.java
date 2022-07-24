package hundun.gdxgame.textuma.core.logic.handler;

import hundun.gdxgame.textuma.core.TextUmaGame;
import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.share.framework.BaseIdleGame;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;

/**
 * @author hundun
 * Created on 2022/08/03
 */
public class StartRaceActionHandler extends UmaActionHandler {

    public StartRaceActionHandler(TextUmaGame game) {
        super(game, UserActionId.START_RACE);
        
        this.detailDescroptionConstPart = "Details of StartRace";
        this.descriptionPackage = UmaActionHandler.MAX_LEVEL_AUTO_DESCRIPTION_PACKAGE;
        
    }


    @Override
    public void onClick() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean canClickEffect() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public String getSecondDescroption() {
        
        return "SecondDescroption of StartRaceActionHandler";
    }


    @Override
    public void updateModifiedValues() {
        // TODO Auto-generated method stub
        
    }


}
