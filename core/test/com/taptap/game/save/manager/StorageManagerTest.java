package com.taptap.game.save.manager;

import com.badlogic.gdx.Gdx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

public class StorageManagerTest {

    @Before
    public void setUp() throws Exception {
        System.out.println(Gdx.files.getLocalStoragePath());
        //testStorage = new StorageManager(true, "saved_records.json");
    }

    @Test
    public void testSaveDataValue() throws Exception {
        //testStorage.saveDataValue("some ", 5);
        //testStorage.saveDataValue("some1", 88);
        //testStorage.saveDataValue("some3", 55);

       // for (Map.Entry entry : testStorage.getAllData().entrySet()){
       //     System.out.println((Integer)entry.getValue());
       // }
    }

    @After
    public void tearDown() throws Exception {
        //testStorage.deleteFile();
    }

    private StorageManager testStorage2;
    private StorageManager testStorage;
}