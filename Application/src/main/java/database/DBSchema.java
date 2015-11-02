package database;

import android.provider.BaseColumns;

/**
 * Created by lovisa on 10/28/15.
 * Database Schema for the the particiapant info
 */
interface DBSchema {

    String DB_NAME = "participantinfo.db";
    // If the Schema is changed the version needs to be updated for the changes to take effect
    int VERSION = 8;

    String TABEL_COUNTRIES = "countries";
    String TABEL_ORGANISATIONS = "organisations";
    String TABEL_NAMES = "names";

    String COL_ID = BaseColumns._ID;
    String COL_COUNTRY = "country";
    String COL_ORGANISATION = "organisation";
    String COL_NAME = "name";
    String COL_DESCRIPTION = "description";

    String CREATE_TABLE_COUNTRIES =
            "CREATE TABLE " + TABEL_COUNTRIES + " ( "
                    + COL_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, \n"
                    + COL_COUNTRY + " TEXT NOT NULL UNIQUE \n )";

    String CREATE_TABLE_ORGANISATIONS =
            "CREATE TABLE " + TABEL_ORGANISATIONS + " ( "
                    + COL_ID + "   INTEGER  PRIMARY KEY AUTOINCREMENT, \n"
                    + COL_ORGANISATION + " TEXT NOT NULL UNIQUE, \n "
                    + COL_COUNTRY + "_id  INTEGER NOT NULL \n)";

    String CREATE_TABLE_NAMES =
            "CREATE TABLE " + TABEL_NAMES + " ( "
                    + COL_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, \n"
                    + COL_NAME + " TEXT NOT NULL, \n"
                    + COL_DESCRIPTION + " TEXT, \n"
                    + COL_ORGANISATION + "_id INTEGER NOT NULL, \n" +
                    " CONSTRAINT unq UNIQUE ( " + COL_NAME + " , " + COL_ORGANISATION + "_id ))";


    String DROP_TABLE_COUNTRIES = "DROP TABLE IF EXISTS " + TABEL_COUNTRIES;
    String DROP_TABLE_ORGANISATIONS = "DROP TABLE IF EXISTS " + TABEL_ORGANISATIONS;
    String DROP_TABLE_NAMES = "DROP TABLE IF EXISTS " + TABEL_NAMES;
}
