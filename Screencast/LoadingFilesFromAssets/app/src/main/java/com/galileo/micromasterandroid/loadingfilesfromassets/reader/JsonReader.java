package com.galileo.micromasterandroid.loadingfilesfromassets.reader;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class JsonReader {

    public static String loadJSONFromAsset(Context context, String filePath){
        String json;
        try{
            InputStream inputStream = context.getAssets().open(filePath);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
