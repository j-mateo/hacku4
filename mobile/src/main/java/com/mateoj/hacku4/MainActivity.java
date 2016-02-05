package com.mateoj.hacku4;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends LocationActivity implements MyRecyclerViewAdapter.MyClickListener {
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";
    private boolean isQueryInProgress = false;
    private boolean needsData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        needsData = true;
    }

    private ArrayList<Event> getDataSet() {
        ArrayList results = new ArrayList<Event>();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> locations = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();

        names.add("Kayaking"); names.add("Studying"); names.add("Sucking helium from balloons"); names.add("Party Fun Time!!!"); names.add("Panic over CSCI 301 project");
        locations.add("Matoaka"); locations.add("Swem"); locations.add("Sadler Center"); locations.add("Sunken Garden"); locations.add("McGlothlin Hall");
        tags.add("fun"); tags.add("boring");

        String testDescription = "It's exactly what it sounds like.";

//        Date testDate = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat ("E MM-DD-YYYY 'at' hh:mm:ss a zzz");
        // System.out.println("Current Date: " + ft.format(dNow));

        DateTime testDate = new DateTime();

        for (int i = 0; i < 5; i++) {
            Event obj = new Event();
//            Event obj = new Event("Some Primary Text " + index, "Secondary " + index);
            results.add(i, obj);
        }
        return results;
    }

    @Override
    public void onItemClick(int position, View v) {
        Toast toast = Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG);
        toast.show();
        Intent i = new Intent(getApplicationContext(), DetailActivity.class);
        startActivity(i);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (isQueryInProgress || !needsData)
            return;

        ParseQuery<Building> buildingQuery = ParseQuery.getQuery(Building.class);

        buildingQuery.whereNear(Building.KEY_LOCATION, new ParseGeoPoint(location.getLatitude(),
                location.getLongitude()));
        isQueryInProgress = true;
        buildingQuery.findInBackground(new FindCallback<Building>() {
            @Override
            public void done(List<Building> objects, ParseException e) {
                mAdapter.clear();
                needsData = false;
                isQueryInProgress = false;
                for (Building building : objects) {
                    ParseQuery<Event> eventQuery = ParseQuery.getQuery(Event.class);
                    eventQuery.whereEqualTo("Location", building);
                    eventQuery.findInBackground(new FindCallback<Event>() {
                        @Override
                        public void done(List<Event> objects, ParseException e) {
                            Log.d("Event", objects.toString());
                            mAdapter.addAll(objects);
                        }
                    });
                }
            }
        });
    }
}