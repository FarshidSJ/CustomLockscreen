package com.farshid.customlockscreen.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LockScreenViewModel extends AndroidViewModel {

    private LockScreenRepository lockScreenRepository;
    private LiveData<List<LockScreenEntity>> allData;
    private LiveData<List<LockScreenEntity>> active;


    public LockScreenViewModel(@NonNull Application application) {
        super(application);
        lockScreenRepository = new LockScreenRepository(application);
        allData = lockScreenRepository.getAllData();
        active = lockScreenRepository.getActive();
    }

    public LiveData<List<LockScreenEntity>> getAllData(){ return allData; }
    public LiveData<List<LockScreenEntity>> getActive(){ return  active; }

    public void updateActive(){
        lockScreenRepository.updateActive();
    }

    public void setActive(int imageId){
        lockScreenRepository.setActive(imageId);
    }

    public void updateLockScreen(LockScreenEntity lockScreenEntity){
        lockScreenRepository.updateLockScreen(lockScreenEntity);
    }

}
