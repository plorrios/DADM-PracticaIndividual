package com.pabloor.FirstApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name = findViewById(R.id.editText1);
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        name.setText(preferences.getString("Name", ""));
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        if (name.getText() != null){
            editor.putString("Name",name.getText().toString());
        } else {
            editor.remove("Name");
        }
        editor.apply();
        super.onPause();
    }
}
