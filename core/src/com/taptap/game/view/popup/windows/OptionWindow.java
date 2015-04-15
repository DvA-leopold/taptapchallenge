package com.taptap.game.view.popup.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.taptap.game.TapTap;
import com.taptap.game.internationalization.I18NBundleMy;

public class OptionWindow extends Window {
    public OptionWindow(String title, Skin skin) {
        super(title, skin);
        setFillParent(true);
        setVisible(false);
        this.skin = skin;
    }

    public void initButtonSize(float buttonWidth, float buttonHeight) {
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
    }

    public void createButtons() {
        voiceButton = new CheckBox("v", skin, "owCheckBox");
        musicButton = new CheckBox("m", skin, "owCheckBox");
        languageButton = new TextButton(I18NBundleMy.getLangCodes(), skin);
        deleteSavesButton = new TextButton(I18NBundleMy.getString("delete"), skin);
        closeButton = new Button(skin);
        this.add(voiceButton).width(buttonWidth * 2).height(buttonHeight).pad(10);
        this.add(musicButton).width(buttonWidth * 2).height(buttonHeight).pad(10);
        this.add(closeButton).width(buttonWidth / 2).height(buttonHeight / 2).top().right();
        this.row();
        this.add(languageButton).width(buttonWidth * 2).height(buttonHeight).pad(10);
        this.add(deleteSavesButton).width(buttonWidth * 2).height(buttonHeight).pad(10);
        this.debug();
    }

    public void setListeners() {
        voiceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: switch the music
            }
        });
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((TapTap) Gdx.app.getApplicationListener()).getMusicManager().onOffMusic();
            }
        });
        languageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                I18NBundleMy.initLangCode(new String[]{"en", "EN", "VAR1"});
            }
        });
        deleteSavesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: delete saved data
            }
        });
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
    }

    private final Skin skin;

    private CheckBox voiceButton, musicButton;
    private TextButton languageButton, deleteSavesButton;
    private Button closeButton;

    private float buttonWidth;
    private float buttonHeight;
}
