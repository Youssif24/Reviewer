package com.saad.youssif.reviewer.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.saad.youssif.reviewer.R;

public class LoginActivity extends AppCompatActivity {

    private ImageView logoImgView;
    private EditText emailEt,passEt;
    private Button sign_in;
    private TextView rgstTv;
    Animation animUp,animDown;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    ConnectivityManager connectivityManager;
    SharedPreferences shrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        shrd=getSharedPreferences("UserLogin",this.MODE_PRIVATE);
        connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        logoImgView=(ImageView)findViewById(R.id.logoImgView);
        emailEt =(EditText)findViewById(R.id.usernameEt);
        passEt=(EditText)findViewById(R.id.passwordEt);
        rgstTv=(TextView)findViewById(R.id.registerTv);
        sign_in=(Button)findViewById(R.id.signBtn);
        animViews();
        register_from_shrd();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkConnection())
                {
                    checkUser();
                }

                else
                    Toast.makeText(LoginActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        });

        rgstTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rgstIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(rgstIntent);
            }
        });
    }

    public void animViews()
    {
        animUp = AnimationUtils.loadAnimation(this,R.anim.translateup);
        animDown=AnimationUtils.loadAnimation(this,R.anim.translatedown);
        logoImgView.setAnimation(animUp);
        emailEt.setAnimation(animUp);
        passEt.setAnimation(animUp);
        sign_in.setAnimation(animDown);
        rgstTv.setAnimation(animDown);
    }

    public boolean checkConnection()
    {
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        return info!=null&&info.isConnected();
    }

    public void save_user_data()
    {
        SharedPreferences.Editor editor=shrd.edit();
        editor.putString("email",emailEt.getText().toString().trim()).commit();
        editor.putString("password",passEt.getText().toString().trim()).commit();
    }

    public void checkUser()
    {
        String email=emailEt.getText().toString().trim();
        String password=passEt.getText().toString().trim();

        if(TextUtils.isEmpty(email))
            Toast.makeText(LoginActivity.this,"Enter Email",Toast.LENGTH_LONG).show();
        else if(TextUtils.isEmpty(password))
            Toast.makeText(LoginActivity.this,"Enter Password",Toast.LENGTH_LONG).show();
        else
        {
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            (firebaseAuth.signInWithEmailAndPassword(email,password))
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this," Welcome ",Toast.LENGTH_LONG).show();
                                save_user_data();
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                LoginActivity.this.finish();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this,"Failed,please try again",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }



    }

    public void register_from_shrd()
    {
        SharedPreferences.Editor editor=shrd.edit();
        String email=shrd.getString("email","");
        String pass=shrd.getString("password","");
        if(!(TextUtils.isEmpty(email))&&!(TextUtils.isEmpty(pass)))
        {
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            LoginActivity.this.finish();
        }
    }

}
