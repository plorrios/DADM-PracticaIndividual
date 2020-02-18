package com.pabloor.FirstApplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;

public class QuotationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotations);

        final TextView tvSecondActivity = findViewById(R.id.tvSecondActivity);
        tvSecondActivity.setText(getString(R.string.noquotationtext,"Nameless One"));
    }
    public void loadquotation(View view){
        final TextView tvSecondActivity = findViewById(R.id.tvSecondActivity);
        final TextView tvSecondActivity2 = findViewById(R.id.tvSecondActivity2);
        tvSecondActivity.setText(getString(R.string.samplequotations));
        tvSecondActivity2.setText(getString(R.string.sampleauthor));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.AddId:
                return true;
            case R.id.RefreshId:
                final TextView tvSecondActivity = findViewById(R.id.tvSecondActivity);
                final TextView tvSecondActivity2 = findViewById(R.id.tvSecondActivity2);
                tvSecondActivity.setText(getString(R.string.samplequotations));
                tvSecondActivity2.setText(getString(R.string.sampleauthor));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
