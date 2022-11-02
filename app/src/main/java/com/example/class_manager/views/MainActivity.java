package com.example.class_manager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.class_manager.data.DBHelper;
import com.example.class_manager.R;

public class MainActivity extends AppCompatActivity {

    ViewHolder mViewHolder = new ViewHolder();
    DBHelper DataBase = new DBHelper(this);
    Intent i;
    Button bt_login, about;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_login = findViewById(R.id.bt_login);
        about = findViewById(R.id.about);

        ImageView splash = (ImageView) this.findViewById(R.id.splash);



        splash.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                splash.setVisibility(View.GONE);
            }
        }, 2000);




        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

            }

        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddTurmaActivity.AboutActivity.class);
                startActivity(i);


            }

        });







    }

    public static class ViewHolder {
        public RecyclerView rv_turmas;
        public RecyclerView rv_alunos;
    }



}