package com.example.ivany.petshop.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Innovation Lab on 8/22/2017.
 */

public final class ShopContract {

    /*
    *
    * content://Authority/BasePath
    *
    * content:// = Standard Prefix indicating the data is controlled by a content provider
    * Authority = Uniquely Identifies the particular content provider
    * Base path = Used to determine the type of data (wich table) being reques
    * ID = Specifies which record is being requested
    *
    * */


    // Content Authority
    public static final String CONTENT_AUTHORITY = "com.androidbeginnerspetshop.pets";

    // Base of all URI'S
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    // Path for Dogs
    public static final String PATH_DOGS = "dogs";


    private ShopContract(){

    }

    public static final class ShopEntry implements BaseColumns{

        // Content Uri to Access the shop data in the provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_DOGS);

        // Name of the table
        public final static String TABLE_NAME = "dogs";

        // Unique ID fo the Dog
        public final static String _ID = BaseColumns._ID;

        // Name of the dog
        public final static String COLUMN_DOG_NAME = "name";

        // Weight of the dog
        public final static String COLUMN_DOG_WEIGHT = "weight";

        // All Columns
        public final static String[] All_COLUMNS = {_ID, COLUMN_DOG_NAME,COLUMN_DOG_WEIGHT};
    }


}
