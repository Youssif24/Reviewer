package com.saad.youssif.reviewer.controller;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.saad.youssif.reviewer.R;

import java.io.IOException;
import java.io.InputStream;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences shrd;
    ImageView imageView;
    TextView introTv;
    String text="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView=(ImageView)findViewById(R.id.sliderImageView);
        introTv=(TextView)findViewById(R.id.introTv);
        shrd=getSharedPreferences("UserLogin",this.MODE_PRIVATE);

        ((AnimationDrawable) imageView.getBackground()).start();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try{
            InputStream is=getAssets().open("introduction.txt");
            int size=is.available();
            byte []buffer=new byte[size];
            is.read(buffer);
            is.close();
            text=new String(buffer);

        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
        introTv.setText(text);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.lawEnfo) {
            Intent intent=new Intent(HomeActivity.this,OptionsActivity.class);
            intent.putExtra("lawEnfo","lawEnfo");
            startActivity(intent);
        } else if (id == R.id.crimin) {
            Intent intent=new Intent(HomeActivity.this,OptionsActivity.class);
            intent.putExtra("crimin","crimin");
            startActivity(intent);
        } else if (id == R.id.crinJur) {
            Intent intent=new Intent(HomeActivity.this,OptionsActivity.class);
            intent.putExtra("crinJur","crinJur");
            startActivity(intent);

        } else if (id == R.id.crimDet) {
            Intent intent=new Intent(HomeActivity.this,OptionsActivity.class);
            intent.putExtra("crimDet","crimDet");
            startActivity(intent);
        }else if(id==R.id.socOfCrimes) {
            Intent intent=new Intent(HomeActivity.this,OptionsActivity.class);
            intent.putExtra("socOfCrimes","socOfCrimes");
            startActivity(intent);
        }else if(id==R.id.CorrAdmin){
            Intent intent=new Intent(HomeActivity.this,OptionsActivity.class);
            intent.putExtra("CorrAdmin","CorrAdmin");
            startActivity(intent);
        } else if (id == R.id.log_out) {
            SharedPreferences.Editor editor=shrd.edit();
            editor.clear().commit();
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            HomeActivity.this.finish();

        }
        else if(id==R.id.scoresOption)
        {
            startActivity(new Intent(HomeActivity.this,ScoresActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
