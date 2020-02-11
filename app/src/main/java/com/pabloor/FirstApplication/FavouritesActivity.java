package com.pabloor.FirstApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.pabloor.FirstApplication.LayourFiller.FavouritesLayoutFiller;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        FavouritesLayoutFiller favouritesLayoutFiller = new FavouritesLayoutFiller(getMockQuotations());
        RecyclerView recyclerView = findViewById(R.id.FavouritesLayout);
        recyclerView.setAdapter(favouritesLayoutFiller);
        recyclerView.setLayoutManager(recyclerView.getLayoutManager());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
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
}
