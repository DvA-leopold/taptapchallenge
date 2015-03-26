package com.taptap.game.model.monsters.factory;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;

public class MonstersFactory {
    public MonstersFactory() {
        monsters = new HashMap<>(5);
    }

    public void initMonsterMap() {
        monsters.put("name", new UniversalMonsterTemplate(10, 50, 12));
        jsonReader = new JsonReader();

    }

    private JsonReader jsonReader;
    private HashMap<String, UniversalMonsterTemplate> monsters;
}
