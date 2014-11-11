package com.taptap.game.save.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

    public void deleteFile() {
        if (!file.delete()){
            System.out.println("error file wasn`t deleted");
        }
    }

    @SuppressWarnings("unchecked")
    public<T> T loadDataValue(String key) {
        if(save.data.containsKey(key))
            return (T)save.data.get(key);
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    public void saveDataValue(String key, Integer value){
        save.data.put(key, value);
        List list = new ArrayList(save.data.entrySet());
        Collections.sort(list, new Comparator<Map.Entry>() {
            @Override
            public int compare(Map.Entry o1, Map.Entry o2) {
                return ((Integer) o1.getValue()).compareTo((Integer)o2.getValue());
            }
        });

        saveToJson();
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
