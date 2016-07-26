package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.model.*;
import edu.byu.cs.superasteroids.database.GameDBSchema.*;

/**
 * Created by ctrumbo on 7/12/16.
 *
 * Accesses the SQLite database.
 * Creates to the database from JSON files before any ship building or game play.
 * During ship building and game play, info about parts, levels, etc is available from this object.
 */
public class DAO {
    private SQLiteDatabase db = null;
    public static final DAO DAOSingletonInstance = new DAO();


    /**
     * private constructor so that only one instance is ever created.
     */
    private DAO () { }


    /**
     * retrieves a LevelObject with data from the level_objects table in the db corresponding to the user's selection.

     * @return LevelObject object
     */
    public ArrayList<LevelObject> getLevelObjects () {
        ArrayList<LevelObject> objects = new ArrayList<>();
        Cursor c = db.query(LevelObjectsTable.NAME, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(LevelObjectsTable.Cols.ID));
            String image = c.getString(c.getColumnIndex(LevelObjectsTable.Cols.IMAGE));
            LevelObject lo = new LevelObject(id, image);
            objects.add(lo);
        }
        c.close();
        return objects;
    }


    /**
     * retrieves an Asteroid with data from the asteroids table in the db corresponding to the user's selection.
     * This asteroid can be passed to the Octoroid or GrowingAsteroid contructors.
     * @return Asteroid object
     */
    public ArrayList<Asteroid> getAsteroids () {
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        Cursor c = db.query(AsteroidsTable.NAME, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(AsteroidsTable.Cols.ID));
            String name = c.getString(c.getColumnIndex(AsteroidsTable.Cols.NAME));
            String image = c.getString(c.getColumnIndex(AsteroidsTable.Cols.IMAGE));
            int imageWidth = c.getInt(c.getColumnIndex(AsteroidsTable.Cols.IMAGE_WIDTH));
            int imageHeight = c.getInt(c.getColumnIndex(AsteroidsTable.Cols.IMAGE_HEIGHT));
            String type = c.getString(c.getColumnIndex(AsteroidsTable.Cols.TYPE));
            Asteroid mb = new Asteroid(id, name, image, imageWidth, imageHeight, type);
            asteroids.add(mb);
        }
        c.close();
        return asteroids;
    }


    /**
     * retrieves a Level with data from the levels table in the db corresponding to the user's selection.
     * Also contains info from the levels_level_objects_join and level_asteroids_join tables so that the level has the correct objects and asteroids

     * @return Level object
     */
    public ArrayList<Level> getLevels () {
        ArrayList<Level> levels = new ArrayList<>();
        Cursor c = db.query(LevelsTable.NAME, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(LevelsTable.Cols.ID));
            int number = c.getInt(c.getColumnIndex(LevelsTable.Cols.NUMBER));
            String title = c.getString(c.getColumnIndex(LevelsTable.Cols.TITLE));
            String hint = c.getString(c.getColumnIndex(LevelsTable.Cols.HINT));
            int width = c.getInt(c.getColumnIndex(LevelsTable.Cols.WIDTH));
            int height = c.getInt(c.getColumnIndex(LevelsTable.Cols.HEIGHT));
            String music = c.getString(c.getColumnIndex(LevelsTable.Cols.MUSIC));
            ArrayList<Asteroid> asteroids = getAsteroidsForLevel(id);
            ArrayList<LevelObject> objects = getLevelObjectsForLevel(id);
            Level level = new Level(id, number, title, hint, width, height, music, asteroids, objects);
            levels.add(level);
        }
        c.close();
        return levels;
    }


    private ArrayList<Asteroid> getAsteroidsForLevel(int levelId) {
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT "
                + LevelsAsteroidsJoinTable.Cols.ASTEROID_QUANTITY
                + ", " + AsteroidsTable.Cols.ID
                + ", " + AsteroidsTable.Cols.NAME
                + ", " + AsteroidsTable.Cols.IMAGE
                + ", " + AsteroidsTable.Cols.IMAGE_WIDTH
                + ", " + AsteroidsTable.Cols.IMAGE_HEIGHT
                + ", " + AsteroidsTable.Cols.TYPE
                + " FROM "
                + LevelsAsteroidsJoinTable.NAME + " as lev_ast, "
                + AsteroidsTable.NAME + " as a "
                + " WHERE "
                + "lev_ast." + LevelsAsteroidsJoinTable.Cols.ASTEROID_ID + " = a." + AsteroidsTable.Cols.ID
                + " AND " + LevelsAsteroidsJoinTable.Cols.LEVEL_ID + " = " + levelId, null);
        while (c.moveToNext()) {
            int qty = c.getInt(c.getColumnIndex(LevelsAsteroidsJoinTable.Cols.ASTEROID_QUANTITY));
            int asteroidId = c.getInt(c.getColumnIndex(AsteroidsTable.Cols.ID));
            String name = c.getString(c.getColumnIndex(AsteroidsTable.Cols.NAME));
            String image = c.getString(c.getColumnIndex(AsteroidsTable.Cols.IMAGE));
            int imageWidth = c.getInt(c.getColumnIndex(AsteroidsTable.Cols.IMAGE_WIDTH));
            int imageHeight = c.getInt(c.getColumnIndex(AsteroidsTable.Cols.IMAGE_HEIGHT));
            String type = c.getString(c.getColumnIndex(AsteroidsTable.Cols.TYPE));
            for (int i = 0; i < qty; i++) {
                 asteroids.add(new Asteroid(asteroidId, name, image, imageWidth, imageHeight, type));
            }
        }
        c.close();
        return asteroids;
    }


    private ArrayList<LevelObject> getLevelObjectsForLevel(int levelId) {
        ArrayList<LevelObject> objects = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT "
                + LevelObjectsTable.Cols.ID
                + ", " + LevelObjectsTable.Cols.IMAGE
                + ", " + LevelsLevelObjectsJoinTable.Cols.OBJECT_POSITION_X
                + ", " + LevelsLevelObjectsJoinTable.Cols.OBJECT_POSITION_Y
                + ", " + LevelsLevelObjectsJoinTable.Cols.OBJECT_SCALE
                + " FROM "
                + LevelsLevelObjectsJoinTable.NAME + " as lev_obj, "
                + LevelObjectsTable.NAME + " as o "
                + " WHERE "
                + "lev_obj." + LevelsLevelObjectsJoinTable.Cols.OBJECT_ID + " = o." + LevelObjectsTable.Cols.ID
                + " AND " + LevelsLevelObjectsJoinTable.Cols.LEVEL_ID + " = " + levelId, null);
        while (c.moveToNext()) {
            int positionX = c.getInt(c.getColumnIndex(LevelsLevelObjectsJoinTable.Cols.OBJECT_POSITION_X));
            int positionY = c.getInt(c.getColumnIndex(LevelsLevelObjectsJoinTable.Cols.OBJECT_POSITION_Y));
            double scale = c.getDouble(c.getColumnIndex(LevelsLevelObjectsJoinTable.Cols.OBJECT_SCALE));
            float scaleFloat = (float) scale;
            int objectId = c.getInt(c.getColumnIndex(LevelObjectsTable.Cols.ID));
            String image = c.getString(c.getColumnIndex(LevelObjectsTable.Cols.IMAGE));
            objects.add(new LevelObject(objectId, image, positionX, positionY, scaleFloat));
        }
        c.close();
        return objects;
    }


    /**
     * retrieves a MainBody from the main_bodies table according to the user's selection.
     * @return MainBody object with data corresponding to the db.
     *
     * take out all the main bodies from the table
     * iterate through them, and at each one create a MainBody from it and add it to the array list.
     */
    public ArrayList<MainBody> getMainBodies(){
        ArrayList<MainBody> mainBodies = new ArrayList<>();
        Cursor c = db.query(MainBodiesTable.NAME, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(MainBodiesTable.Cols.ID));
            int cannonX = c.getInt(c.getColumnIndex(MainBodiesTable.Cols.CANNON_ATTACH_X));
            int engineX = c.getInt(c.getColumnIndex(MainBodiesTable.Cols.ENGINE_ATTACH_X));
            int extraX = c.getInt(c.getColumnIndex(MainBodiesTable.Cols.EXTRA_ATTACH_X));
            int cannonY = c.getInt(c.getColumnIndex(MainBodiesTable.Cols.CANNON_ATTACH_Y));
            int engineY = c.getInt(c.getColumnIndex(MainBodiesTable.Cols.ENGINE_ATTACH_Y));
            int extraY = c.getInt(c.getColumnIndex(MainBodiesTable.Cols.EXTRA_ATTACH_Y));
            String image = c.getString(c.getColumnIndex(MainBodiesTable.Cols.IMAGE));
            int imageWidth = c.getInt(c.getColumnIndex(MainBodiesTable.Cols.IMAGE_WIDTH));
            int imageHeight = c.getInt(c.getColumnIndex(MainBodiesTable.Cols.IMAGE_HEIGHT));
            MainBody mb = new MainBody(id, cannonX, engineX, extraX, cannonY, engineY, extraY, image, imageWidth, imageHeight);
            mainBodies.add(mb);
        }
        c.close();
        return mainBodies;
    }


    /**
     * retrieves a Cannon with data from the cannons table in the db corresponding to the user's selection.
     * @return Cannon object
     */
    public ArrayList<Cannon> getCannons () {
        ArrayList<Cannon> cannons = new ArrayList<>();
        Cursor c = db.query(CannonsTable.NAME, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(CannonsTable.Cols.ID));
            int attachX = c.getInt(c.getColumnIndex(CannonsTable.Cols.ATTACH_POINT_X));
            int attachY = c.getInt(c.getColumnIndex(CannonsTable.Cols.ATTACH_POINT_Y));
            int emitX = c.getInt(c.getColumnIndex(CannonsTable.Cols.EMIT_POINT_X));
            int emitY = c.getInt(c.getColumnIndex(CannonsTable.Cols.EMIT_POINT_Y));
            String image = c.getString(c.getColumnIndex(CannonsTable.Cols.IMAGE));
            int imageWidth = c.getInt(c.getColumnIndex(CannonsTable.Cols.IMAGE_WIDTH));
            int imageHeight = c.getInt(c.getColumnIndex(CannonsTable.Cols.IMAGE_HEIGHT));
            String attackImage = c.getString(c.getColumnIndex(CannonsTable.Cols.ATTACK_IMAGE));
            int attackImageWidth = c.getInt(c.getColumnIndex(CannonsTable.Cols.ATTACK_IMAGE_WIDTH));
            int attackImageHeight = c.getInt(c.getColumnIndex(CannonsTable.Cols.ATTACK_IMAGE_HEIGHT));
            String attackSound = c.getString(c.getColumnIndex(CannonsTable.Cols.ATTACK_SOUND));
            int damage = c.getInt(c.getColumnIndex(CannonsTable.Cols.DAMAGE));
            Cannon cannon = new Cannon(id, attachX, attachY, emitX, emitY, image, imageWidth, imageHeight, attackImage, attackImageWidth, attackImageHeight, attackSound, damage);
            cannons.add(cannon);
        }
        c.close();
        return cannons;
    }


    /**
     * retrieves an ExtraPart with data from the extra_parts table in the db corresponding to the user's selection.
     * @return ExtraPart object
     */
    public ArrayList<ExtraPart> getExtraParts () {
        ArrayList<ExtraPart> parts = new ArrayList<>();
        Cursor c = db.query(ExtraPartsTable.NAME, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(ExtraPartsTable.Cols.ID));
            int attachX = c.getInt(c.getColumnIndex(ExtraPartsTable.Cols.ATTACH_POINT_X));
            int attachY = c.getInt(c.getColumnIndex(ExtraPartsTable.Cols.ATTACH_POINT_Y));
            String image = c.getString(c.getColumnIndex(ExtraPartsTable.Cols.IMAGE));
            int imageWidth = c.getInt(c.getColumnIndex(ExtraPartsTable.Cols.IMAGE_WIDTH));
            int imageHeight = c.getInt(c.getColumnIndex(ExtraPartsTable.Cols.IMAGE_HEIGHT));
            ExtraPart ep = new ExtraPart(id, attachX, attachY, image, imageWidth, imageHeight);
            parts.add(ep);
        }
        c.close();
        return parts;
    }


    /**
     * retrieves an Engine from the engines table according to the user's selection.
     * @return Engine object with data from the db corresponding to the user's selection.
     */
    public ArrayList<Engine> getEngines () {
        ArrayList<Engine> engines = new ArrayList<>();
        Cursor c = db.query(EnginesTable.NAME, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(EnginesTable.Cols.ID));
            int attachX = c.getInt(c.getColumnIndex(EnginesTable.Cols.ATTACH_POINT_X));
            int attachY = c.getInt(c.getColumnIndex(EnginesTable.Cols.ATTACH_POINT_Y));
            String image = c.getString(c.getColumnIndex(EnginesTable.Cols.IMAGE));
            int imageWidth = c.getInt(c.getColumnIndex(EnginesTable.Cols.IMAGE_WIDTH));
            int imageHeight = c.getInt(c.getColumnIndex(EnginesTable.Cols.IMAGE_HEIGHT));
            int baseSpeed = c.getInt(c.getColumnIndex(EnginesTable.Cols.BASE_SPEED));
            int baseTurnRate = c.getInt(c.getColumnIndex(EnginesTable.Cols.BASE_TURN_RATE));
            Engine e = new Engine(id, attachX, attachY, image, imageWidth, imageHeight, baseSpeed, baseTurnRate);
            engines.add(e);
        }
        c.close();
        return engines;
    }


    /**
     * retrieves a PowerCore with data from the power_cores table in the db corresponding to the user's selection.

     * @return PowerCore object
     */
    public ArrayList<PowerCore> getPowerCores () {
        ArrayList<PowerCore> powerCores = new ArrayList<>();
        Cursor c = db.query(PowerCoresTable.NAME, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(PowerCoresTable.Cols.ID));
            int cannonBoost = c.getInt(c.getColumnIndex(PowerCoresTable.Cols.CANNON_BOOST));
            int engineBoost = c.getInt(c.getColumnIndex(PowerCoresTable.Cols.ENGINE_BOOST));
            String image = c.getString(c.getColumnIndex(PowerCoresTable.Cols.IMAGE));
            PowerCore pc = new PowerCore(id, cannonBoost, engineBoost, image);
            powerCores.add(pc);
        }
        c.close();
        return powerCores;
    }


    public static DAO getSingletonInstance() {
        return DAOSingletonInstance;
    }


    public void setDb(SQLiteDatabase dbIn) {
        this.db = dbIn;
    }


    /**
     * Picks data out of "objects" JSONArray and inserts them as records into the database.
     */
    public void insertObjects (JSONArray objectsArr) {
        for (int i = 0; i < objectsArr.length(); i++) {
            ContentValues objectValues = new ContentValues();
            try {
                objectValues.put(LevelObjectsTable.Cols.ID, i + 1);
                objectValues.put(LevelObjectsTable.Cols.IMAGE, objectsArr.getString(i));
                db.insert(LevelObjectsTable.NAME, null, objectValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Picks data out of an asteroid JSONObject and inserts it as a record into the database.
     */
    public void insertAsteroids (JSONArray asteroidsArr) {
        for (int i = 0; i < asteroidsArr.length(); i++) {
            ContentValues asteroidValues = new ContentValues();
            try {
                JSONObject asteroidObj = asteroidsArr.getJSONObject(i);
                asteroidValues.put(AsteroidsTable.Cols.ID, i + 1);
                asteroidValues.put(AsteroidsTable.Cols.NAME, asteroidObj.getString("name"));
                asteroidValues.put(AsteroidsTable.Cols.IMAGE, asteroidObj.getString("image"));
                asteroidValues.put(AsteroidsTable.Cols.IMAGE_WIDTH, asteroidObj.getInt("imageWidth"));
                asteroidValues.put(AsteroidsTable.Cols.IMAGE_HEIGHT, asteroidObj.getInt("imageHeight"));
                asteroidValues.put(AsteroidsTable.Cols.TYPE, asteroidObj.getString("type"));
                db.insert(AsteroidsTable.NAME, null, asteroidValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Picks data out of a level JSONObject and inserts it as a record into the database.
     */
    public void insertLevels (JSONArray levelsArr) {
        for (int i = 0; i < levelsArr.length(); i++) {
            ContentValues levelValues = new ContentValues();
            try {
                JSONObject levelJSONObj = levelsArr.getJSONObject(i);
                int id = i + 1;
                levelValues.put(LevelsTable.Cols.ID, id);
                levelValues.put(LevelsTable.Cols.NUMBER, levelJSONObj.getInt("number"));
                levelValues.put(LevelsTable.Cols.TITLE, levelJSONObj.getString("title"));
                levelValues.put(LevelsTable.Cols.HINT, levelJSONObj.getInt("hint"));
                levelValues.put(LevelsTable.Cols.WIDTH, levelJSONObj.getInt("width"));
                levelValues.put(LevelsTable.Cols.HEIGHT, levelJSONObj.getString("height"));
                levelValues.put(LevelsTable.Cols.MUSIC, levelJSONObj.getString("music"));
                db.insert(LevelsTable.NAME, null, levelValues);
                insertLevelObjects(id, levelJSONObj.getJSONArray("levelObjects"));
                insertLevelAsteroids(id, levelJSONObj.getJSONArray("levelAsteroids"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Picks data out of a levelObject JSONArray and inserts records into the database.
     * Gets called during insertion of a level, so this inserts all the objects of that level.
     */
    private void insertLevelObjects (int levelID, JSONArray levelObjectsArr) {
        for (int i = 0; i < levelObjectsArr.length(); i++) {
            ContentValues levelObjectValues = new ContentValues();
            try {
                JSONObject levelObjectJSONObj = levelObjectsArr.getJSONObject(i);
                String positionCoordinate = levelObjectJSONObj.getString("position");
                int XCoordinate = getXCoordinateFromString(positionCoordinate);
                int YCoordinate = getYCoordinateFromString(positionCoordinate);
                levelObjectValues.put(LevelsLevelObjectsJoinTable.Cols.LEVEL_ID, levelID);
                levelObjectValues.put(LevelsLevelObjectsJoinTable.Cols.OBJECT_ID, levelObjectJSONObj.getInt("objectId"));
                levelObjectValues.put(LevelsLevelObjectsJoinTable.Cols.OBJECT_POSITION_X, XCoordinate);
                levelObjectValues.put(LevelsLevelObjectsJoinTable.Cols.OBJECT_POSITION_Y, YCoordinate);
                levelObjectValues.put(LevelsLevelObjectsJoinTable.Cols.OBJECT_SCALE, levelObjectJSONObj.getDouble("scale"));
                db.insert(LevelsLevelObjectsJoinTable.NAME, null, levelObjectValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Picks data out of a levelAsteroid JSONArray and inserts records into the database using a join statement to get the level matched with the levelAsteroids.
     */
    private void insertLevelAsteroids (int levelID, JSONArray levelAsteroidsArr) {
        for (int i = 0; i < levelAsteroidsArr.length(); i++) {
            ContentValues levelAsteroidValues = new ContentValues();
            try {
                JSONObject levelAsteroidJSONObj = levelAsteroidsArr.getJSONObject(i);
                levelAsteroidValues.put(LevelsAsteroidsJoinTable.Cols.LEVEL_ID, levelID);
                levelAsteroidValues.put(LevelsAsteroidsJoinTable.Cols.ASTEROID_ID, levelAsteroidJSONObj.getInt("asteroidId"));
                levelAsteroidValues.put(LevelsAsteroidsJoinTable.Cols.ASTEROID_QUANTITY, levelAsteroidJSONObj.getInt("number"));
                db.insert(LevelsAsteroidsJoinTable.NAME, null, levelAsteroidValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * insertMainBody: picks data out of a mainbody JSONObject and inserts it as a record into the database.
     */
    public void insertMainBodies (JSONArray mainBodiesArr) {
        for (int i = 0; i < mainBodiesArr.length(); i++) {
            ContentValues mainBodyValues = new ContentValues();
            try {
                JSONObject mainBodyJSONObj = mainBodiesArr.getJSONObject(i);

                int id = i + 1;
                mainBodyValues.put(MainBodiesTable.Cols.ID, id);

                String cannonAttachCoordinate = mainBodyJSONObj.getString("cannonAttach");
                String engineAttachCoordinate = mainBodyJSONObj.getString("engineAttach");
                String extraAttachCoordinate = mainBodyJSONObj.getString("extraAttach");

                int cannonAttachX = getXCoordinateFromString(cannonAttachCoordinate);
                mainBodyValues.put(MainBodiesTable.Cols.CANNON_ATTACH_X, cannonAttachX);
                int engineAttachX = getXCoordinateFromString(engineAttachCoordinate);
                mainBodyValues.put(MainBodiesTable.Cols.ENGINE_ATTACH_X, engineAttachX);
                int extraAttachX = getXCoordinateFromString(extraAttachCoordinate);
                mainBodyValues.put(MainBodiesTable.Cols.EXTRA_ATTACH_X, extraAttachX);

                int cannonAttachY = getYCoordinateFromString(cannonAttachCoordinate);
                mainBodyValues.put(MainBodiesTable.Cols.CANNON_ATTACH_Y, cannonAttachY);
                int engineAttachY = getYCoordinateFromString(engineAttachCoordinate);
                mainBodyValues.put(MainBodiesTable.Cols.ENGINE_ATTACH_Y, engineAttachY);
                int extraAttachY = getYCoordinateFromString(extraAttachCoordinate);
                mainBodyValues.put(MainBodiesTable.Cols.EXTRA_ATTACH_Y, extraAttachY);

                mainBodyValues.put(MainBodiesTable.Cols.IMAGE, mainBodyJSONObj.getString("image"));
                mainBodyValues.put(MainBodiesTable.Cols.IMAGE_WIDTH, mainBodyJSONObj.getInt("imageWidth"));
                mainBodyValues.put(MainBodiesTable.Cols.IMAGE_HEIGHT, mainBodyJSONObj.getInt("imageHeight"));
                db.insert(MainBodiesTable.NAME, null, mainBodyValues);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Picks data out of a cannon JSONObject and inserts it as a record into the database.
     */
    public void insertCannons (JSONArray cannonsArr) {
        for (int i = 0; i < cannonsArr.length(); i++) {
            ContentValues cannonValues = new ContentValues();
            try {
                JSONObject cannonJSONObj = cannonsArr.getJSONObject(i);

                int id = i + 1;
                cannonValues.put(CannonsTable.Cols.ID, id);

                String attachCoordinate = cannonJSONObj.getString("attachPoint");
                int attachXCoordinate = getXCoordinateFromString(attachCoordinate);
                int attachYCoordinate = getYCoordinateFromString(attachCoordinate);
                cannonValues.put(CannonsTable.Cols.ATTACH_POINT_X, attachXCoordinate);
                cannonValues.put(CannonsTable.Cols.ATTACH_POINT_Y, attachYCoordinate);

                String emitCoordinate = cannonJSONObj.getString("emitPoint");
                int emitXCoordinate = getXCoordinateFromString(emitCoordinate);
                int emitYCoordinate = getYCoordinateFromString(emitCoordinate);
                cannonValues.put(CannonsTable.Cols.EMIT_POINT_X, emitXCoordinate);
                cannonValues.put(CannonsTable.Cols.EMIT_POINT_Y, emitYCoordinate);

                cannonValues.put(CannonsTable.Cols.IMAGE, cannonJSONObj.getString("image"));
                cannonValues.put(CannonsTable.Cols.IMAGE_WIDTH, cannonJSONObj.getInt("imageWidth"));
                cannonValues.put(CannonsTable.Cols.IMAGE_HEIGHT, cannonJSONObj.getInt("imageHeight"));
                cannonValues.put(CannonsTable.Cols.ATTACK_IMAGE, cannonJSONObj.getString("attackImage"));
                cannonValues.put(CannonsTable.Cols.ATTACK_IMAGE_WIDTH, cannonJSONObj.getInt("attackImageWidth"));
                cannonValues.put(CannonsTable.Cols.ATTACK_IMAGE_HEIGHT, cannonJSONObj.getInt("attackImageHeight"));
                cannonValues.put(CannonsTable.Cols.ATTACK_SOUND, cannonJSONObj.getString("attackSound"));
                cannonValues.put(CannonsTable.Cols.DAMAGE, cannonJSONObj.getInt("damage"));

                db.insert(CannonsTable.NAME, null, cannonValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * Picks data out of an extraPart JSONObject and inserts it as a record into the database.
     */
    public void insertExtraParts (JSONArray extraPartsArr) {
        for (int i = 0; i < extraPartsArr.length(); i++) {
            ContentValues extraPartValues = new ContentValues();
            try {
                JSONObject extraPartJSONObj = extraPartsArr.getJSONObject(i);
                int id = i + 1;
                extraPartValues.put(ExtraPartsTable.Cols.ID, id);
                String attachCoordinate = extraPartJSONObj.getString("attachPoint");
                int attachXCoordinate = getXCoordinateFromString(attachCoordinate);
                int attachYCoordinate = getYCoordinateFromString(attachCoordinate);
                extraPartValues.put(ExtraPartsTable.Cols.ATTACH_POINT_X, attachXCoordinate);
                extraPartValues.put(ExtraPartsTable.Cols.ATTACH_POINT_Y, attachYCoordinate);
                extraPartValues.put(ExtraPartsTable.Cols.IMAGE, extraPartJSONObj.getString("image"));
                extraPartValues.put(ExtraPartsTable.Cols.IMAGE_WIDTH, extraPartJSONObj.getInt("imageWidth"));
                extraPartValues.put(ExtraPartsTable.Cols.IMAGE_HEIGHT, extraPartJSONObj.getInt("imageHeight"));
                db.insert(ExtraPartsTable.NAME, null, extraPartValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Picks data out of an engine JSONObject and inserts it as a record into the database.
     */
    public void insertEngines (JSONArray enginesArr) {
        for (int i = 0; i < enginesArr.length(); i++) {
            ContentValues engineValues = new ContentValues();
            try {
                JSONObject engineJSONObj = enginesArr.getJSONObject(i);
                int id = i + 1;
                engineValues.put(EnginesTable.Cols.ID, id);
                engineValues.put(EnginesTable.Cols.BASE_SPEED, engineJSONObj.getInt("baseSpeed"));
                engineValues.put(EnginesTable.Cols.BASE_TURN_RATE, engineJSONObj.getInt("baseTurnRate"));
                String attachCoordinate = engineJSONObj.getString("attachPoint");
                int attachXCoordinate = getXCoordinateFromString(attachCoordinate);
                int attachYCoordinate = getYCoordinateFromString(attachCoordinate);
                engineValues.put(EnginesTable.Cols.ATTACH_POINT_X, attachXCoordinate);
                engineValues.put(EnginesTable.Cols.ATTACH_POINT_Y, attachYCoordinate);
                engineValues.put(EnginesTable.Cols.IMAGE, engineJSONObj.getString("image"));
                engineValues.put(EnginesTable.Cols.IMAGE_WIDTH, engineJSONObj.getInt("imageWidth"));
                engineValues.put(EnginesTable.Cols.IMAGE_HEIGHT, engineJSONObj.getInt("imageHeight"));
                db.insert(EnginesTable.NAME, null, engineValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Picks data out of a powerCore JSONObject and inserts it as a record into the database.
     */
    public void insertPowerCores (JSONArray powerCoresArr) {
        for (int i = 0; i < powerCoresArr.length(); i++) {
            ContentValues powerCoreValues = new ContentValues();
            try {
                JSONObject powerCoreJSONObj = powerCoresArr.getJSONObject(i);
                int id = i + 1;
                powerCoreValues.put(PowerCoresTable.Cols.ID, id);
                powerCoreValues.put(PowerCoresTable.Cols.CANNON_BOOST, powerCoreJSONObj.getInt("cannonBoost"));
                powerCoreValues.put(PowerCoresTable.Cols.ENGINE_BOOST, powerCoreJSONObj.getInt("engineBoost"));
                powerCoreValues.put(PowerCoresTable.Cols.IMAGE, powerCoreJSONObj.getString("image"));
                db.insert(PowerCoresTable.NAME, null, powerCoreValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private int getXCoordinateFromString(String coordinateString) {
        String[] coordinateStringArray = coordinateString.split(",");
        int x = Integer.parseInt(coordinateStringArray[0]);
        return x;
    }


    private int getYCoordinateFromString(String coordinateString) {
        String[] coordinateStringArray = coordinateString.split(",");
        int y = Integer.parseInt(coordinateStringArray[1]);
        return y;
    }

}
