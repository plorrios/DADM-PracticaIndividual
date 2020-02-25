package com.pabloor.FirstApplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pabloor.FirstApplication.LayourFiller.FavouritesLayoutFiller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    FavouritesLayoutFiller favouritesLayoutFiller;
    public MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

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

        favouritesLayoutFiller = new FavouritesLayoutFiller(getMockQuotations(), new FavouritesLayoutFiller.InterfaceClick() {
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
    }

    public void info(View view){
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + "Albert Einstein"));
        startActivity(intent);
    }

    public ArrayList<Quotation> getMockQuotations(){

        ArrayList<Quotation> arrayList = new ArrayList<Quotation>();
        for (int i=0; i<10; i++){

            Quotation quotation = new Quotation();
            quotation.setQuoteAuthor("author" + i);
            quotation.setQuoteText("quotation" + i);
            arrayList.add(quotation);
        }
        return arrayList;
    }

    public void CreateAlertMenuRemoval(final FavouritesLayoutFiller internalfavouritesLayoutFiller,final int removalItem){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.AlertRemovalMessage);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                favouritesLayoutFiller.RemoveQuotation(removalItem);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        CreateAlertMenuRemovalAll(favouritesLayoutFiller);
        return super.onOptionsItemSelected(item);
    }
}
