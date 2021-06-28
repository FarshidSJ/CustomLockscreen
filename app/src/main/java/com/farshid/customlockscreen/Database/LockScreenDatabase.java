package com.farshid.customlockscreen.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(exportSchema = false, entities = LockScreenEntity.class, version = 1)
public abstract class LockScreenDatabase extends RoomDatabase {

    public static LockScreenDatabase instance;
    public abstract LockScreenDao lockScreenDao();

    public static synchronized LockScreenDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, LockScreenDatabase.class, "lock_screen")
                    .createFromAsset("database/lock_screen.db")
                    .build();
        }
        return instance;
    }



}
