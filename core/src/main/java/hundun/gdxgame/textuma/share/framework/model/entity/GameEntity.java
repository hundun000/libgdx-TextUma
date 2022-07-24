package hundun.gdxgame.textuma.share.framework.model.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * @author hundun
 * Created on 2021/11/16
 */
public class GameEntity {
    Sprite texture;
    float x;
    float y;
    int drawWidth;
    int drawHeight;
    boolean moveable;
    boolean hiden;
    float speedX;
    float speedY;
    boolean textureFlipX;

    public void frameLogic() {}
    public boolean checkRemove() {
        return false;
    }

    // ------ replace-lombok ------
    public Sprite getTexture() {
        return texture;
    }
    public void setTexture(Sprite texture) {
        this.texture = texture;
    }
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public int getDrawWidth() {
        return drawWidth;
    }
    public void setDrawWidth(int drawWidth) {
        this.drawWidth = drawWidth;
    }
    public int getDrawHeight() {
        return drawHeight;
    }
    public void setDrawHeight(int drawHeight) {
        this.drawHeight = drawHeight;
    }
    public boolean isMoveable() {
        return moveable;
    }
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }
    public boolean isHiden() {
        return hiden;
    }
    public void setHiden(boolean hiden) {
        this.hiden = hiden;
    }
    public float getSpeedX() {
        return speedX;
    }
    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }
    public float getSpeedY() {
        return speedY;
    }
    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
    public boolean isTextureFlipX() {
        return textureFlipX;
    }
    public void setTextureFlipX(boolean textureFlipX) {
        this.textureFlipX = textureFlipX;
    }
}
