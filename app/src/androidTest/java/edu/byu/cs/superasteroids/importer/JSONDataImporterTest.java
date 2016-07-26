package edu.byu.cs.superasteroids.importer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.DAO;
import edu.byu.cs.superasteroids.model.LevelObject;

import static org.junit.Assert.*;

/**
 * Created by cole on 7/24/16.
 */
public class JSONDataImporterTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testImportData() throws Exception {

    }

    @Test
    public void testGetLevelObjects() throws Exception  {
        ArrayList<LevelObject> levelObjects = DAO.getSingletonInstance().getLevelObjects();
        for (LevelObject obj : levelObjects) {
            assertNotNull(obj);
        }
    }
}