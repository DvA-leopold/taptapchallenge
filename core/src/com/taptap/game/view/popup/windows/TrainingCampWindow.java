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
        this.setModal(true);
        // sum of all buttons sizes must be smaller then window size
        this.buttonWidth = this.getWidth() / 7;
        this.buttonHeight = this.getHeight() / 4;
        this.setVisible(false);
        this.skin = skin;
        bowTable = new Table();
        initBowTable();
        currentTable = bowTable;
        swordTable = new Table();
        initSwordTable();
        staffTable = new Table();
        spellTable = new Table();
        skillGroup = new ButtonGroup<>();
    }

    public void createButtons() {
        final Window thisWindow = this;
        final CheckBox bowBox = new CheckBox(null, skin, "bowBox");
        bowBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                thisWindow.removeActor(currentTable);
                currentTable = bowTable;
                thisWindow.addActorBefore(bowBox, currentTable);
            }
        });
        final CheckBox swordBox = new CheckBox(null, skin, "swordBox");
        swordBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                thisWindow.removeActor(currentTable);
                currentTable = swordTable;
                thisWindow.addActorBefore(swordBox, currentTable);
            }
        });
        CheckBox staffBox = new CheckBox(null, skin, "staffBox");
        staffBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentTable = staffTable;
            }
        });
        CheckBox spellBox = new CheckBox(null, skin, "spellBox");
        spellBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentTable = spellTable;
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
        this.add(bowBox).width(buttonWidth).height(buttonHeight).expandY().pad(1);
        this.add(currentTable).size(this.getWidth() - buttonWidth * 2, this.getHeight()).
                padTop(this.getHeight() / 2 + buttonHeight);
        this.add(exitButton).width(buttonWidth).height(buttonHeight);
        this.row();
        this.add(swordBox).width(buttonWidth).height(buttonHeight).pad(1);
        this.row();
        this.add(staffBox).width(buttonWidth).height(buttonHeight).pad(1);
        this.row();
        this.add(spellBox).width(buttonWidth).height(buttonHeight).pad(1);
        this.debug();
    }

    private void initBowTable() {
        bowTable.add(new CheckBox(null, skin, "bowBox"));
        bowTable.add(new CheckBox(null, skin, "bowBox"));
        bowTable.add(new CheckBox(null, skin, "bowBox"));
        bowTable.add(new CheckBox(null, skin, "bowBox"));
    }

    private void initSwordTable() {
        swordTable.add(new CheckBox(null, skin, "swordBox"));
        swordTable.add(new CheckBox(null, skin, "swordBox"));
        swordTable.add(new CheckBox(null, skin, "swordBox"));
        swordTable.add(new CheckBox(null, skin, "swordBox"));
    }

    final private Table bowTable, swordTable, staffTable, spellTable;
    private Table currentTable;
    final private Skin skin;
    private ButtonGroup<CheckBox> skillGroup;
    private float buttonWidth, buttonHeight;
}
