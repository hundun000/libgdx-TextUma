package hundun.gdxgame.textuma.core.ui.component.construction.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import hundun.gdxgame.textuma.core.logic.handler.BaseRaceActionHandler;
import hundun.gdxgame.textuma.core.logic.handler.BaseTrainActionHandler;
import hundun.gdxgame.textuma.core.ui.component.TextNinePatchWrapper;
import hundun.gdxgame.textuma.core.ui.component.TextSkinButton;
import hundun.gdxgame.textuma.core.ui.screen.UmaPlayScreen;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.PlayScreenLayoutConst;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class BaseUserActionControlNode extends Table implements ILogicFrameListener {
    UmaPlayScreen parent;
    UmaActionHandler model;
    Label constructionNameLabel;

    Label workingLevelLabel;

    TextSkinButton clickEffectButton;

    Table changeWorkingLevelGroup;


    private void clickedOrEnter() {
        if (model instanceof BaseTrainActionHandler) {
            if (model.canClickEffect()) {
                parent.getSecondaryInfoBoard().updateAsTrainActionHint((BaseTrainActionHandler)model);
            } else {
                parent.getSecondaryInfoBoard().updateAsText("Disabled");
            }
        } else if (model instanceof BaseRaceActionHandler) {
            if (model.canClickEffect()) {
                parent.getSecondaryInfoBoard().updateAsRaceActionHint((BaseRaceActionHandler) model);
            } else {
                parent.getSecondaryInfoBoard().updateAsText("Disabled");
            }
            
        }
    }


    public BaseUserActionControlNode(UmaPlayScreen parent, int index, PlayScreenLayoutConst playScreenLayoutConst) {
        super();
        this.parent = parent;

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_BOX_WIDTH;
        int BUTTON_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BOX_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BOX_NAME_HEIGHT;

        this.constructionNameLabel = new Label("", parent.game.getButtonSkin());
        //constructionNameLabel.setWrap(true);

        this.clickEffectButton = TextSkinButton.typeButton("", parent.game);
        clickEffectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (model.canClickEffect()) {
                    Gdx.app.log("clickEffectButton", "node of " + model.name + "clicked and can effect");
                    model.onEffectableClick();
                } else {
                    Gdx.app.log("clickEffectButton", "node of " + model.name + "clicked and cannot effect");
                }
            }
        });

        this.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (model != null) {
                    BaseUserActionControlNode.this.clickedOrEnter();
                }
                //Gdx.app.log(BaseUserActionControlNode.class.getSimpleName(), "clicked event");
                super.clicked(event, x, y);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (model != null && pointer == -1) {
                    BaseUserActionControlNode.this.clickedOrEnter();
                }
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    parent.getSecondaryInfoBoard().updateAsClear();
                }
                super.exit(event, x, y, pointer, toActor);
            }
        });

        // ------ changeWorkingLevelGroup ------
        this.changeWorkingLevelGroup = new Table();

        
        this.workingLevelLabel = new Label("", parent.game.getButtonSkin());
        workingLevelLabel.setAlignment(Align.center);
        changeWorkingLevelGroup.add(workingLevelLabel).size(CHILD_WIDTH / 2, BUTTON_CHILD_HEIGHT);

        

        // ------ this ------
        this.add(constructionNameLabel)
                .center()
                .size(CHILD_WIDTH, NAME_CHILD_HEIGHT)
                .row();
        this.add(clickEffectButton)
                .size(CHILD_WIDTH, BUTTON_CHILD_HEIGHT)
                .row();
        this.add(changeWorkingLevelGroup)
                .size(CHILD_WIDTH, BUTTON_CHILD_HEIGHT);
        //this.setBackground(parent.getLayoutConst().blackBoard);
    }


    public void setModel(UmaActionHandler model) {
        this.model = model;
    }

    @Override
    public void onLogicFrame() {
        // ------ update show-state ------
        if (model == null) {
            setVisible(false);
            //textButton.setVisible(false);
            //Gdx.app.log("ConstructionView", this.hashCode() + " no model");
            return;
        } else {
            setVisible(true);
            //textButton.setVisible(true);
            //Gdx.app.log("ConstructionView", model.getName() + " set to its view");
        }
        // ------ update text ------
        constructionNameLabel.setText(model.getName());
        clickEffectButton.contentSetText(model.getButtonDescroption());
        workingLevelLabel.setText(model.getWorkingLevelDescroption());

        // ------ update clickable-state ------
        boolean canClickEffect = model.canClickEffect();
        //clickEffectButton.setTouchable(clickable ? Touchable.enabled : Touchable.disabled);


        clickEffectButton.contentSetEnable(canClickEffect);

        // ------ update model ------
        //model.onLogicFrame();

    }


}
