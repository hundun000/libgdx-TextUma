package hundun.gdxgame.textuma.share.framework.model.entity;

public class FailingEntity extends GameEntity {

    int removeY;
    int hidenFrameCount;


    public FailingEntity(int removeY, int hidenFrameCount) {
        super();
        this.removeY = removeY;
        this.hidenFrameCount = hidenFrameCount;
        this.hiden = hidenFrameCount > 0;
    }

    @Override
    public void frameLogic() {
        hidenFrameCount--;
        if (hidenFrameCount < 0) {
            hiden = false;
        }
    }

    @Override
    public boolean checkRemove() {
        return this.y <= removeY;
    }
}
