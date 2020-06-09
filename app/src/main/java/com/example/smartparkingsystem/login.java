package com.example.smartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText txt_Email,txt_Password;
    Button btn_login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("SMART PARKING");
        txt_Email=(EditText)findViewById(R.id.txt_Email);
        txt_Password=(EditText)findViewById(R.id.txt_Password);
        btn_login=(Button)findViewById(R.id.btn_login);

        firebaseAuth= FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = txt_Email.getText().toString();
                String password = txt_Password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(login.this, "Password is too short.Min length is 6 characters", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));

                                } else {

                                    Toast.makeText(login.this, "invalid password or username", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });




    }

    public void btn_register(View view) {
        startActivity(new Intent(getApplicationContext(),signup.class));
    }
}

