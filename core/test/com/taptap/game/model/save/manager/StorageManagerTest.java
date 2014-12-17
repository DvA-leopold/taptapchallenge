package com.taptap.game.model.save.manager;

import com.badlogic.gdx.Gdx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageManagerTest {

    @Before
    public void setUp() throws Exception {
        System.out.println(Gdx.files.getLocalStoragePath());
        System.out.printf("test running");

        //testStorage = new StorageManager(true, "saved_records.json");
    }

    @Test
    public void testSaveDataValue() throws Exception {
        assertFalse(true);
        assertFalse(false);
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