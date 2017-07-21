package com.saad.youssif.reviewer.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saad.youssif.reviewer.R;

public class RegisterActivity extends AppCompatActivity {

    EditText nameEt,emailEt,passEt,confirmPassEt;
    Button registerBtn;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        progressDialog=new ProgressDialog(this);
        nameEt=(EditText)findViewById(R.id.registerNameEt);
        emailEt=(EditText)findViewById(R.id.registerEmailEt);
        passEt=(EditText)findViewById(R.id.registerPasswordEt);
        confirmPassEt=(EditText)findViewById(R.id.registerConfirmPasswordEt);
        registerBtn=(Button)findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateName()&&validateEmail()&&validatePassword()&&isPasswordMatched())
                {
                    if(checkConnection()){
                    progressDialog.setMessage("Registering User......");
                    progressDialog.show();
                    insertUser();}
                    else
                        Toast.makeText(RegisterActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public boolean checkConnection()
    {
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        return info!=null&&info.isConnected();
    }

    public void insertUser()
    {
        final String Name=nameEt.getText().toString().trim();
        String Email=emailEt.getText().toString().trim();
        String Password=passEt.getText().toString().trim();

        (firebaseAuth.createUserWithEmailAndPassword(Email,Password))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            String user_id=firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_id=databaseReference.child(user_id);
                            current_user_id.child("name").setValue(Name);
                            progressDialog.hide();
                            Toast.makeText(RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
                            startActivity(intent);
                            RegisterActivity.this.finish();
                        }
                        else
                        {
                            progressDialog.hide();
                            Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }

    public boolean validateName()
    {
        if(nameEt.getText().toString().equals(""))
        {
            nameEt.setError("Enter Name");
            return false;
        }

          return true;
    }
    public boolean validateEmail()
    {
        if(emailEt.getText().toString().equals(""))
        {
            emailEt.setError("Email Required !!");
            return false;
        }
        else if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(emailEt.getText().toString().trim()).matches()))
        {
            emailEt.setError("Email not valid !!");
            return false;
        }
        return true;
    }
    public boolean validatePassword()
    {
     if(passEt.getText().toString().equals("") )
     {
         passEt.setError("Enter Password");
         return false;
     }
     else if( (passEt.getText().toString().trim().length())<8){
         Toast.makeText(RegisterActivity.this,"Password less than 8 characters...",Toast.LENGTH_LONG).show();
         return false;
     }
     return true;
    }
    public boolean isPasswordMatched()
    {
        if(passEt.getText().toString().equals(confirmPassEt.getText().toString()))
            return true;
        else
        {
            Toast.makeText(RegisterActivity.this,"Mismatched Password",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
