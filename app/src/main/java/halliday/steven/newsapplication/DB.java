package halliday.steven.newsapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.time.LocalDateTime;
import java.util.UUID;

public final class DB {
    private DB(){}
    public static class db implements BaseColumns{
        public static final String columnTime = "Time";
        public static final String id = "idtest";
        public static final String tableName = "Patients";
        public static final String columnName = "Name";
        public static final String columnScore = "Score";
    }
    private static final String createEntries = "CREATE TABLE " + db.tableName + " (" + db.id + " INTEGER PRIMARY KEY, " +
            db.columnName  + " TEXT," + db.columnScore + " TEXT," + db.columnTime + " TEXT)";
    private static final String deleteEntries = "DROP TABLE IF EXISTS " + db.tableName;

    public static class dbhelp extends SQLiteOpenHelper {
        public static final int dbVersion = 1;
        public static final String dbName = "Patients.db";

        public dbhelp (Context context){
            super(context, dbName, null, dbVersion);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL(createEntries);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL(deleteEntries);
            onCreate(db);
        }
    }
}
