package com.pabloor.FirstApplication.taks;

import android.os.AsyncTask;
import android.util.Log;

import com.pabloor.FirstApplication.FavouritesActivity;
import com.pabloor.FirstApplication.Quotation;
import com.pabloor.FirstApplication.databases.BD_Access;
import com.pabloor.FirstApplication.databases.QuotationsDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class DBThread extends AsyncTask<Boolean, Void, List<Quotation>> {

    private final WeakReference<FavouritesActivity> favouritesActivityWeakReference;
    private List<Quotation>quotations;

    public DBThread(FavouritesActivity activity){
        this.favouritesActivityWeakReference = new WeakReference<FavouritesActivity>(activity);
    }

    public WeakReference<FavouritesActivity> GetWeakReference(){
        if(favouritesActivityWeakReference!=null)
        { return favouritesActivityWeakReference; }
        else{ return null;}
    }

    public FavouritesActivity GetActivity()
    {
        if(favouritesActivityWeakReference!=null)
        { return favouritesActivityWeakReference.get(); }
        else{ return null;}
    }

    @Override
    protected List<Quotation> doInBackground(Boolean... booleans) {
        if (booleans[0]){
            quotations = BD_Access.getInstance(GetActivity()).QuotationsList();}
        else{
            quotations = QuotationsDatabase.getInstance(GetActivity()).userDao().getAll();
        }
        return quotations;
    }

    @Override
    protected void onPostExecute(List<Quotation> localQuotations) {
        favouritesActivityWeakReference.get().AddQuotations(localQuotations);
        super.onPostExecute(localQuotations);
    }
}
