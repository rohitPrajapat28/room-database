package com.example.random1.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT EXISTS (SELECT * FROM user WHERE email = :email)")
    boolean is_taken(String email);

    @Query("SELECT EXISTS (SELECT * FROM user WHERE email = :email AND password =:password)")
    boolean login(String email, String password);
}