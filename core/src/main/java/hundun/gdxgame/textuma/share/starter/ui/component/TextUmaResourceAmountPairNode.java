package hundun.gdxgame.textuma.share.starter.ui.component;

import hundun.gdxgame.textuma.core.TextUmaGame;


/**
 * @author hundun
 * Created on 2021/11/25
 */
public class TextUmaResourceAmountPairNode extends BaseAmountPairNode<TextUmaGame> {

    

    String resourceType;
    // ------ replace-lombok ------
    public String getResourceType() {
        return resourceType;
    }

    public TextUmaResourceAmountPairNode(TextUmaGame game, String resourceType) {
        super(game);
        this.resourceType = resourceType;
        String textureRegion = game.getGameDictionary().resourceIdToShowName(resourceType);
        lazyInit(textureRegion);
    }
    
    @Override
    public BaseAmountPairNode update(long amout) {
        return super.update(game.getTextFormatTool().format(amout));
    }

}
