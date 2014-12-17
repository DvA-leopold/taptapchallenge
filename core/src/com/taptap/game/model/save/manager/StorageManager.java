package com.taptap.game.model.save.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.*;
import java.util.*;

public class StorageManager {
    public StorageManager(boolean encoded){
        this.encoded = encoded;
        file = Gdx.files.local("saves/saved_records.json");
        save = getSave();
    }

    private Save getSave(){
        Save save = new Save();
        if(file.exists()){
            Json json = new Json();
            if(encoded)
                save = json.fromJson(Save.class, Base64Coder.decodeString(file.readString()));
            else
                save = json.fromJson(Save.class, file.readString());
        }
        return save;
    }

    private void saveToJson(){
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        if(encoded)
            file.writeString(Base64Coder.encodeString(json.prettyPrint(save)), false);
        else
            file.writeString(json.prettyPrint(save), false);
    }

    @SuppressWarnings("unchecked")
    public<T> T loadDataValue(String key) {
        if(save.data.containsKey(key))
            return (T)save.data.get(key);
        else
            return null;
    }

    public void saveDataValue(String key, Integer value){
        save.data.put(key, value);
        saveToJson();
    }

    @SuppressWarnings("unchecked")
    public void displayData(Table table, Skin skinRecords){
        List<Map.Entry> list = new ArrayList(save.data.entrySet());
        Collections.sort(list, new Comparator<Map.Entry>() {
            @Override
            public int compare(Map.Entry o1, Map.Entry o2) {
                return ((Integer) o2.getValue()).compareTo((Integer)o1.getValue());
            }
        });
        save.data.clear();
        try {
            Iterator lists = list.iterator();
            for (int i=0; i<5 && i<list.size(); i++){
                Map.Entry me = (Map.Entry)lists.next();
                //System.out.println(me.getKey() + " " + me.getValue());
                String score;
                if (i==0){
                    score = me.getKey() + " (best score) " + me.getValue();
                } else {
                    score = me.getKey() + " " + me.getValue();
                }
                table.add(new Label(score, skinRecords)).pad(10).row();
                save.data.put((String)me.getKey(), (Integer)me.getValue());
            }

        } catch (NoSuchElementException e){
            table.add(new Label("no score yet", skinRecords)).pad(10).row();
        }
    }

    public Map<String, Integer> getAllData(){
        return save.data;
    }

    private static class Save {
        public Map<String, Integer> data = new TreeMap<String, Integer>();
    }

    private Save save;
    private boolean encoded;
    private FileHandle file;
}
