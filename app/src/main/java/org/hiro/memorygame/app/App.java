package org.hiro.memorygame.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import org.hiro.memorygame.database.DataBase;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataBase.init(this);
        DataBase db = DataBase.getDataBase();
        SQLiteDatabase s = db.mDatabase;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DataBase.getDataBase().closeDatabase();
    }
}
