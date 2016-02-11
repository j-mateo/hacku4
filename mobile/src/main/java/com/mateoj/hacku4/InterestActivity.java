package com.mateoj.hacku4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseInstallation;
import com.parse.ParsePush;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InterestActivity extends AppCompatActivity {

    @Bind(R.id.listView)
    ListView listView;

    ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        listView = (ListView) findViewById(R.id.listView);
        String[] optionChoices = getResources().getStringArray(R.array.sort);

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, optionChoices);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(listAdapter);
        List<String> subscribed =  ParseInstallation.getCurrentInstallation().getList("channels");

        for (String myChannel : subscribed) {
            for (int i = 0; i < listAdapter.getCount(); i++) {
                if (listAdapter.getItem(i).equals(myChannel))
                    listView.setItemChecked(i, true);
            }
        }

    }

    @OnClick(R.id.fab)
    public void onButtonClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    if (checked.valueAt(i)) {
                        ParsePush.subscribeInBackground(listAdapter.getItem(position));
                    } else {
                        ParsePush.unsubscribeInBackground(listAdapter.getItem(position));
                    }

                }
            }
        }).start();

        onBackPressed();
    }
}
