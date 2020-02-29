package com.pabloor.FirstApplication.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pabloor.FirstApplication.Quotation;

import java.util.ArrayList;
import java.util.List;

public class BD_Access extends SQLiteOpenHelper {

    private static BD_Access BDAccess = null;

    private BD_Access(Context context) {
        super(context, "quotation_database", null, 1);
    }

    public static synchronized BD_Access getInstance(Context context) {
        if (BDAccess == null) {
            BDAccess = new BD_Access(context);
        }
        return BDAccess;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE QuotationContract(_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " Text TEXT NOT NULL, Author TEXT,Quotation UNIQUE);");
    }

    public List<Quotation> QuotationsList(){
        List<Quotation> ans = new ArrayList<Quotation>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("QuotationContract", new String[]{"Text","Author"},null, null, null, null, null);
        while(cursor.moveToNext()){
            Quotation quotation = new Quotation();
            quotation.setQuoteText(cursor.getString(0));
            quotation.setQuoteAuthor(cursor.getString(1));

            ans.add(quotation);
        }
        database.close();
        cursor.close();
        return ans;
    }

    public boolean isQuotationInDatabase(Quotation quotation){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("QuotationContract",null, "Text=?", new String[]{quotation.getQuoteText()},null, null, null, null);
        if(cursor.getCount()>0){ database.close(); cursor.close(); return true;}
        else{ database.close(); cursor.close(); return false;}
    }

    public void AddQuotation(Quotation quotation){
        ContentValues values = new ContentValues();
        values.put("Text",quotation.getQuoteText());
        values.put("Author",quotation.getQuoteAuthor());
        SQLiteDatabase database = getWritableDatabase();
        database.insert("QuotationContract",null, values);
        database.close();
    }

    public void ClearDatabase(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("QuotationContract",null,null);
        database.close();
    }

    public void RemoveQuotation(Quotation quotation){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("QuotationContract","Text=?",new String[]{quotation.getQuoteText()});
        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
