package com.shareable.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    EditText phoneNumber,otpNumber;
    Button loginButton;
    ProgressBar progressBar;

    private String vId;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    String userId;

    Boolean isStep1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumber = findViewById(R.id.phoneNumber);
        otpNumber = findViewById(R.id.otpNumber);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar2);

        otpNumber.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);
        loginButton.setText("Get OTP");
        isStep1 = true;

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progressBar.setVisibility(View.VISIBLE);
                mAuth = FirebaseAuth.getInstance();


                if(phoneNumber.getText().length() == 10){
                    loginButton.setText("Submit OTP");

                    otpNumber.setEnabled(true);
                    phoneNumber.setEnabled(false);
                    if(isStep1){
                        sendVerificationCode(phoneNumber.getText().toString());
                        isStep1=false;
                    }else{
                        verifyCode(otpNumber.getText().toString());
                    }



                }else{
                    loginButton.setText("Get OTP");
                    Toast.makeText(LoginActivity.this,"Enter Phone Number of 10 Numbers (+91 is not required)",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    private void verifyCode(String code){

        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vId,code);
        signInWithCredential(credential);


    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){




                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            progressBar.setVisibility(View.INVISIBLE);

                            userId = mAuth.getCurrentUser().getUid();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();


                            Map<String,Object> user = new HashMap<>();
                            user.put("phoneNumber",phoneNumber.getText().toString());
                            user.put("lastOtp",otpNumber.getText().toString());
                            
                            db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    
                                    SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("SIGN_IN_STATUS",true);
                                    editor.putString("USER_PHONE_NUMBER",phoneNumber.getText().toString());
                                    editor.putString("USER_UID",documentReference.getId());
                                    editor.apply();


                                    Intent intents = new Intent(LoginActivity.this,CreateProfileActivity.class);

                                    intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    Log.i("USER_CREATED WITH ID",documentReference.getId());
                                    startActivity(intents);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this,"Failed to Create User in FireStore",Toast.LENGTH_LONG).show();
                                }
                            });





//                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void sendVerificationCode(String number){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+91"+number)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            vId = s;
            progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };
}