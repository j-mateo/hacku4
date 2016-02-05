package com.mateoj.hacku4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;



public class LoginActivity extends AppCompatActivity {

    private Button loginOrLogoutButton;
    private TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);

        Parse.initialize(this);
        if(ParseUser.getCurrentUser() == null) {
            ParseLoginBuilder builder = new ParseLoginBuilder(LoginActivity.this);
            startActivityForResult(builder.build(), 0);
        }
        else{
            //If they signed up go to intreast
            //If they logged in already move to main.
        }


        //Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        //ParseFacebookUtils.initialize(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
