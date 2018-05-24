package com.galileo.micromasterandroid.loadingfilesfromassets;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.galileo.micromasterandroid.loadingfilesfromassets.adapters.RecyclerViewAdapter;
import com.galileo.micromasterandroid.loadingfilesfromassets.model.Pokemon;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.galileo.micromasterandroid.loadingfilesfromassets.reader.JsonReader.loadJSONFromAsset;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Pokemon> pokemonList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(this,pokemonList);
        recyclerView.setAdapter(recyclerViewAdapter);
        Log.d("TAG","start");
        new ReadPokemonDataOnBackGround(this).execute();

    }
    private class ReadPokemonDataOnBackGround extends AsyncTask<Void, Integer, ArrayList<Pokemon>>{

        private ProgressDialog dialog;

        public ReadPokemonDataOnBackGround(Context context){
            this.dialog = new ProgressDialog(context);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Reading Data...");
            dialog.show();
        }

        @Override
        protected ArrayList<Pokemon> doInBackground(Void... voids) {
            String pokemonJson = loadJSONFromAsset(getApplicationContext(),"pokemonlist.json");

            Gson gson = new Gson();
            Pokemon[] pokemon = gson.fromJson(pokemonJson, Pokemon[].class);
            return new ArrayList<Pokemon>(Arrays.asList(pokemon));
        }


        @Override
        protected void onPostExecute(ArrayList<Pokemon> pokemon) {
            super.onPostExecute(pokemon);
            pokemonList.clear();
            Log.d("TAG",pokemon.get(0).toString());
            pokemonList.addAll(pokemon);

            recyclerViewAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
