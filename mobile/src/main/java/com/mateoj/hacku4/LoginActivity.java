package com.mateoj.hacku4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;



public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
        if(ParseUser.getCurrentUser() == null || !ParseUser.getCurrentUser().isAuthenticated()) {
            ParseLoginBuilder builder = new ParseLoginBuilder(this);
            builder.setAppLogo(R.mipmap.ic_launcher);
            startActivityForResult(builder.build(), 0);
        }
        else{
            startActivity(new Intent(this, MainActivity.class));
        }


        //Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        //ParseFacebookUtils.initialize(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startActivity(new Intent(this, InterestActivity.class));
    }
}
