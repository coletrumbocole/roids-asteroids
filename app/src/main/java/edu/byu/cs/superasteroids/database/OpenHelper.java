package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.Scanner;

/**
 * Created by cole on 7/18/16.
 */
public class OpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "asteroidsGameBase.db";
    private Context createdInContext;

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.createdInContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Scanner s;
        try {
            s = new Scanner(createdInContext.getAssets().open("createStatements.txt"));
            s.useDelimiter(";");
        } catch (Exception e) {
            System.out.println("the createStatements.txt asset broke the OpenHelper.onCreate()");
            e.printStackTrace();
            return;
        }
        while (s.hasNext()) {
            String sql = s.next();
            db.beginTransaction();
            db.execSQL(sql);
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
