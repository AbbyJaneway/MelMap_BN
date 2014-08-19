package us.abbyjaneway.dev.melmapb_n;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abbyjaneway on 8/19/14.
 */
public class SQLOH extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "PLACES";

    public SQLOH(Context ctx) {
        super(ctx, "userplaces.db", null, 1);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(50), Lat FLOAT, Lng FLOAT);");
    }
}
