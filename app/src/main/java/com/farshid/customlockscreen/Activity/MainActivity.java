package com.farshid.customlockscreen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import com.farshid.customlockscreen.Adapter.LockScreenAdapter;
import com.farshid.customlockscreen.Database.LockScreenEntity;
import com.farshid.customlockscreen.Database.LockScreenViewModel;
import com.farshid.customlockscreen.LockScreenSharedPrefManager;
import com.farshid.customlockscreen.LockScreenState;
import com.farshid.customlockscreen.R;
import com.farshid.customlockscreen.Service.MyService;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.farshid.customlockscreen.EXTRA_ID";
    public static final String EXTRA_IMAGE_NAME =
            "com.farshid.customlockscreen.EXTRA_IMAGE_NAME";
    public static final String EXTRA_GIF_NAME =
            "com.farshid.customlockscreen.EXTRA_GIF_NAME";
    private static final String TAG = "MainActivity";
    private LockScreenSharedPrefManager lockScreenSharedPrefManager;
    private LockScreenState lockScreenState;
    private CoordinatorLayout coordinatorLayout;
    public LockScreenViewModel lockScreenViewModel;
    public static LockScreenAdapter lockScreenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*RecyclerView recyclerView = findViewById(R.id.recycler_view_lock_screens);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false));
        final LockScreenAdapter lockScreenAdapter = new LockScreenAdapter(this);
        recyclerView.setAdapter(lockScreenAdapter);*/

        RecyclerView recyclerView = findViewById(R.id.recycler_view_lock_screens);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, LinearLayoutManager.VERTICAL, false));
        lockScreenAdapter = new LockScreenAdapter(MainActivity.this);
//        Log.e(TAG, "onCreate: " );
//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(lockScreenAdapter);


        lockScreenViewModel = ViewModelProviders.of(this).get(LockScreenViewModel.class);
        lockScreenViewModel.getAllData().observe(this, new Observer<List<LockScreenEntity>>() {
            @Override
            public void onChanged(List<LockScreenEntity> lockScreenEntities) {
//                lockScreenAdapter.notifyDataSetChanged();
                lockScreenAdapter.submitList(lockScreenEntities);
//                Log.e(TAG, "onChanged: " );
            }
        });




        coordinatorLayout = findViewById(R.id.layout_main);
        lockScreenSharedPrefManager = new LockScreenSharedPrefManager(this);
        lockScreenState = lockScreenSharedPrefManager.getLockScreenState();
        final TextView lockScreenStateText = findViewById(R.id.txt_lock_screen_state);
        lockScreenStateText.setText(lockScreenState.getLockScreenStateText());
        final Switch lockScreenStateSwitch = findViewById(R.id.switch_lock_screen);
        lockScreenStateSwitch.setChecked(lockScreenState.isLockScreenStateSwitch());
        if(isMyServiceRunning(MyService.class)){
            lockScreenStateText.setText("ON");
            lockScreenStateSwitch.setChecked(true);
            lockScreenState.setLockScreenStateSwitch(true);
            lockScreenState.setLockScreenStateText("ON");
            lockScreenSharedPrefManager.saveState(lockScreenState);
        }else{
            lockScreenStateText.setText("OFF");
            lockScreenStateSwitch.setChecked(false);
            lockScreenState.setLockScreenStateSwitch(false);
            lockScreenState.setLockScreenStateText("OFF");
            lockScreenSharedPrefManager.saveState(lockScreenState);
        }
        lockScreenStateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lockScreenStateSwitch.isChecked()){
                    startService(new Intent(MainActivity.this, MyService.class));
                    lockScreenStateText.setText("ON");
                    lockScreenState.setLockScreenStateSwitch(true);
                    lockScreenState.setLockScreenStateText("ON");
                    lockScreenSharedPrefManager.saveState(lockScreenState);

                }else{
                    stopService(new Intent(MainActivity.this, MyService.class));
                    lockScreenStateText.setText("OFF");
                    lockScreenState.setLockScreenStateSwitch(false);
                    lockScreenState.setLockScreenStateText("OFF");
                    lockScreenSharedPrefManager.saveState(lockScreenState);
                }
            }
        });

       /* lockScreenViewModel.getActive().observe(this, new Observer<List<LockScreenEntity>>() {
            @Override
            public void onChanged(List<LockScreenEntity> lockScreenEntities) {
            }
        });*/

        lockScreenAdapter.setOnItemClickListener(new LockScreenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LockScreenEntity lockScreenEntity) {
                Intent intent = new Intent(MainActivity.this, LockScreenPreviewActivity.class);
                intent.putExtra(EXTRA_ID, lockScreenEntity.getId());
                intent.putExtra(EXTRA_IMAGE_NAME, lockScreenEntity.getImageName());
                intent.putExtra(EXTRA_GIF_NAME, lockScreenEntity.getGifName());
                /*lockScreenViewModel.updateActive();
                lockScreenEntity.setIsActive(1);
                lockScreenViewModel.updateLockScreen(lockScreenEntity);*/
                Log.e(TAG, "onItemClick: " + lockScreenEntity.getIsActive() );
//                lockScreenAdapter.notifyDataSetChanged();
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        lockScreenAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        lockScreenAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        lockScreenAdapter.notifyDataSetChanged();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(coordinatorLayout,"Press Back one more time to exit",Snackbar.LENGTH_LONG).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
