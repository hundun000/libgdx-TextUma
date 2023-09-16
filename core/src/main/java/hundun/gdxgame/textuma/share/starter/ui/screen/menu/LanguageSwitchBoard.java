package hundun.gdxgame.textuma.share.starter.ui.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import hundun.gdxgame.corelib.base.util.DrawableFactory;
import hundun.gdxgame.textuma.share.framework.util.text.Language;
import lombok.Getter;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hundun
 * Created on 2023/03/02
 */
public class LanguageSwitchBoard extends Table {
    
    TextUmaMenuScreen parent;
    @Getter
    SelectBox<String> selectBox;
    Label restartHintLabel;
    private Map<Language, String> languageShowNameMap;
    
    LanguageSwitchBoard(TextUmaMenuScreen parent,
                        Language[] values,
                        Language current,
                        String startText,
                        String hintText,
                        Consumer<Language> onSelect
            ) {
        this.parent = parent;
        this.setBackground(DrawableFactory.createAlphaBoard(1, 1, Color.GRAY, 1.0f));
        this.languageShowNameMap = parent.getGame().getGameDictionary().getLanguageShowNameMap();
        
        this.add(new Label(startText, parent.getGame().getMainSkin()));


        this.selectBox = new SelectBox<>(parent.getGame().getMainSkin());
        selectBox.setItems(Stream.of(values)
                .map(it -> languageShowNameMap.get(it))
                .collect(Collectors.toList())
                .toArray(new String[] {})
                );
        selectBox.setSelected(languageShowNameMap.get(current));
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                restartHintLabel.setVisible(true);
                Language language = languageShowNameMap.entrySet().stream()
                        .filter(x -> x.getValue().equals(selectBox.getSelected()))
                        .findFirst()
                        .get().getKey();
                onSelect.accept(language);
            }
        });
        this.add(selectBox).row();
        
        this.restartHintLabel = new Label(hintText, parent.getGame().getMainSkin());
        restartHintLabel.setVisible(false);
        this.add(restartHintLabel);
        
        
    }
    
    
    
}
