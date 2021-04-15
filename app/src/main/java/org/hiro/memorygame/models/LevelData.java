package org.hiro.memorygame.models;

import android.database.Cursor;

public class LevelData {
    private int level;
    private int starCount;
    private boolean exist;

    private LevelData(int level, int starCount, boolean exist) {

        this.level = level;
        this.starCount = starCount;
        this.exist = exist;
    }

    public static LevelData getInstance(Cursor cursor) {
        return new LevelData(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getInt(cursor.getColumnIndex("star")),
                cursor.getInt(cursor.getColumnIndex("exist")) == 1
        );
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
