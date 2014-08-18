package us.abbyjaneway.dev.melmapb_n;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by abbyjaneway on 8/18/14.
 */
public class BusDBOH extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "Stops";
    private static final String DB_NAME = "busroutesAndroid";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;
    public static final String ID = "rowid";
    private SQLiteDatabase busDB;
    private final Context appContext;
    private static String dbFilePath;

    public BusDBOH (Context ctx) {
        super (ctx, DB_NAME, null, DB_VERSION);
        DB_PATH = ctx.getApplicationInfo().dataDir + "/databases/";
        dbFilePath = DB_PATH + DB_NAME;
        this.appContext = ctx;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createDatabase() throws IOException {

        boolean dbExists = checkDatabase();

        System.out.println("dbExists: " + dbExists);
        if (!dbExists) {
            this.getReadableDatabase(); //creates database dir in application data dir and blank database to copy app db into

            try {
                copyDatabase();
            } catch (IOException e) {
                System.out.println("Error copying database");
                e.printStackTrace();
            }
        }
    }

    private boolean checkDatabase() {
        File dbFile = appContext.getDatabasePath(DB_NAME);
        return dbFile.exists();

    }

    private void copyDatabase() throws IOException {
        InputStream mInput = null;
        OutputStream mOutput = null;
        try {
            String[] files = appContext.getAssets().list("");
            System.out.println("files.length = " + files.length);
            mInput = appContext.getAssets().open(DB_NAME + ".sqlite");
            mOutput = new FileOutputStream(dbFilePath);
            byte[] mBuffer = new byte[1024];
            int length = 0;
            while ((length = mInput.read(mBuffer))>0) {
                mOutput.write(mBuffer, 0, length);
            }
            mOutput.flush();
            System.out.println("database copied");
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
            throw ioe;
        } finally {
            try {
                mOutput.close();
            } catch(Exception e) {}
            try {
                mInput.close();
            } catch(Exception e){}
        }
    }

    public void openDatabase() throws SQLException {
        busDB = SQLiteDatabase.openDatabase(dbFilePath, null, SQLiteDatabase.OPEN_READONLY);
        MapsActivity.setDB(busDB);
    }

    @Override
    public synchronized void close() {
        if(busDB != null) {
            busDB.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }
}
