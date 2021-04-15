package org.hiro.memorygame.libs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by DilshodbeK on 25.02.2017. (Asus)
 */

public class DBHelper extends SQLiteOpenHelper {
    protected Context mContext;
    public SQLiteDatabase mDatabase;
    private String mDataBaseName;


    protected DBHelper(Context context, final String mDataBaseName, int version) {
        super(context, mDataBaseName, null, version);
        this.mDataBaseName = mDataBaseName;
        mContext = context;
        File database = mContext.getApplicationContext().getDatabasePath(mDataBaseName);
        if (!database.exists()) {
            getReadableDatabase();
            if (!is_openDatabase()) return;
        }
        openDatabase();
    }

    public DBHelper(Context context, String mDataBaseName) {
        this(context, mDataBaseName, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(mDataBaseName).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    private boolean is_openDatabase() {
        try {
            InputStream inputStream = mContext.getAssets().open(mDataBaseName);
            String outFileName = "/data/data/" + mContext.getPackageName() + "/databases/" + mDataBaseName;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeDatabase();
    }
}
