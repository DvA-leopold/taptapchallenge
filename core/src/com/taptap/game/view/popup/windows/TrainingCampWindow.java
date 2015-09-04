package com.taptap.game.view.popup.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;

public class TrainingCampWindow extends Window implements Disposable {
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
        bowTable = initBowTable();
        swordTable = initSwordTable();
        staffTable = initStaffTable();
        spellTable = initSpellTable();
        skillGroup = new ButtonGroup<>();
    }

    public void createButtons() {
        final CheckBox bowBox = new CheckBox(null, skin, "bowBox");
        bowBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removeActor(currentTable);
                currentTable = bowTable;
                addActorAt(5, currentTable);
            }
        });
        final CheckBox swordBox = new CheckBox(null, skin, "swordBox");
        swordBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removeActor(currentTable);
                currentTable = swordTable;
                addActorAt(5, currentTable);
            }
        });
        final CheckBox staffBox = new CheckBox(null, skin, "staffBox");
        staffBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removeActor(currentTable);
                currentTable = staffTable;
                addActorAt(5, currentTable);
            }
        });
        final CheckBox spellBox = new CheckBox(null, skin, "spellBox");
        spellBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removeActor(currentTable);
                currentTable = spellTable;
                addActorAt(5, currentTable);
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
        this.add(currentTable).size(this.getWidth() - buttonWidth * 2, this.getHeight()).colspan(4);
        this.add(exitButton).width(buttonWidth).height(buttonHeight).top().right();
        this.row();
        this.add(swordBox).width(buttonWidth).height(buttonHeight).pad(1);
        this.row();
        this.add(staffBox).width(buttonWidth).height(buttonHeight).pad(1);
        this.row();
        this.add(spellBox).width(buttonWidth).height(buttonHeight).pad(1);

        this.debug();
    }

    private Table initBowTable() {
        Table bowTable = new Table();
        bowTable.add(new CheckBox(null, skin, "bowBox"));
        bowTable.add(new CheckBox(null, skin, "bowBox"));
        bowTable.add(new CheckBox(null, skin, "bowBox"));
        bowTable.add(new CheckBox(null, skin, "bowBox"));
        bowTable.setSize(this.getWidth() - buttonWidth * 2, this.getHeight());
        bowTable.padTop(this.getHeight() / 8 + buttonHeight);
        // TODO установка позиций для скилов на тейблах
        return bowTable;
    }

    private Table initSwordTable() {
        Table swordTable = new Table();
        swordTable.add(new CheckBox(null, skin, "swordBox"));
        swordTable.add(new CheckBox(null, skin, "swordBox"));
        swordTable.add(new CheckBox(null, skin, "swordBox"));
        swordTable.add(new CheckBox(null, skin, "swordBox"));
        swordTable.setSize(this.getWidth() - buttonWidth * 2, this.getHeight());
        swordTable.padTop(this.getHeight() / 8 + buttonHeight);
        return swordTable;
    }

    private Table initStaffTable() {
        Table staffTable = new Table();
        staffTable.add(new CheckBox(null, skin, "staffBox"));
        staffTable.add(new CheckBox(null, skin, "staffBox"));
        staffTable.add(new CheckBox(null, skin, "staffBox"));
        staffTable.add(new CheckBox(null, skin, "staffBox"));
        staffTable.setSize(this.getWidth() - buttonWidth * 2, this.getHeight());
        staffTable.padTop(this.getHeight() / 8 + buttonHeight);
        return staffTable;
    }

    private Table initSpellTable() {
        Table spellTable = new Table();
        spellTable.add(new CheckBox(null, skin, "spellBox"));
        spellTable.add(new CheckBox(null, skin, "spellBox"));
        spellTable.add(new CheckBox(null, skin, "spellBox"));
        spellTable.add(new CheckBox(null, skin, "spellBox"));
        spellTable.setSize(this.getWidth() - buttonWidth * 2, this.getHeight());
        spellTable.padTop(this.getHeight() / 8 + buttonHeight);
        return spellTable;
    }

    @Override
    public void dispose() {
        //skin.dispose();
    }


    final private Table bowTable, swordTable, staffTable, spellTable;
    private Table currentTable = null;

    final private Skin skin;
    private ButtonGroup<CheckBox> skillGroup;

    private float buttonWidth, buttonHeight;
}
