package com.example.ivany.petshop.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ivany on 8/21/2017.
 */

public class ShopProvider extends ContentProvider {

    // Log
    private static final String LOG_TAG = ShopProvider.class.getSimpleName() ;

    // Database helper
    private ShopDbHelper mShopDbHelper;

    // UriMatcher types
    private static final int DOGS = 100;
    private static final int DOGS_ID = 101;



    // UriMatcher
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //Static initializer
    static{
        // Used to access multiple rows of the dogs table
        mUriMatcher.addURI(ShopContract.CONTENT_AUTHORITY, ShopContract.PATH_DOGS, DOGS);

        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        mUriMatcher.addURI(ShopContract.CONTENT_AUTHORITY, ShopContract.PATH_DOGS + "/#", DOGS_ID);
    }

    // Start our DbHelper
    @Override
    public boolean onCreate() {
        mShopDbHelper = new ShopDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // Open Database
        SQLiteDatabase database = mShopDbHelper.getReadableDatabase();

        // Cursor that will contain all the result data
        Cursor cursor;

        // Verify Uri
        switch (mUriMatcher.match(uri)){
            case DOGS:{
                cursor = database.query(ShopContract.ShopEntry.TABLE_NAME, ShopContract.ShopEntry.All_COLUMNS, selection, selectionArgs,
                        null, null, ShopContract.ShopEntry.COLUMN_DOG_NAME + " ASC");
                break;
            }
            case DOGS_ID:{

                selection = ShopContract.ShopEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ShopContract.ShopEntry.TABLE_NAME, projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (mUriMatcher.match(uri)){
            case DOGS:{
                return insertDog(uri, contentValues);
            }
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertDog(@NonNull Uri uri, @Nullable ContentValues contentValues){
        // Get Writable database
        SQLiteDatabase database = mShopDbHelper.getReadableDatabase();

        // Insert new Pet
        long id = database.insert(ShopContract.ShopEntry.TABLE_NAME,null, contentValues);
        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
