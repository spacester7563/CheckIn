package com.spacester.checkin.staff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.spacester.checkin.R;

import java.util.HashMap;

public class AddRoomActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 2;
    private static final int IMAGE_PICK = 5;

    Uri image_uri = null;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());

        img = findViewById(R.id.image_place);

        img.setOnClickListener(view -> {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
                    String[] permissions= {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                }else{
                    pickImage();
                }
            }else {
                pickImage();
            }
        });

        EditText name = findViewById(R.id.name);
        EditText cost = findViewById(R.id.cost);
        EditText capacity = findViewById(R.id.capacity);

        findViewById(R.id.singUp).setOnClickListener(view -> {
            if (name.getText().toString().isEmpty() || capacity.getText().toString().isEmpty() || cost.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter the details", Toast.LENGTH_SHORT).show();
            }else {

                StorageReference storageReference = FirebaseStorage.getInstance().getReference("room_image/"+ ""+System.currentTimeMillis());
                storageReference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();
                        if (uriTask.isSuccessful()){
                            String id = ""+System.currentTimeMillis();
                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("name", name.getText().toString());
                            hashMap.put("capacity", capacity.getText().toString());
                            hashMap.put("cost", cost.getText().toString());
                            hashMap.put("id", id);
                            hashMap.put("image", downloadUri.toString());
                            FirebaseDatabase.getInstance().getReference().child("Rooms").child(id).setValue(hashMap);
                            Toast.makeText(AddRoomActivity.this, "Added", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            finish();
                        }

                    }
                });

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickImage();
        }else {
            Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickImage() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK && data != null){
            image_uri = data.getData();
            Glide.with(AddRoomActivity.this).load(image_uri).into(img);
        }
    }
}