package com.eventfire.ashley.eventfire;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

import static com.eventfire.ashley.eventfire.User_model.MY_PREFS;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,phoneNum,username,classDiv,collegeName;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference sref;
    public static User_model nUser;
    Map<String, Object> keys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        sref= FirebaseDatabase.getInstance().getReference("Users");

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

                phoneNum= (EditText) findViewById(R.id.userPhonenum);
                username= (EditText) findViewById(R.id.username);
                classDiv= (EditText) findViewById(R.id.classdiv);
                collegeName= (EditText) findViewById(R.id.college);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    keys = RSAEncryptionJava8.initialize() ;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                final PrivateKey privateKey = (PrivateKey) keys.get("private");
               final PublicKey publicKey = (PublicKey) keys.get("public");
                User_model.pubKey= publicKey;
                User_model.priKey = privateKey;
               // Toast.makeText(SignupActivity.this, "signup activity:"+privateKey.toString(), Toast.LENGTH_SHORT).show();

               // final String email = null;
                try {
                    final String email = inputEmail.getText().toString().trim();
                //    final String college = collegeName.getText().toString().trim();
                //    final String phone = phoneNum.getText().toString().trim();
                    //final String usern = username.getText().toString().trim();
                //    final String classd = classDiv.getText().toString().trim();

//inputEmail.getText().toString().trim();
                //final String email = RSAEncryptionJava8.encryptMessage(emailn,publicKey);
                //    Toast.makeText(SignupActivity.this,email, Toast.LENGTH_SHORT).show();
                final String password = RSAEncryptionJava8.encryptMessage(inputPassword.getText().toString().trim(),publicKey);
                final String college=RSAEncryptionJava8.encryptMessage(collegeName.getText().toString().trim(),publicKey);
                final String phone=RSAEncryptionJava8.encryptMessage(phoneNum.getText().toString().trim(),publicKey);
                final String usern=RSAEncryptionJava8.encryptMessage(username.getText().toString().trim(),publicKey);
                final String classd=RSAEncryptionJava8.encryptMessage(classDiv.getText().toString().trim(),publicKey);
                //String =.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(college)) {
                    Toast.makeText(getApplicationContext(), "Enter college name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(usern)) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(classd)) {
                    Toast.makeText(getApplicationContext(), "Enter  class name !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }



                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {

                                    Toast.makeText(SignupActivity.this, task.getException().toString(),Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignupActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                    nUser=new User_model(phone,email,password,college,classd,usern,"none",publicKey.toString());

                                    Bundle userBundlem=new Bundle();

                                    userBundlem.putSerializable("userObj",nUser);
                                    userBundlem.putSerializable("privatekey",privateKey);
                                  //  Bundle keyb = new Bundle();
                                   // keyb.putSerializable("privatekey",privateKey);
                                    Intent intent=new Intent(SignupActivity.this, MainActivity.class);
                                   intent.putExtras(userBundlem);
                                   // intent.putExtras(keyb);
                                    startActivity(intent);

                                    sref.push().setValue(nUser);
                                    finish();
                                }
                            }
                        });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }



}
