package com.pabloor.FirstApplication.databases;

import androidx.room.*;

import com.pabloor.FirstApplication.Quotation;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM Quotation")
    List<Quotation> getAll();

    @Query("SELECT * FROM Quotation WHERE Text=:text")
    Quotation findByName(String text);

    @Query("DELETE FROM Quotation")
    void DeleteAll();

    @Insert
    public void insert(Quotation quotation);

    @Delete
    public void delete(Quotation quotation);
}