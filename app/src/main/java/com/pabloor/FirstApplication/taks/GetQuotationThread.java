package com.pabloor.FirstApplication.taks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.pabloor.FirstApplication.Quotation;
import com.pabloor.FirstApplication.QuotationsActivity;
import com.pabloor.FirstApplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetQuotationThread extends AsyncTask<Void, Void, Quotation> {

    WeakReference<QuotationsActivity> quotationsActivityWeakReference;

    public GetQuotationThread(QuotationsActivity activity){
        this.quotationsActivityWeakReference = new WeakReference<QuotationsActivity>(activity);
    }

    @Override
    protected Quotation doInBackground(Void... voids) {
        Quotation quotation = null;
        String language = "en";
        String languagepreference = PreferenceManager.getDefaultSharedPreferences(quotationsActivityWeakReference.get().getApplicationContext()).getString(quotationsActivityWeakReference.get().getResources().getString(R.string.TitQuoatationsLanguaje),"en");
        if (languagepreference.equals(quotationsActivityWeakReference.get().getString(R.string.russiant)))
            language = "ru";
        String typeConsulta =PreferenceManager.getDefaultSharedPreferences(quotationsActivityWeakReference.get().getApplicationContext()).getString("HTTPMethod","GET");
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("api.forismatic.com");
        builder.appendPath("api");
        builder.appendPath("1.0");
        builder.appendPath("");

        if(typeConsulta.equals("GET")) {
            builder.appendQueryParameter("method", "getQuote");
            builder.appendQueryParameter("format", "json");
            builder.appendQueryParameter("lang", language);
            try{
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    Gson gsonfile =  new Gson();
                    Log.d("",gsonfile.toString());
                    quotation = gsonfile.fromJson(reader,Quotation.class);
                    reader.close();
                }
            } catch (MalformedURLException e) {
                Log.d("quotationtext","1");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("quotationtext","2");
                e.printStackTrace();
            }
        }else{
            String body = "?language="+language+"&format=json&method=getQuote";
            try {
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    Gson gsonfile = new Gson();
                    quotation = gsonfile.fromJson(reader, Quotation.class);
                    reader.close();
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("quotationtext",quotation.getQuoteText());
        return quotation;

    }

    @Override
    protected void onPreExecute() {
        quotationsActivityWeakReference.get().loadquotation();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Quotation quotation) {
        quotationsActivityWeakReference.get().getQuote(quotation);
        super.onPostExecute(quotation);
    }
}
