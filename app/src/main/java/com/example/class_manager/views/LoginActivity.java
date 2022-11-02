package com.example.class_manager.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.class_manager.data.DBHelper;
import com.example.class_manager.R;

public class LoginActivity extends AppCompatActivity {

    DBHelper DataBase = new DBHelper(this);

    Button bt_submitlogin;
    EditText et_user;
    EditText et_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // VALIDAÇÃO DE LOGIN


        bt_submitlogin = (Button) findViewById(R.id.bt_submitlogin);
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);



        bt_submitlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {




                // Validação de Autenticação //

                SQLiteDatabase db = DataBase.getReadableDatabase();

                // To read or show singel data
                if(et_password.getText().toString().trim().isEmpty() ||
                        et_user.getText().toString().trim().isEmpty()){

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fill_all_login), Toast.LENGTH_LONG).show();

                }else {

                    //To check wether the feed username and password match or not
                    Cursor c = db.rawQuery("SELECT * FROM Utilizadores WHERE utilizador='"
                                    + et_user.getText().toString().trim() + "'" +
                                    "AND password='"+et_password.getText().toString().trim()+"'"
                            , null);

                    if (c.moveToFirst()) {

                        /*String loginUsername = c.getString(1);
                        String loginEmail = c.getString(2);
                        String loginPassword = c.getString(3);

                        // showMessage(loginUsername, loginPassword);*/

                        Intent i = new Intent(LoginActivity.this, AlunosActivity.MenuPrincipalActivity.class);
                        startActivity(i);

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.successful_login), Toast.LENGTH_LONG).show();


                    } else {

                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_login), Toast.LENGTH_LONG).show();

                    }
                }
            }
        });




    }
}




