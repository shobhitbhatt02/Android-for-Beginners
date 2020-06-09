package com.example.smartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    EditText txt_fullname,txt_phonenumber,txt_email,txt_password,txt_cfmpassword;
    Button btn_register;
    RadioButton radioGenderMale,radioGenderFemale;
    DatabaseReference databaseReference;
    String gender="";
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("Smart Parkng");

        txt_fullname=(EditText)findViewById(R.id.txt_full_name);
        txt_phonenumber=(EditText)findViewById(R.id.txt_phonenumber);
        txt_email=(EditText)findViewById(R.id.txt_email);
        txt_password=(EditText)findViewById(R.id.txt_password);
        btn_register=(Button) findViewById(R.id.btn_register);
        radioGenderFemale=(RadioButton) findViewById(R.id.radio_female);
        radioGenderMale=(RadioButton)findViewById(R.id.radio_male);
        txt_cfmpassword=(EditText)findViewById(R.id.txt_cfmpassword);

        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth= FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname = txt_fullname.getText().toString();
                final String phonenumber = txt_phonenumber.getText().toString();
                final String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();
                String cfmpassword=txt_cfmpassword.getText().toString();

                if (radioGenderMale.isChecked()) {
                    gender = "Male";
                }
                if (radioGenderFemale.isChecked()) {
                    gender = "Female";
                }
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(signup.this, "Please Enter Email", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(signup.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(fullname))
                {
                    Toast.makeText(signup.this, "Please Enter FullName", Toast.LENGTH_SHORT).show();

                }
                if(TextUtils.isEmpty(phonenumber))
                {
                    Toast.makeText(signup.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();

                }
                if(password.length()<6){
                    Toast.makeText(signup.this, "Password is too short.Min length is 6 characters", Toast.LENGTH_SHORT).show();
                }



                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    users information=new users(
                                            fullname,
                                            phonenumber,
                                            email,
                                            gender
                                    );

                                    FirebaseDatabase.getInstance().getReference("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(signup.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),login.class));
                                        }
                                    });

                                } else {

                                }

                                // ...
                            }
                        });

            }


        });
    }
}

