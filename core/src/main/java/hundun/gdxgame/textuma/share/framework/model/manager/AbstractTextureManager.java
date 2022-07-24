package hundun.gdxgame.textuma.share.framework.model.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AbstractTextureManager {

    protected Texture winTexture;
    // ------ replace-lombok ------
    public Texture getWinTexture() {
        return winTexture;
    }

    protected Texture menuTexture;
    // ------ replace-lombok ------
    public Texture getMenuTexture() {
        return menuTexture;
    }



    protected Map<String, String> resourceIconMap = new HashMap<>();
    protected Map<String, TextureRegion> resourceEntityMap = new HashMap<>();
    protected Map<String, TextureRegion> constructionEntityMap = new HashMap<>();
    protected Map<String, TextureRegion> gameAreaBackMap = new HashMap<>();

    protected String defaultIconString = "[?]";
    
    protected TextureRegion defaultIcon;
    protected TextureRegion defaultAreaBack;



    public TextureRegion getBackgroundTexture(String gameArea) {
        return gameAreaBackMap.getOrDefault(gameArea, defaultAreaBack);
    }

    public String getResourceIcon(String resourceType) {
        return resourceIconMap.getOrDefault(resourceType, defaultIconString);
    }

    public TextureRegion getResourceEntity(String resourceType) {
        return resourceEntityMap.getOrDefault(resourceType, defaultIcon);
    }

    public TextureRegion getConstructionEntity(String constructionId) {
        return constructionEntityMap.getOrDefault(constructionId, defaultIcon);
    }

}
