package com.pabloor.FirstApplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final static String LIFECYCLE = "LifeCycle";
    private final static int SECOND_ACTIVITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LIFECYCLE, "onCreate()");
    }
    public void launchActivity(View view){
        Intent intent = new Intent();;
        switch(view.getId()){
            case R.id.bGetQuotations:
                intent = new Intent(MainActivity.this, QuotationsActivity.class);
                break;
            case R.id.bFavouriteQuotations:
                intent = new Intent(MainActivity.this,FavouritesActivity.class);
                break;
            case R.id.bSettings:
                intent = new Intent(MainActivity.this,SettingsActivity.class);
                break;
            case R.id.bAbout:
                intent = new Intent(MainActivity.this,AboutActivity.class);
                break;
        }

        startActivity(intent);
    }

    @Override
    protected void onStart() {
        Log.d(LIFECYCLE, "onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(LIFECYCLE, "onStop()");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(LIFECYCLE, "onRestart()");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d(LIFECYCLE, "onPause()");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(LIFECYCLE, "onResume()");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d(LIFECYCLE, "onDestroy()");
        super.onDestroy();
    }
}
