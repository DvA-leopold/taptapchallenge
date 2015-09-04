package com.taptap.game.view.popup.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.taptap.game.TapTap;
import com.taptap.game.internationalization.I18NBundleMy;
import com.taptap.game.model.music.player.MusicManager;

public class TownMainOptionWindow extends Window implements Disposable {
    public TownMainOptionWindow(String title, Skin skin) {
        super(title, skin);
        this.setSize(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.7f);
        this.setPosition(
                Gdx.graphics.getWidth() * 0.5f - this.getWidth() * 0.5f,
                Gdx.graphics.getHeight() * 0.5f - this.getHeight() * 0.5f);
        this.setVisible(false);
        this.setModal(true);
        this.skin = skin;
    }

    public void initButtonSize(float buttonWidth, float buttonHeight) {
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
    }

    public void createButtons() {
        soundButton = new CheckBox(null, skin, "soundCheckBox");
        musicButton = new CheckBox(null, skin, "musicCheckBox");
        musicButton.setChecked(!MusicManager.isMusicEnable());
        languageButton = new TextButton(I18NBundleMy.getLanguageCode(), skin);
        deleteSavesButton = new TextButton(I18NBundleMy.getString("delete"), skin);
        closeButton = new Button(skin, "close");

        this.add(soundButton).width(buttonWidth * 2).height(buttonHeight).pad(10);
        this.add(musicButton).width(buttonWidth * 2).height(buttonHeight).pad(10);
        this.add(closeButton).width(buttonWidth / 2).height(buttonHeight / 2).top().right();
        this.row();
        this.add(languageButton).width(buttonWidth * 2).height(buttonHeight).pad(10);
        this.add(deleteSavesButton).width(buttonWidth * 2).height(buttonHeight).pad(10);
        this.debug();
    }

    public void setListeners() {
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: switch the sound
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
                I18NBundleMy.changeLanguage();
                languageButton.setText(I18NBundleMy.getLanguageCode());
                deleteSavesButton.setText(I18NBundleMy.getString("delete"));
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

    @Override
    public void dispose() {
        //skin.dispose();
    }


    final private Skin skin;
    private CheckBox soundButton, musicButton;
    private TextButton languageButton, deleteSavesButton;

    private Button closeButton;
    private float buttonWidth;

    private float buttonHeight;
}
