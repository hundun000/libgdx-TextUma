package hundun.gdxgame.textuma.share.starter.ui.component.board.construction.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import hundun.gdxgame.textuma.core.logic.handler.BaseTrainActionHandler;
import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.construction.base.UmaActionHandler;
import hundun.gdxgame.textuma.share.starter.ui.component.TextNinePatchWrapper;
import hundun.gdxgame.textuma.share.starter.ui.component.TextSkinButton;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.PlayScreenLayoutConst;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class BaseUserActionControlNode extends Table implements ILogicFrameListener {
    BasePlayScreen<?> parent;
    UmaActionHandler model;
    Label constructionNameLabel;

    Label workingLevelLabel;

    TextSkinButton clickEffectButton;

    Table changeWorkingLevelGroup;




    public BaseUserActionControlNode(BasePlayScreen<?> parent, int index, PlayScreenLayoutConst playScreenLayoutConst) {
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
                Gdx.app.log("clickEffectButton", "node of " + model.name + "clicked");
                model.onClick();
            }
        });

        this.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (model != null) {
                    if (model instanceof BaseTrainActionHandler) {
                        parent.infoBoardAsTrainInfo((BaseTrainActionHandler)model);
                    }
                }
                //Gdx.app.log(BaseUserActionControlNode.class.getSimpleName(), "clicked event");
                super.clicked(event, x, y);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (model != null && pointer == -1) {
                    if (model instanceof BaseTrainActionHandler) {
                        parent.infoBoardAsTrainInfo((BaseTrainActionHandler)model);
                    }
                }
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    parent.infoBoardAsIdle();
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
        clickEffectButton.setText(model.getButtonDescroption());
        workingLevelLabel.setText(model.getSecondDescroption());

        // ------ update clickable-state ------
        boolean canClickEffect = model.canClickEffect();
        //clickEffectButton.setTouchable(clickable ? Touchable.enabled : Touchable.disabled);


        if (canClickEffect) {
            clickEffectButton.setColor(Color.WHITE);
        } else {
            //clickEffectButton.setDisabled(true);
            clickEffectButton.setColor(Color.RED);
        }
        // ------ update model ------
        //model.onLogicFrame();

    }


}
