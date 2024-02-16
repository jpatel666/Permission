package com.example.pemission.PermissionActivity;


import static android.Manifest.permission.CAMERA;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pemission.MainActivity;
import com.example.pemission.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class AppPermissionActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_app_permission);


        findViewById(R.id.btn_done).setOnClickListener(view -> setPermissions2());
    }

    public void setPermissions2() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Dexter.withContext(AppPermissionActivity.this)
                    .withPermissions(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    ).withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                next();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                        }


                    }).check();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Dexter.withContext(AppPermissionActivity.this)
                    .withPermissions(
                            Manifest.permission.READ_MEDIA_AUDIO,
                            Manifest.permission.READ_MEDIA_IMAGES,
                            Manifest.permission.READ_MEDIA_VIDEO,
                            Manifest.permission.CAMERA
                    ).withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                next();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                        }

                    }).check();
        } else {
            next();
        }
    }

    private boolean checkPermission() {

        //23,24,25...,29,30,31,32
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        }

        //33,34...
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
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

    public void next() {
        if (checkPermission()) {
            Intent intent = new Intent(AppPermissionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkPermission()) {
            Intent intent = new Intent(this, AppPermissionActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
