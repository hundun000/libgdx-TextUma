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

import hundun.gdxgame.textuma.share.framework.listener.ILogicFrameListener;
import hundun.gdxgame.textuma.share.framework.model.construction.base.BaseConstruction;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.BasePlayScreen;
import hundun.gdxgame.textuma.share.starter.ui.screen.play.PlayScreenLayoutConst;


/**
 * @author hundun
 * Created on 2021/11/05
 */
public class ConstructionControlNode extends Table implements ILogicFrameListener {
    BasePlayScreen<?> parent;
    BaseConstruction model;
    Label constructionNameLabel;
    TextButton upWorkingLevelButton;
    TextButton downWorkingLevelButton;
    Label workingLevelLabel;

    TextButton clickEffectButton;

    Table changeWorkingLevelGroup;




    public ConstructionControlNode(BasePlayScreen<?> parent, int index, PlayScreenLayoutConst playScreenLayoutConst) {
        super();
        this.parent = parent;

        int CHILD_WIDTH = playScreenLayoutConst.CONSTRUCION_CHILD_WIDTH;
        int CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_BUTTON_HEIGHT;
        int NAME_CHILD_HEIGHT = playScreenLayoutConst.CONSTRUCION_CHILD_NAME_HEIGHT;

        this.constructionNameLabel = new Label("", parent.game.getButtonSkin());
        constructionNameLabel.setWrap(true);

        this.clickEffectButton = new TextButton("", parent.game.getButtonSkin());
        clickEffectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "clicked");
                model.onClick();
            }
        });

        this.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (model != null) {
                    parent.showAndUpdateGuideInfo(model);
                }
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "exit event");
                super.clicked(event, x, y);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (model != null && pointer == -1) {
                    parent.showAndUpdateGuideInfo(model);
                }
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    parent.hideAndCleanGuideInfo();
                }
                super.exit(event, x, y, pointer, toActor);
            }
        });

        // ------ changeWorkingLevelGroup ------
        this.changeWorkingLevelGroup = new Table();

        this.downWorkingLevelButton = new TextButton("-", parent.game.getButtonSkin());
        downWorkingLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ConstructionView", "down clicked");
                model.getLevelComponent().changeWorkingLevel(-1);
            }
        });
        changeWorkingLevelGroup.add(downWorkingLevelButton).size(CHILD_WIDTH / 4, CHILD_HEIGHT);

        this.workingLevelLabel = new Label("", parent.game.getButtonSkin());
        workingLevelLabel.setAlignment(Align.center);
        changeWorkingLevelGroup.add(workingLevelLabel).size(CHILD_WIDTH / 2, CHILD_HEIGHT);

        this.upWorkingLevelButton = new TextButton("+", parent.game.getButtonSkin());
        upWorkingLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(ConstructionControlNode.class.getSimpleName(), "up clicked");
                model.getLevelComponent().changeWorkingLevel(1);
            }
        });
        changeWorkingLevelGroup.add(upWorkingLevelButton).size(CHILD_WIDTH / 4, CHILD_HEIGHT);


        // ------ this ------
        this.add(constructionNameLabel).size(CHILD_WIDTH, NAME_CHILD_HEIGHT).row();
        this.add(clickEffectButton).size(CHILD_WIDTH, CHILD_HEIGHT).row();
        this.add(changeWorkingLevelGroup).size(CHILD_WIDTH, CHILD_HEIGHT);
        this.setBackground(BasePlayScreen.createBorderBoard(30, 10, 0.8f, 1));
    }


    private void initAsNormalStyle() {

        this.upWorkingLevelButton.setVisible(false);
        this.downWorkingLevelButton.setVisible(false);

        //changeWorkingLevelGroup.setVisible(false);

        //this.debug();
    }


    private void initAsChangeWorkingLevelStyle() {
        //clearStyle();

        //changeWorkingLevelGroup.setVisible(true);
        this.upWorkingLevelButton.setVisible(true);
        this.downWorkingLevelButton.setVisible(true);



    }

    public void setModel(BaseConstruction model) {
        this.model = model;
        if (model != null) {
            if (model.getLevelComponent().isWorkingLevelChangable()) {
                initAsChangeWorkingLevelStyle();
            } else {
                initAsNormalStyle();
            }
        }
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
        workingLevelLabel.setText(model.getLevelComponent().getWorkingLevelDescroption());

        // ------ update clickable-state ------
        boolean canClickEffect = model.canClickEffect();
        //clickEffectButton.setTouchable(clickable ? Touchable.enabled : Touchable.disabled);


        if (canClickEffect) {
            clickEffectButton.getLabel().setColor(Color.WHITE);
        } else {
            clickEffectButton.setDisabled(true);
            clickEffectButton.getLabel().setColor(Color.RED);
        }

        boolean canUpWorkingLevel = model.getLevelComponent().canChangeWorkingLevel(1);
        if (canUpWorkingLevel) {
            upWorkingLevelButton.getLabel().setColor(Color.WHITE);
        } else {
            upWorkingLevelButton.setDisabled(true);
            upWorkingLevelButton.getLabel().setColor(Color.RED);
        }

        boolean canDownWorkingLevel = model.getLevelComponent().canChangeWorkingLevel(-1);
        if (canDownWorkingLevel) {
            downWorkingLevelButton.getLabel().setColor(Color.WHITE);
        } else {
            downWorkingLevelButton.setDisabled(true);
            downWorkingLevelButton.getLabel().setColor(Color.RED);
        }
        // ------ update model ------
        //model.onLogicFrame();

    }


}
