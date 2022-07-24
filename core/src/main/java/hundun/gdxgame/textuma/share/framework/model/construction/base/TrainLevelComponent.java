package hundun.gdxgame.textuma.share.framework.model.construction.base;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.textuma.core.logic.handler.BaseTrainActionHandler;


/**
 * @author hundun
 * Created on 2021/12/17
 */
public class TrainLevelComponent {

    private final BaseTrainActionHandler construction;
    
    protected static final int DEFAULT_MAX_LEVEL = 99;
    protected int maxLevel = DEFAULT_MAX_LEVEL;

    public TrainLevelComponent(BaseTrainActionHandler construction) {
        super();
        this.construction = construction;
    }

    public String getWorkingLevelDescroption() {
        boolean reachMaxLevel = construction.getSaveData().getLevel() == this.maxLevel;
        return construction.descriptionPackage.getLevelDescroptionProvider().provide(construction.saveData.getLevel(), construction.saveData.getWorkingLevel(), reachMaxLevel);
    }

    public boolean canChangeWorkingLevel(int delta) {
        int next = construction.saveData.getWorkingLevel() + delta;
        if (next > construction.saveData.getLevel() || next < 0) {
            return false;
        }
        return true;
    }

    public void changeWorkingLevel(int delta) {
        if (canChangeWorkingLevel(delta)) {
            construction.saveData.setWorkingLevel(construction.saveData.getWorkingLevel() + delta);
            construction.updateModifiedValues();
            Gdx.app.log(construction.name, "changeWorkingLevel delta = " + delta + ", success to " + construction.saveData.getWorkingLevel());
        } else {
            Gdx.app.log(construction.name, "changeWorkingLevel delta = " + delta + ", but cannot!");
        }
    }

}
