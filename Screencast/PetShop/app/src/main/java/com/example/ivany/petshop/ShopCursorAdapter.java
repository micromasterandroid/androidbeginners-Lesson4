package com.example.ivany.petshop;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ivany.petshop.data.ShopContract;

/**
 * Created by Innovation Lab on 8/22/2017.
 */

public class ShopCursorAdapter extends CursorAdapter {

    private String dogName, dogWeight;
    private TextView tvDogName, tvDogWeight;

    public ShopCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // Setup layout
        return LayoutInflater.from(context).inflate(
                R.layout.dog_list_item,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get Cursor Result
        dogName = cursor.getString(cursor.getColumnIndex(ShopContract.ShopEntry.COLUMN_DOG_NAME));
        dogWeight = cursor.getString(cursor.getColumnIndex(ShopContract.ShopEntry.COLUMN_DOG_WEIGHT));
        // Find views
        tvDogName = (TextView) view.findViewById(R.id.tvDogName);
        tvDogWeight = (TextView) view.findViewById(R.id.tvDogWeight);
        // Set text in views
        tvDogName.setText(dogName);
        tvDogWeight.setText(dogWeight);
    }
}
