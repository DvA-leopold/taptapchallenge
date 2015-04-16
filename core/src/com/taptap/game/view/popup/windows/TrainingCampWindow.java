package com.taptap.game.view.popup.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TrainingCampWindow extends Window {
    public TrainingCampWindow(String title, Skin skin) {
        super(title, skin);
        this.setSize(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.7f);
        this.setPosition(
                Gdx.graphics.getWidth() * 0.5f - this.getWidth() * 0.5f,
                Gdx.graphics.getHeight() * 0.5f - this.getHeight() * 0.5f
        );
        this.buttonWidth = this.getWidth() / 4;
        this.buttonHeight = this.getHeight() / 4;
        this.setVisible(false);
        this.skin = skin;
        skillGroup = new ButtonGroup<>();
    }

    public void createButtons() {
        CheckBox bowBox = new CheckBox(null, skin, "bowBox");
        bowBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: switch box
            }
        });
        CheckBox swordBox = new CheckBox(null, skin, "swordBox");
        swordBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: switch box
            }
        });
        CheckBox staffBox = new CheckBox(null, skin, "staffBox");
        staffBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: switch box
            }
        });
        CheckBox spellBox = new CheckBox(null, skin, "spellBox");
        spellBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO: switch box
            }
        });
        Button exitButton = new Button(skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        skillGroup.add(bowBox, swordBox, staffBox, spellBox);
        this.left();
        this.add(bowBox).width(buttonWidth).height(buttonHeight).pad(1);
        this.row();
        this.add(swordBox).width(buttonWidth).height(buttonHeight).pad(1);
        this.row();
        this.add(staffBox).width(buttonWidth).height(buttonHeight).pad(1);
        this.row();
        this.add(spellBox).width(buttonWidth).height(buttonHeight).pad(1);
        this.add(exitButton).width(buttonWidth).height(buttonHeight);
        this.debug();
    }


    private final Skin skin;
    private ButtonGroup<CheckBox> skillGroup;

    private float buttonWidth, buttonHeight;
}
