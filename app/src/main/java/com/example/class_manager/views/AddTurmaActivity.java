package com.example.class_manager.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.class_manager.data.DBHelper;
import com.example.class_manager.R;
import com.example.class_manager.model.Turmas;

import java.util.Locale;

public class AddTurmaActivity extends AppCompatActivity {

    Button bt_adicionarturma, bt_cancelaradicionarturma;
    EditText et_adicionardesignacao, et_adicionarano;

    DBHelper DataBase = new DBHelper(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_turma);

            //Botões
        bt_adicionarturma = findViewById(R.id.bt_adicionarturma);
        bt_cancelaradicionarturma = findViewById(R.id.bt_cancelaradicionarturma);

            // Caixas de Texto Input
        et_adicionardesignacao = findViewById(R.id.et_adicionardesignacao);
        et_adicionarano = findViewById(R.id.et_adicionarano);

        bt_adicionarturma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value= et_adicionarano.getText().toString();
                int value1 = value.length();

                if ( value1 < 1 ) {
                    Toast.makeText(AddTurmaActivity.this, "Erro! Preencha o ANO da turma.", Toast.LENGTH_SHORT).show();

                } else {
                int ano = Integer.parseInt(value);

                String designacao = et_adicionardesignacao.getText().toString();
                int num = designacao.length();


                if (TextUtils.isEmpty(et_adicionardesignacao.getText().toString())
                        || num != 1
                        || ano > 12
                        || ano < 1)  {
                    Toast.makeText(AddTurmaActivity.this, "Por favor, preencha corretamente os dados antes de avançar!", Toast.LENGTH_SHORT).show();

                } else {

                    DataBase.insertTurma(et_adicionarano.getText(), et_adicionardesignacao.getText().toString().toUpperCase(Locale.ROOT));

                        // Voltar para a página de Listagem de Turmas
                    Intent i = new Intent(AddTurmaActivity.this, TurmasActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.new_added_class), Toast.LENGTH_LONG).show();

                    finish();
            }
            }}
        });

        bt_cancelaradicionarturma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Voltar para a página de Listagem de Alunos
                Intent i = new Intent(AddTurmaActivity.this, TurmasActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    public static class AlunosViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_row_alunos;


        public AlunosViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_row_alunos=(TextView) itemView.findViewById(R.id.tv_row_alunos);
            //itemView.setOnClickListener((View.OnClickListener) this);

        }

        public void bindData(Turmas.Alunos alunos) {
            // int ano = turmas.getAno();
            String nome = alunos.getNome().toString();
            String morada = alunos.getMorada().toString();
            String email = alunos.getEmail().toString();
            String telefone = alunos.getTelefone().toString();
            String displayAluno = nome + " - " + morada + " - " + email + " - " + telefone;
            tv_row_alunos.setText(displayAluno);
        }
    }

    public static class AboutActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about);
        }
    }
}