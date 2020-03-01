package com.pabloor.FirstApplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pabloor.FirstApplication.databases.BD_Access;
import com.pabloor.FirstApplication.databases.QuotationsDatabase;
import com.pabloor.FirstApplication.databases.UserDao;
import com.pabloor.FirstApplication.taks.GetQuotationThread;

public class QuotationsActivity extends AppCompatActivity {
    public MenuItem item2;
    public boolean enable=false;
    Menu actionmenu;
    BD_Access database;
    QuotationsDatabase database2;
    public String accesMethod;
    TextView tvSecondActivity;
    TextView tvSecondActivity2;
    ProgressBar progressBar = null;
    Handler handler;
    GetQuotationThread task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotations);

        handler = new Handler();

        tvSecondActivity = findViewById(R.id.tvSecondActivity);
        tvSecondActivity2 = findViewById(R.id.tvSecondActivity2);

        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

        accesMethod = preferences.getString("DatabaseType", getString(R.string.SQLitet));
        if (accesMethod.equals(getString(R.string.SQLitet))){
            database = BD_Access.getInstance(getApplicationContext());
        } else{
            database2 = QuotationsDatabase.getInstance(getApplicationContext());
        }
        if(savedInstanceState==null) {
            final TextView tvSecondActivity = findViewById(R.id.tvSecondActivity);
            tvSecondActivity.setText(getString(R.string.noquotationtext, preferences.getString("UserNameText", "Nameless One")));
        }else{

            tvSecondActivity.setText(savedInstanceState.getString("QuoteText"));
            tvSecondActivity2.setText(savedInstanceState.getString("AuthorText"));

            enable = savedInstanceState.getBoolean("AddIsVisible");
        }

        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionmenu = menu;
        getMenuInflater().inflate(R.menu.quotations_actionbar,menu);
        if (menu != null){

            item2 = menu.findItem(R.id.AddId);
            item2.setVisible(false);

        }
        return super.onCreateOptionsMenu(menu);
    }

    public void loadquotation(){
        progressBar.setVisibility(View.VISIBLE);
        actionmenu.findItem(R.id.AddId).setVisible(false);
        actionmenu.findItem(R.id.RefreshId).setVisible(false);

    }

    public void getQuote(Quotation quotation){

        tvSecondActivity.setText(quotation.getQuoteText());
        tvSecondActivity2.setText(quotation.getQuoteAuthor());
        progressBar.setVisibility(View.INVISIBLE);
        actionmenu.findItem(R.id.AddId).setVisible(true);
        actionmenu.findItem(R.id.RefreshId).setVisible(true);
        newQuote();
    }

    public boolean hasConectivity(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return ((networkInfo != null) && (networkInfo.isConnected()));
    }

    public void newQuote(){

        final Boolean[] quotationinTable = new Boolean[1];
        Thread t =  new  Thread(new Runnable() {
            @Override
            public void run() {

                if(accesMethod.equals("SQLiteOpenHelper")){
                    Quotation quotation = new Quotation();
                    quotation.setQuoteAuthor(tvSecondActivity2.getText().toString());
                    quotation.setQuoteText(tvSecondActivity.getText().toString());
                    quotationinTable[0] = database.getInstance(getApplicationContext()).isQuotationInDatabase(quotation);
                }else{
                    quotationinTable[0] = database2.getInstance(getApplicationContext()).userDao().findByName(tvSecondActivity.getText().toString()) != null;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(quotationinTable[0]){
                            actionmenu.findItem(R.id.AddId).setVisible(false);
                        }else{
                            actionmenu.findItem(R.id.AddId).setVisible(true);
                        }
                    }
                });
            }
        });
        t.start();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final String quotationtext = getString(R.string.samplequotations);
        final String quotationauthor = getString(R.string.sampleauthor);
        final Quotation quotation = new Quotation();
        quotation.setQuoteText(quotationtext);
        quotation.setQuoteAuthor(quotationauthor);
        switch (item.getItemId()){
            case R.id.AddId:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (accesMethod.equals(getString(R.string.SQLitet))){
                            database.AddQuotation(quotation);
                        }
                        else{
                            database2.userDao().insert(quotation);
                        }
                        // Include here the code to access the database
                    }
                }).start();

                item2.setVisible(false);
                return true;
            case R.id.RefreshId:
                task = new GetQuotationThread(this);

                if(hasConectivity()) {
                    task.execute();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        if (task != null) {

            if (task.getStatus() == AsyncTask.Status.RUNNING) {
                task.cancel(true);
            }
        }
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("QuoteText",tvSecondActivity.getText().toString());
        outState.putString("AuthorText",tvSecondActivity2.getText().toString());
        outState.putBoolean("AddIsVisible",item2.isVisible());
    }
}
