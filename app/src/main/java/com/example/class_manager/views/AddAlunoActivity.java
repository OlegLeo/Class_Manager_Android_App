package com.example.class_manager.views;

import static com.example.class_manager.views.AlunosActivity.getIdFromTurma;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.class_manager.data.DBHelper;
import com.example.class_manager.R;
import com.example.class_manager.model.Turmas;

import java.util.ArrayList;
import java.util.List;

public class AddAlunoActivity extends AppCompatActivity {


    EditText et_adicionar_nome, et_adicionar_morada, et_adicionar_email, et_adicionar_telefone;
    Button bt_adicionar_aluno, bt_cancelar_adicionar_aluno ;

    Spinner spinner;

    DBHelper DataBase = new DBHelper(this);
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aluno);

        // Caixas de Texto Input
        et_adicionar_nome = findViewById(R.id.et_adicionar_nome);
        et_adicionar_morada = findViewById(R.id.et_adicionar_morada);
        et_adicionar_email = findViewById(R.id.et_adicionar_email);
        et_adicionar_telefone = findViewById(R.id.et_adicionar_telefone);

        //Botões
        bt_adicionar_aluno = findViewById(R.id.bt_adicionar_aluno);
        bt_cancelar_adicionar_aluno = findViewById(R.id.bt_cancelar_adicionar_aluno);

        //Spinner
        spinner = findViewById(R.id.spinner_adicionar_turma);


        // Trazer a listaTurmas para dentro desta ACTIVITY

        List<Turmas> listaTurmas = new ArrayList<>();
        Cursor c = DataBase.selectAllTurmas();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                @SuppressLint("Range") int turma_id = c.getInt(c.getColumnIndex("turma_id"));
                @SuppressLint("Range") int ano = c.getInt(c.getColumnIndex("ano"));
                @SuppressLint("Range") String descricao = c.getString(c.getColumnIndex("descricao")).toString();
                listaTurmas.add(new Turmas(turma_id, ano, descricao));
            } while (c.moveToNext());
        }


        //Transportar listaTurmas para dentro do spinner

        ArrayAdapter aa = new ArrayAdapter(AddAlunoActivity.this, android.R.layout.simple_spinner_item, listaTurmas);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //int getIdFromTurma = listaTurmas.get(i).getTurma_id();

                //Toast.makeText(AddAlunoActivity.this, String.valueOf(getIdFromTurma), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast.makeText(MainActivity.this, "Nada selecionado", Toast.LENGTH_SHORT).show();
            }
        });



        bt_adicionar_aluno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DataBase.insertAluno(
                            getIdFromTurma,
                            et_adicionar_nome.getText().toString(),
                            et_adicionar_morada.getText().toString(),
                            et_adicionar_email.getText().toString(),
                            et_adicionar_telefone.getText().toString());


                    // Voltar para a página de Listagem de Alunos
                    Intent i = new Intent(AddAlunoActivity.this, AlunosActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.new_added_student), Toast.LENGTH_LONG).show();
                finish();
                }
            });



        bt_cancelar_adicionar_aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Voltar para a página de Listagem de Alunos
                Intent i = new Intent(AddAlunoActivity.this, AlunosActivity.class);
                startActivity(i);
                finish();
            }
        });



        }




        /*public static class AlunosViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_row_alunos;


            public AlunosViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_row_alunos=(TextView) itemView.findViewById(R.id.tv_row_alunos);
                //itemView.setOnClickListener((View.OnClickListener) this);

            }

            public void bindData(Alunos alunos) {
                // int ano = turmas.getAno();
                String nome = alunos.getNome().toString();
                String morada = alunos.getMorada().toString();
                String email = alunos.getEmail().toString();
                String telefone = alunos.getTelefone().toString();
                String displayAluno = nome + " - " + morada + " - " + email + " - " + telefone;
                tv_row_alunos.setText(displayAluno);
            }
        }*/
        }