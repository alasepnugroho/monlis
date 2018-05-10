package com.example.toothless.monlis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import  com.example.toothless.monlis.Helper.UserSessionManager;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    private TextView mTextMessage;

    BottomNavigationView bottomNavigationView;
    UserSessionManager session;
    String status ="";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            session = new UserSessionManager(getApplicationContext());
            String id = FirebaseInstanceId.getInstance().getToken();
            Log.d("mylog", "Token: " + id);
            if(session.checkLogin())
                finish();
            if (session.checkLogin()){
                session.logoutUser();
            }else{
                HashMap<String, String> user = session.getUserDetails();
                status = user.get(UserSessionManager.KEY_USER);
                int status_user = Integer.parseInt(status);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, HomeFragment.newInstance())
                        .commit();
            }

            bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = HomeFragment.newInstance();
                    break;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    break;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
            return false;
        }

}
