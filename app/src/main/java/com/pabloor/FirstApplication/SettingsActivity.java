package com.pabloor.FirstApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
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
        getSupportFragmentManager().beginTransaction().replace(R.id.ActivitySettings,new SettingsFragment()).commit();
        name = findViewById(R.id.UserNameText);
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

    public static class SettingsFragment extends PreferenceFragmentCompat{

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences_settings,rootKey);
        }
    }
}
