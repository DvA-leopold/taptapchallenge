package com.taptap.game.save.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;

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
                save = json.fromJson(Save.class,file.readString());
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

    public void resetSavedData() {
        file.delete();
    }

    @SuppressWarnings("unchecked")
    public <T> T loadDataValue(String key) {
        if(save.data.containsKey(key))
            return (T)save.data.get(key);
        else
            return null;
    }

    public void saveDataValue(String key, Object object){
        save.data.put(key, object);
        saveToJson();
    }

    public ObjectMap<String, Object> getAllData(){
        return save.data;
    }

    public static class Save {
        public ObjectMap<String, Object> data = new ObjectMap<String, Object>();
    }

    private Save save;
    private boolean encoded;
    private FileHandle file;
}
