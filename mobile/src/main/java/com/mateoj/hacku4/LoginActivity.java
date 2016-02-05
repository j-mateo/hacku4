package com.mateoj.hacku4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;



public class LoginActivity extends AppCompatActivity {


    private Button loginOrLogoutButton;
    private TextView titleTextView;
    private ParseUser currentUser;

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
    }

    private void showProfileLoggedOut() {
        titleTextView.setText("You must log in!");
        loginOrLogoutButton.setText("Log in");

    }

    private void showProfileLoggedIn() {
        titleTextView.setText("Welcome!!");
        loginOrLogoutButton.setText("Log Out");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startActivity(new Intent(this, MainActivity.class));
    }

}