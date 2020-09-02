package com.example.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText username,email,password;
    Button registerbtn;
    TextView regiview;
    FirebaseAuth auth;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.RegiUsername);
        email=findViewById(R.id.RegiEmail);
        password=findViewById(R.id.RegiPassword);

        registerbtn=findViewById(R.id.RegiBtn);
        regiview=findViewById(R.id.RegiTextview);

        //firebaseauth
        auth=FirebaseAuth.getInstance();
        progress=findViewById(R.id.progressBar);
        
         if(auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        registerbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String Email=email.getText().toString().trim();
                String psd=password.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(psd)){
                    password.setError("Password is required");
                    return;
                }
                if(psd.length()<4){
                    password.setError("password required more than 4 characters");
                    return;
                }
                progress.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(Email,psd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, go to main activity
                                    Toast.makeText(RegisterActivity.this, "User added successfully !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

            }
        });
     regiview.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(getApplicationContext(),LoginActivity.class));
         }
     });
    }
}