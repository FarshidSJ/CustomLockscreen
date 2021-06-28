package com.farshid.customlockscreen.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LockScreenRepository {

    private LockScreenDao lockScreenDao;
    private LiveData<List<LockScreenEntity>> allData;
    private LiveData<List<LockScreenEntity>> active;


    public LockScreenRepository(Application application){
        LockScreenDatabase lockScreenDatabase = LockScreenDatabase.getInstance(application);
        lockScreenDao = lockScreenDatabase.lockScreenDao();
        allData = lockScreenDao.getAll();
        active = lockScreenDao.getActive();
    }

    public LiveData<List<LockScreenEntity>> getAllData(){ return allData; }
    public LiveData<List<LockScreenEntity>> getActive(){ return  active; }

    public void updateLockScreen(LockScreenEntity lockScreenEntity){
        new UpdateLockScreenAsyncTask(lockScreenDao).execute(lockScreenEntity);
    }

    public void updateActive(){
        new UpdateActiveAsyncTask(lockScreenDao).execute();
    }
    public void setActive(int imageId){
        new SetActiveAsyncTask(lockScreenDao, imageId).execute();
    }


    private static class UpdateLockScreenAsyncTask extends AsyncTask<LockScreenEntity, Void, Void>{
        private LockScreenDao lockScreenDao;
        private UpdateLockScreenAsyncTask(LockScreenDao lockScreenDao){
            this.lockScreenDao = lockScreenDao;
        }

        @Override
        protected Void doInBackground(LockScreenEntity... lockScreenEntities) {
            lockScreenDao.updateLockScreen(lockScreenEntities[0]);
            return null;
        }
    }



    private static class UpdateActiveAsyncTask extends AsyncTask<LockScreenEntity, Void, Void>{
        private LockScreenDao lockScreenDao;
        private UpdateActiveAsyncTask(LockScreenDao lockScreenDao){
            this.lockScreenDao = lockScreenDao;
        }

        @Override
        protected Void doInBackground(LockScreenEntity... lockScreenEntities) {
            lockScreenDao.updateActive();
            return null;
        }
    }

    private static class SetActiveAsyncTask extends AsyncTask<LockScreenEntity, Void, Void>{
        private LockScreenDao lockScreenDao;
        private int id;
        private SetActiveAsyncTask(LockScreenDao lockScreenDao, int id){
            this.lockScreenDao = lockScreenDao;
            this.id = id;
        }

        @Override
        protected Void doInBackground(LockScreenEntity... lockScreenEntities) {
            lockScreenDao.setActive(id);
            return null;
        }
    }


}
