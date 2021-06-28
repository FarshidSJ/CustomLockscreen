package com.farshid.customlockscreen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.farshid.customlockscreen.Database.LockScreenEntity;
import com.farshid.customlockscreen.Database.LockScreenViewModel;
import com.farshid.customlockscreen.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LockScreenActivity extends AppCompatActivity {
    ImageView imageView;
    public LockScreenViewModel lockScreenViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        imageView = findViewById(R.id.img_lock_screen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        lockScreenViewModel = ViewModelProviders.of(this).get(LockScreenViewModel.class);
        lockScreenViewModel.getActive().observe(LockScreenActivity.this, new Observer<List<LockScreenEntity>>() {
            @Override
            public void onChanged(final List<LockScreenEntity> lockScreenEntities) {
                final InputStream ims;
                try {
                    ims = getApplicationContext().getAssets().open(lockScreenEntities.get(0).getImageName());
                    final Drawable d = Drawable.createFromStream(ims, null);
                    imageView.setImageDrawable(d);
                    findViewById(R.id.img_lock_screen).setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Glide.with(getApplicationContext()).asGif().placeholder(d).load("file:///android_asset/"+lockScreenEntities.get(0).getGifName()).into(imageView);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 1000);
                            try {
                                ims.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return true;
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
}
