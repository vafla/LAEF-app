package database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import database.ParticipantContract.Countries;
import database.ParticipantContract.Names;
import database.ParticipantContract.Organisation;

/**
 * Created by lovisa on 10/27/15.
 * <p/>
 * Content Provider for the Participant Info
 */
public class ParticipantProvider extends ContentProvider {
    private static final int COUNTRY_LIST = 1;
    private static final int COUNTRY_ID = 2;
    private static final int ORGANISATION_LIST = 5;
    private static final int ORGANISATION_ID = 6;
    private static final int NAMES_LIST = 10;
    private static final int NAMES_ID = 11;

    private static final UriMatcher URI_MATCHER;

    private ParticipantOpenHelper participantOpenHelper = null;


    // prepare the UriMatcher
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ParticipantContract.AUTHORITY, "countries", COUNTRY_LIST);
        URI_MATCHER.addURI(ParticipantContract.AUTHORITY, "countries/#", COUNTRY_ID);
        URI_MATCHER.addURI(ParticipantContract.AUTHORITY, "organisation", ORGANISATION_LIST);
        URI_MATCHER.addURI(ParticipantContract.AUTHORITY, "organisation/#", ORGANISATION_ID);
        URI_MATCHER.addURI(ParticipantContract.AUTHORITY, "names", NAMES_LIST);
        URI_MATCHER.addURI(ParticipantContract.AUTHORITY, "names/#", NAMES_ID);
    }

    @Override
    public boolean onCreate() {
        participantOpenHelper = new ParticipantOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = participantOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (URI_MATCHER.match(uri)) {
            case COUNTRY_LIST:
                builder.setTables(DBSchema.TABEL_COUNTRIES);
                sortOrder = Countries.SORT_ORDER_DEFAULT;
                break;
            case COUNTRY_ID:
                builder.setTables(DBSchema.TABEL_COUNTRIES);
                builder.appendWhere(Countries._ID + " = " + uri.getLastPathSegment());
                break;
            case ORGANISATION_LIST:
                builder.setTables(DBSchema.TABEL_ORGANISATIONS);
                sortOrder = Organisation.SORT_ORDER_DEFAULT;
                break;
            case ORGANISATION_ID:
                builder.setTables(DBSchema.TABEL_ORGANISATIONS);
                builder.appendWhere(Organisation._ID + " = " + uri.getLastPathSegment());
                break;
            case NAMES_LIST:
                builder.setTables(DBSchema.TABEL_NAMES);
                sortOrder = Names.SORT_ORDER_DEFAULT;
                break;
            case NAMES_ID:
                builder.setTables(DBSchema.TABEL_NAMES);
                builder.appendWhere(Names._ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsported URI: " + uri);
        }

        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case COUNTRY_LIST:
                return Countries.CONTENT_TYPE;
            case COUNTRY_ID:
                return Countries.CONTENT_ITEM_TYPE;
            case ORGANISATION_LIST:
                return Organisation.CONTENT_TYPE;
            case ORGANISATION_ID:
                return Organisation.CONTENT_ITEM_TYPE;
            case NAMES_LIST:
                return Names.CONTENT_TYPE;
            case NAMES_ID:
                return Names.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = participantOpenHelper.getWritableDatabase();
        long id = 0L;
        switch (URI_MATCHER.match(uri)) {
            case COUNTRY_LIST:
                id = db.insertWithOnConflict(DBSchema.TABEL_COUNTRIES, null,
                        values, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case ORGANISATION_LIST:
                id = db.insertWithOnConflict(DBSchema.TABEL_ORGANISATIONS, null,
                        values, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case NAMES_LIST:
                id = db.insertWithOnConflict(DBSchema.TABEL_NAMES, null,
                        values, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for insertion " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
