package hundun.gdxgame.textuma.core.logic.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hundun.gdxgame.textuma.core.logic.UserActionId;
import hundun.gdxgame.textuma.core.logic.GameArea;
import hundun.gdxgame.textuma.core.logic.ResourceType;
import hundun.gdxgame.textuma.share.framework.model.manager.AbstractTextureManager;

public class TextureManager extends AbstractTextureManager {

    private Texture textureOrDefault(FileHandle file) {
        try {
            return new Texture(file);
        } catch (Exception e) {
            return new Texture(Gdx.files.internal("badlogic.jpg"));
        }
    }
    
    public TextureManager() {

        winTexture = textureOrDefault(Gdx.files.internal("win.png"));
        menuTexture = textureOrDefault(Gdx.files.internal("menu.png"));
        {
            Texture texture = textureOrDefault(Gdx.files.internal("resourceIcons.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
            defaultIcon = regions[0][0];
            resourceIconMap.put(ResourceType.COIN, "cion: ");
            //resourceIconMap.put(ResourceType.COOKIE, regions[0][2]);
        }
        {
            Texture texture = textureOrDefault(Gdx.files.internal("constructionEntities.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 32, 32);
            //constructionEntityMap.put(ConstructionId.COOKIE_CLICK_PROVIDER, regions[0][0]);
            constructionEntityMap.put(UserActionId.RUNNING_TRAIN, regions[0][1]);
            //constructionEntityMap.put(ConstructionId.WIN_PROVIDER, regions[0][3]);
        }  
        {
            Texture texture = textureOrDefault(Gdx.files.internal("areas.png"));
            TextureRegion[][] regions = TextureRegion.split(texture, 640, 480);
            defaultAreaBack = regions[0][0];
            gameAreaBackMap.put(GameArea.AREA_RACE, regions[0][1]);
            gameAreaBackMap.put(GameArea.AREA_TRAIN, regions[0][2]);
            gameAreaBackMap.put(GameArea.AREA_RECORD, regions[0][3]);
        }
        
    }

}
