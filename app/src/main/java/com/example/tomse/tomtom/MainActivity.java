package com.example.tomse.tomtom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Features;
import Model.Feed;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    List<Earthquake> earthquakeList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter recAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button emptyView;




        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        final ProgressBar loadingCircle = findViewById(R.id.loading_spinner);
        loadingCircle.setVisibility(View.VISIBLE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QuakeAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuakeAPI quakeAPI = retrofit.create(QuakeAPI.class);


            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String minMagnitude = sharedPrefs.getString(
                    getString(R.string.settings_min_magnitude_key),
                    getString(R.string.settings_min_magnitude_default));

            String startTime = sharedPrefs.getString(
                    getString(R.string.settings_start_time_key),
                    getString(R.string.settings_start_time_default));

            String limit = sharedPrefs.getString(
                    getString(R.string.settings_limit_key),
                    getString(R.string.settings_limit_default));

            String orderBy = sharedPrefs.getString(
                    getString(R.string.settings_order_by_key),
                    getString(R.string.settings_order_by_default));





            Call<Feed> call = quakeAPI.getData("geojson", "earthquake", orderBy, minMagnitude, limit, startTime);


            call.enqueue(new Callback<Feed>() {
                @Override
                public void onResponse(Call<Feed> call, Response<Feed> response) {
                    Log.d(TAG, "Kapitanuv denik" + response.toString());
                    Log.d(TAG, "Kapitanuv denik" + response.body().toString());

                    ArrayList<Features> featuresList = response.body().getFeatures();


                    for (int i = 0; i < featuresList.size(); i++) {


                        Earthquake earthquake = new Earthquake(
                                featuresList.get(i).getProperties().getMag(),
                                featuresList.get(i).getProperties().getPlace(),
                                featuresList.get(i).getProperties().getTime(),
                                featuresList.get(i).getProperties().getUrl()
                        );

                        earthquakeList.add(earthquake);

                    }

                    String[] places = new String[earthquakeList.size()];

                    for (int i = 0; i < earthquakeList.size(); i++) {
                        Log.d(TAG, "\n NaHovno " + earthquakeList.get(i).getLocation());
                        places[i] = featuresList.get(i).getProperties().getPlace();

                    }

               /* ListView listView = findViewById(R.id.list);
                ListAdapter quakeAdapter = new QuakeAdapter(getApplicationContext(), earthquakeList);
                listView.setAdapter(quakeAdapter);*/

                    loadingCircle.setVisibility(View.GONE);

                    //to see the loading circle for 5 seconds for testing purposes
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mRecyclerView = findViewById(R.id.my_recycler_view);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    recAdapter = new RecAdapter(earthquakeList);
                    mRecyclerView.setAdapter(recAdapter);

                    if (earthquakeList.isEmpty()) {
                        emptyView = findViewById(R.id.empty_view_button);
                        emptyView.setVisibility(View.VISIBLE);
                        emptyView.setText("Nedoslo k zadnemu zemetreseni");

                    }


                }


                @Override
                public void onFailure(Call<Feed> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Nefunguje", Toast.LENGTH_SHORT).show();

                    loadingCircle.setVisibility(View.GONE);
                    emptyView = findViewById(R.id.empty_view_button);
                    emptyView.setVisibility(View.VISIBLE);


                    if (t instanceof IOException) {
                        emptyView.setText("Problem s internetem RETRY");
                        emptyView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MainActivity.this.recreate();


                            }
                        });


                    } else {
                        emptyView.setText("Problem neznamo kde !!!");
                    }


                }
            });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
