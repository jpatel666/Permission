package com.example.pemission;

import static android.Manifest.permission.CAMERA;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemission.PermissionActivity.AppPermissionActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextscreen();
            }
        }, 5000);
    }

    void nextscreen() {
        if (checkPermission()) {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkPermission()) {
            Intent intent = new Intent(this, AppPermissionActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean checkPermission() {

        //23,24,25...,29,30,31,32
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        }

        //33,34...
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        }

        //
        else {
            return true;
        }
    }

}