package com.pabloor.FirstApplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pabloor.FirstApplication.LayourFiller.FavouritesLayoutFiller;
import com.pabloor.FirstApplication.databases.BD_Access;
import com.pabloor.FirstApplication.databases.QuotationsDatabase;
import com.pabloor.FirstApplication.taks.DBThread;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {
    FavouritesLayoutFiller favouritesLayoutFiller;
    public MenuItem item;
    BD_Access database;
    QuotationsDatabase database2;
    public String accesMethod;
    List<Quotation> quotations;
    DBThread databaseAcces;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        SharedPreferences preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);

        handler = new Handler();
        /*favouritesLayoutFiller.InterfaceClick interfaceClick = new FavouritesLayoutFiller.InterfaceClick() {
            @Override
            public void OnInterfaceClick(int position) {
                Intent intent = new Intent();
                if (FavouritesLayoutFiller.GetQuotation(position).getQuoteAuthor()==null){

                }
                intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + URLEncoder.encode(FavouritesLayoutFiller.GetQuotation(position).getQuoteAuthor(),"UTF-8")));
                startActivity(intent);
            }
        };*///para declararlo aparte y luego pasarlo como parametro en vez de declararlo directamente en el parametro


        databaseAcces = new DBThread(this);
        accesMethod = preferences.getString("DatabaseType", getString(R.string.SQLitet));
        quotations = new ArrayList<Quotation>();
        if (accesMethod.equals(getString(R.string.SQLitet))){
            database = BD_Access.getInstance(getApplicationContext());
            databaseAcces.execute(true);
        }else{
            database2 = QuotationsDatabase.getInstance(getApplicationContext());
            databaseAcces.execute(false);
        }

    }

    public void AddQuotations(List<Quotation> localQuotations)
    {
        quotations = localQuotations;
        favouritesLayoutFiller = new FavouritesLayoutFiller(localQuotations, new FavouritesLayoutFiller.InterfaceClick() {
            @Override
            public void OnInterfaceClick(int position) {
                if (favouritesLayoutFiller.GetQuotation(position).getQuoteAuthor() == null) {
                    Toast.makeText(FavouritesActivity.this, "Author not found", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();
                try {
                    intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + URLEncoder.encode(favouritesLayoutFiller.GetQuotation(position).getQuoteAuthor(), "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        }, new FavouritesLayoutFiller.InterfaceLongClick() {
            @Override
            public void OnInterfaceLongClick(int position) {
                CreateAlertMenuRemoval(favouritesLayoutFiller, position);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.FavouritesLayout);
        recyclerView.setAdapter(favouritesLayoutFiller);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        if(item!=null) {
            if (quotations.isEmpty()) {
                item.setVisible(false);
            } else {
                item.setVisible(true);
            }
        }
    }

    public void CreateAlertMenuRemoval(final FavouritesLayoutFiller internalfavouritesLayoutFiller,final int removalItem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.AlertRemovalMessage);
        final Quotation removalQuotation = favouritesLayoutFiller.GetQuotation(removalItem);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Include here the code to access the database
                        if (accesMethod.equals(getString(R.string.SQLitet))) {
                            database.RemoveQuotation(removalQuotation);
                        }
                        else{ database2.userDao().delete(removalQuotation);}
                     handler.post(new Runnable() {
                         @Override
                         public void run() {
                             favouritesLayoutFiller.RemoveQuotation(removalItem);
                             if (favouritesLayoutFiller.getItemCount()==0){item.setVisible(false);}
                         }
                     });
                    }
                }).start();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("QuotesSize3",Integer.toString(favouritesLayoutFiller.getItemCount()));
            }
        });
        if (favouritesLayoutFiller.getItemCount()==0){ item.setVisible(false); }
        builder.create().show();

    }

    public void CreateAlertMenuRemovalAll(final FavouritesLayoutFiller internalfavouritesLayoutFiller){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.AlertRemovalMessageAll);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (accesMethod.equals(getString(R.string.SQLitet))){
                            database.ClearDatabase();
                        } else{
                            database2.userDao().DeleteAll();
                        }
                    }
                }).start();
                internalfavouritesLayoutFiller.RemoveAllQuotations();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        item.setVisible(false);
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourites_actionbar,menu);
        if (menu != null){

            item = menu.findItem(R.id.ClearId);
        }
        if (quotations.isEmpty()){item.setVisible(false);}
        else {item.setVisible(true);}
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ClearId)
        CreateAlertMenuRemovalAll(favouritesLayoutFiller);
        return super.onOptionsItemSelected(item);
    }
}
