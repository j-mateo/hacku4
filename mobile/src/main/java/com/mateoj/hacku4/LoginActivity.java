package com.mateoj.hacku4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;



public class LoginActivity extends AppCompatActivity {

    private Button loginOrLogoutButton;
    private TextView titleTextView;
    private ParseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseLoginBuilder builder = new ParseLoginBuilder(LoginActivity.this);
        startActivityForResult(builder.build(), 0);
       /* loginOrLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser.getCurrentUser() != null || currentUser.isAuthenticated()) {
                    // User clicked to log out.
                    ParseUser.logOut();
                    currentUser = null;
                    showProfileLoggedOut();
                } else {
                    // User clicked to log in.
                    ParseLoginBuilder builder = new ParseLoginBuilder(LoginActivity.this);
                    startActivityForResult(builder.build(), 0);
                }
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                showProfileLoggedIn();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showProfileLoggedOut() {
        titleTextView.setText("You must log in!");
        loginOrLogoutButton.setText("Log in");
    }

    private void showProfileLoggedIn() {
        titleTextView.setText("Welcome!!");
        loginOrLogoutButton.setText("Log Out");

    }

}