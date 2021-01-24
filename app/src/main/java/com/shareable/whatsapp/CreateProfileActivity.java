package com.shareable.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateProfileActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    Uri uri;
    FloatingActionButton submitButton;
    EditText name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        button = findViewById(R.id.pickImageCreateProfile);
        imageView = findViewById(R.id.addProfileImageId);
        submitButton = findViewById(R.id.toCreateProfileButton);
        name = findViewById(R.id.nameCreateProfile);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                name.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                }else{
                    name.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LENGTH of Text ", String.valueOf(name.getText().length()));
                Log.i("IMAGE",imageView.toString());
                if(name.getText().length() != 0){


                    SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF",MODE_PRIVATE);
                    String uid = sharedPreferences.getString("USER_UID","");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USER_NAME",name.getText().toString());
                    editor.apply();


                    FirebaseFirestore db = FirebaseFirestore.getInstance();
//                    DocumentReference documentReference = db.collection("users").document(uid);
                    Map<String, Object> userObject = new HashMap<>();
                    userObject.put("username", name.getText().toString());

                    db.collection("users").document(uid)
                            .set(userObject, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Intent intent = new Intent(CreateProfileActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateProfileActivity.this,"Failed to Set Name in CreateProfile Activity",Toast.LENGTH_LONG).show();
                                }
                            });





                }else{
                    Toast.makeText(CreateProfileActivity.this,"Enter name Please!",Toast.LENGTH_LONG).show();
                    name.setBackgroundColor(Color.RED);
                }
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
                uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


        }
    }
}