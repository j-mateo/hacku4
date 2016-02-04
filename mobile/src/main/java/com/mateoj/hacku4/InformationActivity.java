package com.mateoj.hacku4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toast toast = Toast.makeText(getApplicationContext(), "We made it fam", Toast.LENGTH_LONG);
        toast.show();
    }

}
