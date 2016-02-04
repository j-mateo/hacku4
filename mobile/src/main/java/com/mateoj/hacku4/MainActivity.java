package com.mateoj.hacku4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.support.v7.app.ActionBarActivity;

import org.joda.time.DateTime;

public class MainActivity extends ActionBarActivity implements MyRecyclerViewAdapter.MyClickListener {
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new
//                                                                          MyRecyclerViewAdapter.MyClickListener() {
//                                                                              @Override
//                                                                              public void onItemClick(int position, View v) {
//                                                                                  Log.i(LOG_TAG, " Clicked on Item " + position);
//                                                                              }
//                                                                          });
//    }

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
            Event obj = new Event(names.get(i), locations.get(i), tags, testDescription, testDate, testDate);
//            Event obj = new Event("Some Primary Text " + index, "Secondary " + index);
            results.add(i, obj);
        }
        return results;
    }

    @Override
    public void onItemClick(int position, View v) {
        Toast toast = Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG);
        toast.show();
        Intent i = new Intent(getApplicationContext(), InformationActivity.class);
        startActivity(i);
    }
}