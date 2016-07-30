package kr.ac.cbnu.fullstack.simpledictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JiyoungPark on 2016. 5. 3..
 */
public class DBManager extends SQLiteOpenHelper {

    static DBManager g_this;

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        g_this = this;
    }

    public static DBManager getInstance() {
        if(g_this == null)
            Log.e("DBManager", "Instance NULL");
        return g_this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS search_data(" +
                "word TEXT PRIMARY KEY, " +
                "mean TEXT, " +
                "count TEXT, " +
                "time TEXT);";
        db.execSQL(sql);

        Log.e("DBManager", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void write(String _query) {
        Log.e("DBManager", _query);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void select(String query, OnSelect cb) {
        SQLiteDatabase db = getReadableDatabase();
        Log.e("DBManager SELECT query", query);
        try {
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                cb.onSelect(cursor);
            }
            cb.onComplete(cursor.getCount());
        }catch (Exception e){
            cb.onErrorHandler(e);
        }
    }

    public static interface OnSelect {
        public void onSelect(Cursor cursor);
        public void onComplete(int cnt);
        public void onErrorHandler(Exception e);
    }
}
