package com.pabloor.FirstApplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Quotation", indices = {@Index(value = {"Text"}, unique = true)})
public class Quotation
{
    @NonNull
    @ColumnInfo(name = "Text")
    @SerializedName("quoteText")
    private String quoteText;

    @ColumnInfo(name = "Author")
    @SerializedName("quoteAuthor")
    private String quoteAuthor;

    @ColumnInfo(name = "_ID")
    @PrimaryKey(autoGenerate = true)
    private int key;

    public Quotation(){}

    public Quotation(String quoteTextGiven, String quoteAuthorGiven){
        quoteText = quoteTextGiven;
        quoteAuthor = quoteAuthorGiven;
    }

    public int getKey() { return key; }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public void setKey(int key) { this.key = key; }
}
