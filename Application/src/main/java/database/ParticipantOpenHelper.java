package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The SQLiteOpenHelper implemented for participant info
 * Created by lovisa on 10/28/15.
 */
public class ParticipantOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = DBSchema.DB_NAME;
    private static final int VERSION = DBSchema.VERSION;

    public ParticipantOpenHelper(Context context) {
        super(context, NAME, null, VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBSchema.CREATE_TABLE_COUNTRIES);
        db.execSQL(DBSchema.CREATE_TABLE_ORGANISATIONS);
        db.execSQL(DBSchema.CREATE_TABLE_NAMES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drops the databases if the schema is changed

        db.execSQL(DBSchema.DROP_TABLE_COUNTRIES);
        db.execSQL(DBSchema.DROP_TABLE_ORGANISATIONS);
        db.execSQL(DBSchema.DROP_TABLE_NAMES);
        onCreate(db);

    }
}
