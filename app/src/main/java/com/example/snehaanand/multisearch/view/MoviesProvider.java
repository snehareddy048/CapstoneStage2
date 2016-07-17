package com.example.snehaanand.multisearch.view;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.example.snehaanand.multisearch.utils.Utils;

import java.util.HashMap;

/**
 * Created by snehaanandyeluguri on 10/31/15.
 */
public class MoviesProvider  extends ContentProvider {
    static final String PROVIDER_NAME = Utils.CONTENT_BASE_URL;
    static final String URL = "content://" + PROVIDER_NAME + "/"+Utils.MOVIES_TEXT;
    static final Uri CONTENT_URI = Uri.parse(URL);


    static final String _ID = "id";
    static final String SEARCH_RESULT_TYPE = "type";
    private static HashMap<String, String> MOVIES_PROJECTION_MAP;

    static final int SEARCH_RESULT = 1;

//    static final int SEARCH_RESULT_ID = 2;
//    static final int SEARCH_RESULT_TYPE = 3;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, Utils.MOVIES_TEXT, SEARCH_RESULT);
//        uriMatcher.addURI(PROVIDER_NAME, Utils.MOVIES_TEXT+"/#", SEARCH_RESULT_TYPE);
    }

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "MovieDB";
    static final String MOVIES_TABLE_NAME = Utils.MOVIES_TEXT;
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + MOVIES_TABLE_NAME +
                    " ("+_ID+" INTEGER NOT NULL UNIQUE, "+
    SEARCH_RESULT_TYPE+" TEXT NOT NULL);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MOVIES_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new movie record
         */
        long rowID = db.insert(MOVIES_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MOVIES_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case SEARCH_RESULT:
                queryBuilder.setProjectionMap(MOVIES_PROJECTION_MAP);
                break;

//            case SEARCH_RESULT_ID:
//                queryBuilder.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
//                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on movie names
             */
            sortOrder = _ID;
        }
        Cursor c = queryBuilder.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case SEARCH_RESULT:
                count = db.delete(MOVIES_TABLE_NAME, selection, selectionArgs);
                break;

//            case SEARCH_RESULT_ID:
//                String id = uri.getPathSegments().get(1);
//                count = db.delete(MOVIES_TABLE_NAME, _ID +  " = " + id +
//                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
//                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case SEARCH_RESULT:
                count = db.update(MOVIES_TABLE_NAME, values, selection, selectionArgs);
                break;

//            case SEARCH_RESULT_ID:
//                count = db.update(MOVIES_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) +
//                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
//                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all movie records
             */
            case SEARCH_RESULT:
                return "vnd.android.cursor.dir/vnd.example."+Utils.MOVIES_TEXT;

            /**
             * Get a particular movie
             */
//            case SEARCH_RESULT_ID:
//                return "vnd.android.cursor.item/vnd.example."+Utils.MOVIES_TEXT;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
