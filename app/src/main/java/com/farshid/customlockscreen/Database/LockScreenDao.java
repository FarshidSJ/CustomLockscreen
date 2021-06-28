package com.farshid.customlockscreen.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LockScreenDao {

    @Update
    void updateLockScreen(LockScreenEntity lockScreenEntity);

    @Query("SELECT * FROM lock_screen ORDER BY id ASC")
    LiveData<List<LockScreenEntity>> getAll();

    @Query("SELECT * FROM lock_screen WHERE is_active = 1")
    LiveData<List<LockScreenEntity>> getActive();

    @Query("UPDATE lock_screen SET is_active = 1 WHERE id = :id")
    void setActive(int id);

    @Query("UPDATE lock_screen SET is_active = 0 WHERE is_active = 1")
    void updateActive();

}
