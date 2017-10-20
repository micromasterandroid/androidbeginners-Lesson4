package com.example.ivany.petshop;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ivany.petshop.data.ShopContract;

public class MainActivity extends AppCompatActivity implements  android.app.LoaderManager.LoaderCallbacks<Cursor>{

    // Cursor Adapter for the data
    private CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating cursor Adapter
        cursorAdapter = new ShopCursorAdapter(this,null,0);

        // Getting the list layout and applying the cursor Adapter
        ListView list = (ListView)findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init loader
        getLoaderManager().initLoader(0,null,this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating a dialog to enter Dog information
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View dialogView = li.inflate(R.layout.dialog_get_dog_info, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(dialogView);
                // Get dialog views objects
                final EditText etDogName = (EditText) dialogView.findViewById(R.id.etDogName);
                final EditText etDogWeight = (EditText) dialogView.findViewById(R.id.etDogWeight);

                // Add functionality
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Get user input and set it to result

                                insertDog(etDogName.getText().toString(), etDogWeight.getText().toString());
                                restartLoader();

                            }
                        }).create()
                        .show();
            }
        });
    }

    private void insertDog(String name, String weight){
        // Place values
        ContentValues values = new ContentValues();
        values.put(ShopContract.ShopEntry.COLUMN_DOG_NAME, name);
        values.put(ShopContract.ShopEntry.COLUMN_DOG_WEIGHT, weight);
        // // Insert a new row for Dog input into the provider using the ContentResolver.
        getContentResolver().insert(ShopContract.ShopEntry.CONTENT_URI,values);
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // connection to contactProvider
        return new CursorLoader(this, ShopContract.ShopEntry.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // results from the query  of the contact provider
        // set results into adapter
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
