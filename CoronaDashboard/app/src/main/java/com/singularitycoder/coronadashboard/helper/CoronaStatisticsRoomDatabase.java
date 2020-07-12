package com.singularitycoder.coronadashboard.helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.singularitycoder.coronadashboard.model.CoronaResponse;
import com.singularitycoder.coronadashboard.roomdao.StatisticsDao;

@Database(entities = {
        CoronaResponse.class
}, version = 1, exportSchema = false)
public abstract class CoronaStatisticsRoomDatabase extends RoomDatabase {

    @Nullable
    private static CoronaStatisticsRoomDatabase instance;

    @Nullable
    public abstract StatisticsDao statisticsDao();

    @NonNull
    public static synchronized CoronaStatisticsRoomDatabase getInstance(Context context) {
        if (null == instance) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), CoronaStatisticsRoomDatabase.class, "statistics_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}