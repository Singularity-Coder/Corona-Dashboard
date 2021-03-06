package com.singularitycoder.coronadashboard.roomdao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.singularitycoder.coronadashboard.model.CoronaResponse;

@Dao
public interface StatisticsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(CoronaResponse coronaResponse);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateItem(CoronaResponse coronaResponse);

    @Delete
    void deleteItem(CoronaResponse coronaResponse);

    @Query("SELECT * FROM table_statistics WHERE RoomId=:id")
    CoronaResponse getItem(int id);

    @Query("DELETE FROM table_statistics")
    void deleteAllItems();
}