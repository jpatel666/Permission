package com.example.pemission;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    Button btnCamera, btnPhoto;
    CircleImageView imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamera = findViewById(R.id.btnCamera);
        btnPhoto = findViewById(R.id.btnPhoto);
        imgUser = findViewById(R.id.imgUser);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, 1);
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Camera Click Photo
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                imgUser.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "Image Is Not Selected", Toast.LENGTH_SHORT).show();
            }
        }

        //Gallery Select Photo
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                imgUser.setImageURI(selectedImageUri);
            } else {
                Toast.makeText(this, "Image Is Not Selected", Toast.LENGTH_SHORT).show();
            }

        }
    }
}