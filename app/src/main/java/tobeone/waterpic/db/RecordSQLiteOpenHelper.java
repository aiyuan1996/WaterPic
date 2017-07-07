package tobeone.waterpic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 王特 on 2017/7/7.
 */

public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {

    private static String name = "records.db";
    private static Integer version = 1;

    public RecordSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table project_records(id integer primary key autoincrement,name varchar(200))");
        db.execSQL("create table company_records(id integer primary key autoincrement,name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
