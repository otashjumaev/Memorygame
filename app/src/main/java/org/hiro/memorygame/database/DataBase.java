package org.hiro.memorygame.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.hiro.memorygame.libs.DBHelper;
import org.hiro.memorygame.models.LevelData;

import java.util.ArrayList;

public class DataBase extends DBHelper {
    @SuppressLint("StaticFieldLeak")
    private static DataBase dataBase;

    private DataBase(Context context) {
        super(context, "data.db");
    }

    public static DataBase getDataBase() {
        return dataBase;
    }

    public static void init(Context context) {
        dataBase = new DataBase(context);
    }

    public ArrayList<LevelData> getLevels() {
        ArrayList<LevelData> data = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select *from levels", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            data.add(LevelData.getInstance(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    public void setStars(int level, int stars) {
        ContentValues values = new ContentValues();
        values.put("star", stars);
        mDatabase.update("levels", values, "_id=?", new String[]{level + ""});
        if (level < 9) {
            values = new ContentValues();
            values.put("exist", 1);
            mDatabase.update("levels", values, "_id=?", new String[]{(level + 1) + ""});
        }
    }

    public int getLevel() {
        int count = 0;
        Cursor cursor = mDatabase.rawQuery("select *from levels where exist =1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            count++;
            cursor.moveToNext();
        }
        cursor.close();
        return count;
    }
}
