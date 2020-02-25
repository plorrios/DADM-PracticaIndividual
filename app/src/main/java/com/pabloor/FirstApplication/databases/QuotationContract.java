package com.pabloor.FirstApplication.databases;

import android.provider.BaseColumns;

public class QuotationContract {

    public static class Table implements BaseColumns{
        public static final String TABLE_NAME = "Quotation";
        public static final String ID_COL = "_ID";
        public static final String AUTHOR_COL = "Author";
        public static final String TEXT_COL = "Text";
    }

    private QuotationContract(){}
}
