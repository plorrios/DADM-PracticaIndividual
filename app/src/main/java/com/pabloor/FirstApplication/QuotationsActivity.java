package com.pabloor.FirstApplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.pabloor.FirstApplication.databases.BD_Access;
import com.pabloor.FirstApplication.databases.QuotationsDatabase;
import com.pabloor.FirstApplication.databases.UserDao;

public class QuotationsActivity extends AppCompatActivity {

    public int numcitas = 0;
    public MenuItem item2;
    public boolean enable=false;
    BD_Access database;
    QuotationsDatabase database2;
    public String accesMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotations);
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

        accesMethod = preferences.getString("DatabaseType", getString(R.string.SQLitet));
        if (accesMethod == getString(R.string.SQLitet)){ database = BD_Access.getInstance(this); }
        else{ database2 = QuotationsDatabase.getInstance(this);}

        if(savedInstanceState==null) {
            final TextView tvSecondActivity = findViewById(R.id.tvSecondActivity);
            tvSecondActivity.setText(getString(R.string.noquotationtext, preferences.getString("UserNameText", "Nameless One")));
        }else{
            final TextView tvSecondActivity = findViewById(R.id.tvSecondActivity);
            final TextView tvSecondActivity2 = findViewById(R.id.tvSecondActivity2);

            numcitas = savedInstanceState.getInt("NumCitas",numcitas);

            tvSecondActivity.setText(savedInstanceState.getString("QuoteText"));
            tvSecondActivity2.setText(savedInstanceState.getString("AuthorText"));

            enable = savedInstanceState.getBoolean("AddIsVisible");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quotations_actionbar,menu);
        if (menu != null){

            item2 = menu.findItem(R.id.AddId);
            item2.setVisible(enable);

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String quotationtext = getString(R.string.samplequotations);
        String quotationauthor = getString(R.string.sampleauthor);
        Quotation quotation = new Quotation();
        quotation.setQuoteText(quotationtext);
        quotation.setQuoteAuthor(quotationauthor);
        switch (item.getItemId()){
            case R.id.AddId:
                if (accesMethod==getString(R.string.SQLitet))
                { database.AddQuotation(quotation); }
                else{ database2.userDao().insert(quotation); }

                item2.setVisible(false);
                return true;
            case R.id.RefreshId:
                final TextView tvSecondActivity = findViewById(R.id.tvSecondActivity);
                final TextView tvSecondActivity2 = findViewById(R.id.tvSecondActivity2);
                tvSecondActivity.setText(quotationtext);
                tvSecondActivity2.setText(quotationauthor);

                if (accesMethod==getString(R.string.SQLitet))
                    {if (database.isQuotationInDatabase(quotation)){
                        item2.setVisible(false);
                    } else{
                        item2.setVisible(true);
                    }
                }
                else{
                    if ( database2.userDao().findByName(quotationtext)!=null ){
                        item2.setVisible(false);
                    } else{
                        item2.setVisible(true);
                    }
                }


                numcitas++;
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("QuoteText",getText(R.string.samplequotations).toString());
        outState.putString("AuthorText",getText(R.string.sampleauthor).toString());
        outState.putInt("NumCitas",numcitas);
        outState.putBoolean("AddIsVisible",item2.isVisible());
    }
}
