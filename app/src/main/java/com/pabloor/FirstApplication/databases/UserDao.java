package com.pabloor.FirstApplication.databases;

import androidx.room.*;

import com.pabloor.FirstApplication.Quotation;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<Quotation> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<Quotation> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    Quotation findByName(String first, String last);

    @Insert
    public void insert(Quotation quotation);

    @Delete
    public void delete(Quotation quotation);
}