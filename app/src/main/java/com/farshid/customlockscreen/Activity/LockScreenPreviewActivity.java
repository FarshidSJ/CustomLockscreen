package com.farshid.customlockscreen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.farshid.customlockscreen.Adapter.LockScreenAdapter;
import com.farshid.customlockscreen.Database.LockScreenEntity;
import com.farshid.customlockscreen.Database.LockScreenViewModel;
import com.farshid.customlockscreen.R;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LockScreenPreviewActivity extends AppCompatActivity {
    ImageView imageView;
    int imageId;
    String imageName, gifName;
    Button applyBtn;
    public LockScreenViewModel lockScreenViewModel;
    CoordinatorLayout previewLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen_preview);
        imageView = findViewById(R.id.img_lock_screen_preview);
        applyBtn = findViewById(R.id.btn_apply);
        previewLayout = findViewById(R.id.layout_preview);
        lockScreenViewModel = ViewModelProviders.of(this).get(LockScreenViewModel.class);

        Intent intent = getIntent();
        imageId = intent.getIntExtra(MainActivity.EXTRA_ID, 0);
        imageName = intent.getStringExtra(MainActivity.EXTRA_IMAGE_NAME);
        gifName = intent.getStringExtra(MainActivity.EXTRA_GIF_NAME);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockScreenViewModel.updateActive();
                MainActivity.lockScreenAdapter.notifyDataSetChanged();
                lockScreenViewModel.setActive(imageId);
//                Snackbar.make(previewLayout,"This is your new Lock Screen !",Snackbar.LENGTH_LONG).show();
                Toast.makeText(LockScreenPreviewActivity.this, "This is your new Lock Screen !", Toast.LENGTH_LONG).show();
            }
        });

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

        try
        {
            InputStream ims = getApplicationContext().getAssets().open(imageName);
            final Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);
            Glide.with(getApplicationContext()).asGif().placeholder(d).load("file:///android_asset/"+gifName).into(imageView);

            /*findViewById(R.id.img_lock_screen_preview).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Glide.with(getApplicationContext()).asGif().placeholder(d).load("file:///android_asset/"+gifName).into(imageView);

                    *//*new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);*//*
                    return true;
                }
            });*/


            ims.close();
        }
        catch(IOException ex)
        {
            return;
        }



    }

}
