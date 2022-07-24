package hundun.gdxgame.textuma.share.framework.model.entity;


/**
 * @author hundun
 * Created on 2021/12/04
 */
public class RandomMoveEntity extends GameEntity {

    private float FLY_MIN_X;
    private float FLY_MAX_X;
    private float FLY_MIN_Y;
    private float FLY_MAX_Y;
    private double FLY_UNION_SPEED;

    int randomMoveCount;
    // ------ replace-lombok ------
    public int getRandomMoveCount() {
        return randomMoveCount;
    }
    public void setRandomMoveCount(int randomMoveCount) {
        this.randomMoveCount = randomMoveCount;
    }

    public RandomMoveEntity(float FLY_MIN_X, float FLY_MAX_X, float FLY_MIN_Y, float FLY_MAX_Y,
            double FLY_UNION_SPEED) {
        super();
        this.FLY_MIN_X = FLY_MIN_X;
        this.FLY_MAX_X = FLY_MAX_X;
        this.FLY_MIN_Y = FLY_MIN_Y;
        this.FLY_MAX_Y = FLY_MAX_Y;
        this.FLY_UNION_SPEED = FLY_UNION_SPEED;
    }



    @Override
    public void frameLogic() {
        if (this.getRandomMoveCount() > 0) {
            this.setRandomMoveCount(this.getRandomMoveCount() - 1);
        } else {
            this.setRandomMoveCount(50 + (int) (Math.random() * 50));
            double angle = Math.toRadians(Math.random() * 360);
            double unionSpeed = FLY_UNION_SPEED;
            this.setSpeedX((float) (unionSpeed * Math.cos(angle)));
            this.setSpeedY((float) (unionSpeed * Math.sin(angle)));
        }

        if ((this.getX() < FLY_MIN_X && this.getSpeedX() < 0)
                || (this.getX() + this.getDrawWidth() > FLY_MAX_X && this.getSpeedX() > 0)) {
            this.setSpeedX(this.getSpeedX() * -1);
        }
        if ((this.getY() < FLY_MIN_Y && this.getSpeedY() < 0)
                || (this.getY() + this.getDrawHeight() > FLY_MAX_Y && this.getSpeedY() > 0)) {
            this.setSpeedY(this.getSpeedY() * -1);
        }

        this.setTextureFlipX(this.getSpeedX() < 0);

    }

}
