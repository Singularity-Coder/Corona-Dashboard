package com.singularitycoder.coronadashboard.roomdao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.singularitycoder.coronadashboard.model.CoronaResponse;

import java.util.List;

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

    @Query("SELECT * FROM table_statistics ORDER BY RoomId ASC")
    LiveData<List<CoronaResponse>> getAllItems();

    @Query("DELETE FROM table_statistics")
    void deleteAllItems();
}