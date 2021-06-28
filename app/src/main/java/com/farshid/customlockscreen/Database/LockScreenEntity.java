package com.farshid.customlockscreen.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lock_screen")
public class LockScreenEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "image_name")
    private String imageName;

    @ColumnInfo(name = "gif_name")
    private String gifName;

    @ColumnInfo(name = "is_active")
    private int isActive;

    public LockScreenEntity(int id, String imageName, String gifName, int isActive){
        this.id = id;
        this.imageName = imageName;
        this.gifName = gifName;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getGifName() {
        return gifName;
    }

    public void setGifName(String gifName) {
        this.gifName = gifName;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
