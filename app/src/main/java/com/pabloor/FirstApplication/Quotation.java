package com.pabloor.FirstApplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Quotation")
public class Quotation
{
    @NonNull
    @ColumnInfo(name = "Text")
    private String quoteText;

    @ColumnInfo(name = "Author")
    private String quoteAuthor;

    @ColumnInfo(name = "_ID")
    @PrimaryKey(autoGenerate = true)
    private int key;

    public Quotation(){}

    public Quotation(String quoteTextGiven, String quoteAuthorGiven){
        quoteText = quoteTextGiven;
        quoteAuthor = quoteAuthorGiven;
    }

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
}
