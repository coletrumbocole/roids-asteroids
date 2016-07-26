package edu.byu.cs.superasteroids.importer;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import edu.byu.cs.superasteroids.database.DAO;

/**
 * Created by cole on 7/21/16.
 * Parses the JSON into Content
 */
public class JSONDataImporter implements IGameDataImporter {

    /**
     * Reads expected parts of the JSON file and inserts to the db.
     * Fills out the levels, level_objects, asteroids, backgrounds, engines, main_bodies, power_cores, cannons, and extra_parts tables.
     * Relies on private methods to parse specific objects of the JSON and insert correctly, creating join tables during parsing of levelAsteroids objects and levelObjects objects.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return
     */
    @Override
    public boolean importData(InputStreamReader dataInputReader) {
        JSONObject rootObject;
        try {
            rootObject = new JSONObject(makeString(dataInputReader));
            JSONObject asteroidsGameJSONObject = rootObject.getJSONObject("asteroidsGame");
            parseObjects(asteroidsGameJSONObject);
            parseAsteroids(asteroidsGameJSONObject);
            parseLevels(asteroidsGameJSONObject);
            parseMainBodies(asteroidsGameJSONObject);
            parseCannons(asteroidsGameJSONObject);
            parseExtraParts(asteroidsGameJSONObject);
            parseEngines(asteroidsGameJSONObject);
            parsePowerCores(asteroidsGameJSONObject);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private static String makeString(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[512];
        int n = 0;
        while ((n = reader.read(buf)) > 0) {
            sb.append(buf, 0, n);
        }
        return sb.toString();
    }


    /**
     * Looks for the array of "objects" and inserts them to the level_objects table.
     * @param asteroidsGame: the root object in the JSON file.
     */
    private void parseObjects (JSONObject asteroidsGame) throws Exception {
        JSONArray objectsArr = asteroidsGame.getJSONArray("objects");
        DAO.getSingletonInstance().insertObjects(objectsArr);
    }


    /**
     * Looks for "asteroids" and inserts them to the asteroids table.
     * @param asteroidsGame: the root object in the JSON file.
     */
    private void parseAsteroids (JSONObject asteroidsGame) throws Exception {
        JSONArray asteroidsArr = asteroidsGame.getJSONArray("asteroids");
        DAO.getSingletonInstance().insertAsteroids(asteroidsArr);
    }


    /**
     * Looks for "levels" and inserts them to the levels table.
     * @param asteroidsGame: the root object in the JSON file.
     */
    private void parseLevels (JSONObject asteroidsGame) throws Exception {
        JSONArray levelsArr = asteroidsGame.getJSONArray("levels");
        DAO.getSingletonInstance().insertLevels(levelsArr);
        // test to see that all necessary parts of JSON object is there.
        for (int i = 0; i < levelsArr.length(); i++) {
            JSONObject currLevelJSONObj = levelsArr.getJSONObject(i);
            currLevelJSONObj.getJSONArray("levelObjects");
            currLevelJSONObj.getJSONArray("levelObjects");
        }
    }


    /**
     * Looks for "mainBodies" and inserts them to the main_bodies table.
     * @param asteroidsGame: the root object in the JSON file.
     */
    private void parseMainBodies (JSONObject asteroidsGame) throws Exception {
        JSONArray mainBodiesArr = asteroidsGame.getJSONArray("mainBodies");
        DAO.getSingletonInstance().insertMainBodies(mainBodiesArr);
    }

    /**
     * Looks for "cannons" and inserts them to the cannons table.
     * @param asteroidsGame: the root object in the JSON file.
     */
    private void parseCannons (JSONObject asteroidsGame) throws Exception {
        JSONArray cannonsArr = asteroidsGame.getJSONArray("cannons");
        DAO.getSingletonInstance().insertCannons(cannonsArr);
    }

    /**
     * Looks for "extraParts" and inserts them to the extra_parts table.
     * @param asteroidsGame: the root object in the JSON file.
     */
    private void parseExtraParts (JSONObject asteroidsGame) throws Exception {
        JSONArray extraPartsArr = asteroidsGame.getJSONArray("extraParts");
        DAO.getSingletonInstance().insertExtraParts(extraPartsArr);
    }
    
    /**
     * Looks for "engines" and inserts them to the engines table.
     * @param asteroidsGame: the root object in the JSON file.
     */
    private void parseEngines (JSONObject asteroidsGame) throws Exception {
        JSONArray enginesArr = asteroidsGame.getJSONArray("engines");
        DAO.getSingletonInstance().insertEngines(enginesArr);
    }

    /**
     * Looks for "powerCores" and inserts them to the power_cores table.
     * @param asteroidsGame: the root object in the JSON file.
     */
    private void parsePowerCores (JSONObject asteroidsGame) throws Exception {
        JSONArray powerCoresArr = asteroidsGame.getJSONArray("powerCores");
        DAO.getSingletonInstance().insertPowerCores(powerCoresArr);
    }

}
