package com.example.class_manager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.class_manager.adapter.AlunosListAdapter;
import com.example.class_manager.data.DBHelper;
import com.example.class_manager.R;
import com.example.class_manager.model.Turmas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlunosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button bt_removeraluno, bt_cancelareditaraluno, bt_atualizaraluno, bt_adicionaraluno, bt_ordenar_aluno_maior, bt_ordenar_aluno_menor;
    EditText et_editarnome, et_editarmorada, et_editaremail, et_editartelefone;
    TextView tv_textonome, tv_textomorada, tv_textoemail, tv_textotelefone, tv_texto_turma_activityalunos;

    Spinner spinner;
    public static ViewHolder mViewHolder = new ViewHolder();
    public DBHelper DataBase = new DBHelper(this);
    public static List<Turmas.Alunos> listaAlunos = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    public static int getIdFromTurma;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);



        ////////////////////////////////////////////////////////////////////////////////////////////////////
        // Chamar os elementos da activity.xml pelo seu respetivo ID

        // Inputs
        et_editarnome = findViewById(R.id.et_editarnome);
        et_editarmorada = findViewById(R.id.et_editarmorada);
        et_editartelefone = findViewById(R.id.et_editartelefone);
        et_editaremail = findViewById(R.id.et_editaremail);
        //et_editaranoturmadoaluno = findViewById(R.id.et_editaranoturmadoaluno);
        //et_editardesignacaoturmadoaluno = findViewById(R.id.et_editardesignacaoturmadoaluno);

        // Botões
        bt_removeraluno = findViewById(R.id.bt_removeraluno);
        bt_cancelareditaraluno = findViewById(R.id.bt_cancelareditaraluno);
        bt_atualizaraluno = findViewById(R.id.bt_atualizaraluno);
        bt_adicionaraluno = findViewById(R.id.bt_adicionaraluno);
        bt_ordenar_aluno_maior = findViewById(R.id.bt_ordenar_aluno_maior);
        bt_ordenar_aluno_menor = findViewById(R.id.bt_ordenar_aluno_menor);


        // Caixas de Texto
        tv_textonome = findViewById(R.id.tv_textonome);
        tv_textomorada = findViewById(R.id.tv_textomorada);
        tv_textotelefone = findViewById(R.id.tv_textotelefone);
        tv_textoemail = findViewById(R.id.tv_textoemail);
        tv_texto_turma_activityalunos = findViewById(R.id.tv_texto_turma_activityalunos);

        Spinner spinner = findViewById(R.id.spinner);

        //Ocultar o Menu de Editor ao iniciar a activity
        et_editarnome.setVisibility(View.GONE);
        et_editarmorada.setVisibility(View.GONE);
        et_editartelefone.setVisibility(View.GONE);
        et_editaremail.setVisibility(View.GONE);


        tv_textonome.setVisibility(View.GONE);
        tv_textomorada.setVisibility(View.GONE);
        tv_textotelefone.setVisibility(View.GONE);
        tv_textoemail.setVisibility(View.GONE);
        tv_texto_turma_activityalunos.setVisibility(View.GONE);

        bt_atualizaraluno.setVisibility(View.GONE);
        bt_cancelareditaraluno.setVisibility(View.GONE);
        bt_removeraluno.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);


        ////////////////////////////////////////////////////////////////////////////////////////////////////

        mViewHolder.rv_alunos = (RecyclerView) findViewById(R.id.rv_alunos);



        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Ao carregar num dos ITENS da recycle view, abre o MENU de EDITOR



        // ------------ RECYCLE VIEW ------------ //

        RecycleView();



                                        ////////////////////////////////////////////////////////////////////////////////////////////////////


        // Botão Cancelar a Edição
        bt_cancelareditaraluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Reaniciar a Activity
                finish();
                startActivity(getIntent());
            }
        });

        // Botão Adicionar Nova Turma
        bt_adicionaraluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AlunosActivity.this, AddAlunoActivity.class);
                startActivity(i);

            }
        });

        Collections.sort(listaAlunos, Turmas.Alunos.OrdenarCrescente);



                                /////////////////////////////////////////////BOTÃO ORDEM CRESCENTE///////////////////////////////////////

        // Botão Ordenação Crescente
        bt_ordenar_aluno_maior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Limpar lista visualmente destacada anteriormente
                listaAlunos.clear();

                // Display/ Layout da Lista
                Cursor c = DataBase.selectAllAlunos();
                c.moveToFirst();

                if (c.getCount() > 0) {
                    do {
                        @SuppressLint("Range") int turma_id = c.getInt(c.getColumnIndex("turma_id"));
                        @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
                        @SuppressLint("Range") String nome = c.getString(c.getColumnIndex("nome")).toString();
                        @SuppressLint("Range") String morada = c.getString(c.getColumnIndex("morada")).toString();
                        @SuppressLint("Range") String email = c.getString(c.getColumnIndex("email")).toString();
                        @SuppressLint("Range") String telefone = c.getString(c.getColumnIndex("telefone")).toString();
                        listaAlunos.add(new Turmas.Alunos(turma_id, id, nome, morada, telefone, email));
                    } while (c.moveToNext());
                }

                //adapter
                AlunosListAdapter alunosListAdapter = new AlunosListAdapter(listaAlunos);
                mViewHolder.rv_alunos.setAdapter(alunosListAdapter);


                /*Collections.sort(listaTurmas, new Comparator<Turmas>(){
                    public int compare(Turmas obj1, Turmas obj2) {
                        // ## Ascending order
                        //return obj2.getAno().compareToIgnoreCase(obj1.getAno()); // To compare string values
                        return Integer.compare(obj1.getAno(), obj2.getAno()); // To compare integer values

                    }});*/
                Collections.sort(listaAlunos, Turmas.Alunos.OrdenarCrescente);

                // ------------ RECYCLE VIEW ------------ //

               RecycleView();


                                /////////////////////////////////////////////BOTÃO ORDEM DECRESCENTE///////////////////////////////////////


        // Botão ordem decrescente
        bt_ordenar_aluno_menor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listaAlunos.clear();

                // Display/ Layout da Lista
                Cursor c = DataBase.selectAllAlunos();
                c.moveToFirst();

                if (c.getCount() > 0) {
                    do {
                        @SuppressLint("Range") int turma_id = c.getInt(c.getColumnIndex("turma_id"));
                        @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
                        @SuppressLint("Range") String nome = c.getString(c.getColumnIndex("nome")).toString();
                        @SuppressLint("Range") String morada = c.getString(c.getColumnIndex("morada")).toString();
                        @SuppressLint("Range") String email = c.getString(c.getColumnIndex("email")).toString();
                        @SuppressLint("Range") String telefone = c.getString(c.getColumnIndex("telefone")).toString();
                        listaAlunos.add(new Turmas.Alunos(turma_id, id, nome, morada, telefone, email));
                    } while (c.moveToNext());
                }

                //adapter
                AlunosListAdapter alunosListAdapter = new AlunosListAdapter(listaAlunos);
                mViewHolder.rv_alunos.setAdapter(alunosListAdapter);


                /*Collections.sort(listaTurmas, new Comparator<Turmas>(){
                    public int compare(Turmas obj1, Turmas obj2) {
                        // ## Ascending order
                        //return obj2.getAno().compareToIgnoreCase(obj1.getAno()); // To compare string values
                        return Integer.compare(obj1.getAno(), obj2.getAno()); // To compare integer values

                    }});*/
                Collections.sort(listaAlunos, Turmas.Alunos.OrdenarDecrescente);


                // ------------ RECYCLE VIEW ------------ //

                RecycleView();
            }
        });

            }




        });

        bt_ordenar_aluno_maior.performClick();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(), "OnItemSelectedListener : "
                + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private static class ViewHolder {

        RecyclerView rv_alunos;
    }






    private void RecycleView() {


        //adapter
        AlunosListAdapter alunosListAdapter = new AlunosListAdapter(listaAlunos);
        mViewHolder.rv_alunos.setAdapter(alunosListAdapter);
        //layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mViewHolder.rv_alunos.setLayoutManager(linearLayoutManager);


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


        Spinner spinner = findViewById(R.id.spinner);



        //Transportar listaTurmas para dentro do spinner

        ArrayAdapter aa = new ArrayAdapter(AlunosActivity.this, android.R.layout.simple_spinner_item, listaTurmas);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                getIdFromTurma = listaTurmas.get(i).getTurma_id();

                //Toast.makeText(AlunosActivity.this, String.valueOf(getIdFromTurma), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast.makeText(MainActivity.this, "Nada selecionado", Toast.LENGTH_SHORT).show();
            }
        });



        // ------------ RECYCLE VIEW ------------ //

        alunosListAdapter.setOnItemClickListener(new AlunosListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                //Fazer os elementos de ediação APARECER
                et_editarnome.setVisibility(View.VISIBLE);
                et_editarmorada.setVisibility(View.VISIBLE);
                et_editartelefone.setVisibility(View.VISIBLE);
                et_editaremail.setVisibility(View.VISIBLE);

                tv_textonome.setVisibility(View.VISIBLE);
                tv_textomorada.setVisibility(View.VISIBLE);
                tv_textoemail.setVisibility(View.VISIBLE);
                tv_textotelefone.setVisibility(View.VISIBLE);

                bt_atualizaraluno.setVisibility(View.VISIBLE);
                bt_cancelareditaraluno.setVisibility(View.VISIBLE);
                bt_removeraluno.setVisibility(View.VISIBLE);
                tv_texto_turma_activityalunos.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);

                // SPINNER SET NA POSIÇÃO EM QUE ESTÁ DEFINIDA NA BASE DE DADOS O ID DA TURMA

                int index = 0;
                for (int i = 0; i < listaTurmas.size(); i ++) {
                    if (listaTurmas.get(i).getTurma_id() == listaAlunos.get(pos).getTurma_id()) {
                        index = i;
                        break;
                    }
                }
                spinner.setSelection(index);


                //Toast.makeText(AlunosActivity.this, String.valueOf(pos), Toast.LENGTH_SHORT).show();

                // Buscar os Valores da Posição

                et_editarnome.setText(String.valueOf(listaAlunos.get(pos).getNome()));
                et_editarmorada.setText(listaAlunos.get(pos).getMorada());
                et_editartelefone.setText(listaAlunos.get(pos).getTelefone());
                et_editaremail.setText(listaAlunos.get(pos).getEmail());
                //Toast.makeText(TurmasActivity.this, String.valueOf(listaTurmas.get(pos).getId()), Toast.LENGTH_SHORT).show();



                // Botão de SUBMETER A EDIÇÃO da TURMA e GRAVAR os Resultados

                bt_atualizaraluno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String value = et_editartelefone.getText().toString();
                        int value1 = value.length();

                        // Validação de input de número de telemovel
                        if (value1 > 0 && value1 <= 8) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_phone_number), Toast.LENGTH_LONG).show();

                        } else {

                            DataBase.updateAluno(getIdFromTurma,String.valueOf(et_editarnome.getText()), et_editarmorada.getText().toString(), et_editaremail.getText().toString(), et_editartelefone.getText().toString(), listaAlunos.get(pos).getId());
                            listaAlunos.get(pos).setNome(String.valueOf(et_editarnome.getText()));
                            listaAlunos.get(pos).setMorada(String.valueOf(et_editarmorada.getText()));
                            listaAlunos.get(pos).setEmail(String.valueOf(et_editaremail.getText()));
                            listaAlunos.get(pos).setTelefone(String.valueOf(et_editartelefone.getText()));
                            listaAlunos.get(pos).setTurma_id(getIdFromTurma);

                            //Toast.makeText(AlunosActivity.this, String.valueOf(getIdFromTurma), Toast.LENGTH_SHORT).show();

                            // Limpar a parte visual da lista apresentada visualmente para não exister o acrescentamento da mesma.
                            listaAlunos.clear();

                            // Display/ Layout da Lista
                            Cursor c = DataBase.selectAllAlunos();
                            c.moveToFirst();

                            if (c.getCount() > 0) {
                                do {
                                    @SuppressLint("Range") int turma_id = c.getInt(c.getColumnIndex("turma_id"));
                                    @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
                                    @SuppressLint("Range") String nome = c.getString(c.getColumnIndex("nome")).toString();
                                    @SuppressLint("Range") String morada = c.getString(c.getColumnIndex("morada")).toString();
                                    @SuppressLint("Range") String email = c.getString(c.getColumnIndex("email")).toString();
                                    @SuppressLint("Range") String telefone = c.getString(c.getColumnIndex("telefone")).toString();
                                    listaAlunos.add(new Turmas.Alunos(turma_id, id, nome, morada, telefone, email));
                                } while (c.moveToNext());
                            }

                            //adapter
                            AlunosListAdapter alunosListAdapter = new AlunosListAdapter(listaAlunos);
                            mViewHolder.rv_alunos.setAdapter(alunosListAdapter);


                            // Menssagem de Atualização dos dados bem-sucedida
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.student_updated), Toast.LENGTH_LONG).show();

                            // Reaniciar a Activity
                            finish();
                            startActivity(getIntent());

                        }

                    }
                });

                // Remover Turma
                bt_removeraluno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Chamar o metodo de remover da class DBHelper
                        DataBase.deleteAluno(listaAlunos.get(pos).getId());

                        // Menssagem de remoção da turma bem-sucedida.
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.student_removed), Toast.LENGTH_LONG).show();

                        // Reaniciar a Activity
                        finish();
                        startActivity(getIntent());
                    }

                });

                // CHAMADA TELEFONICA COM PERMISSAO

                tv_textotelefone.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub

                        if (listaAlunos.get(pos).getTelefone().matches("")) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_phone_number), Toast.LENGTH_LONG).show();
                        } else {


                            // VERIICAR AS PERMISSÕES
                            if (ActivityCompat.checkSelfPermission(AlunosActivity.this,
                                    Manifest.permission.CALL_PHONE) !=
                                    PackageManager.PERMISSION_GRANTED) {

                                ActivityCompat.requestPermissions(AlunosActivity.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                            } else {
                                // ASSIM QUE TIVER A PERMISSÃO É POSSIVEL REALIZAR A CHAMADA
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + listaAlunos.get(pos).getTelefone().trim().toString()));
                                startActivity(callIntent);
                            }
                        }
                    }
                });



                //ABRIR A APLICAÇÃO DE GOOGLE MAPS COM A MORADA INSERIDA PELO UTILIZADOR

                tv_textomorada.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub

                        if (listaAlunos.get(pos).getMorada().matches("")) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_address), Toast.LENGTH_LONG).show();
                        } else {

                            // Create a Uri from an intent string. Use the result to create an Intent.
                            Uri gmmIntentUri =
                                    Uri.parse("geo:0,0?q=" + Uri.encode(listaAlunos.get(pos).getMorada().toString().trim()));

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                            mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                            startActivity(mapIntent);
                        }
                    }
                });




                //ABRE O OUTLOOK OU GMAIL - NO MEU CASO ABRE A APP HOTMAIL/OUTLOOK

                tv_textoemail.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub

                        String email = String.valueOf(listaAlunos.get(pos).getEmail().toString());

                        if (email.matches("") || !email.contains("@")) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_email), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:"));
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{listaAlunos.get(pos).getEmail().trim().toString()});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Contacto Escolar");
                            intent.putExtra(Intent.EXTRA_TEXT, "Enviado a partir de CLASS MANAGER 2022 - Desenvolvido por Oleg Leonidov");
                            startActivity(intent);
                        }

                    }
                });





            }
        });
    }




    @SuppressLint("QueryPermissionsNeeded")
    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:0,0?q=my+street+address")); //lat lng or address query
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public static class MenuPrincipalActivity extends AppCompatActivity {

        Button bt_mostrarturmas, bt_mostraralunos, bt_logout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu_principal);

            bt_logout = findViewById(R.id.bt_logout);
            bt_mostrarturmas = findViewById(R.id.bt_mostrarturmas);
            bt_mostraralunos = findViewById(R.id.bt_mostraralunos);


            //LOGOUT - VOLTAR A PAGINA INCIAL
            bt_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MenuPrincipalActivity.this, MainActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.disconnected), Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            bt_mostrarturmas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MenuPrincipalActivity.this, TurmasActivity.class);
                    startActivity(i);
                }
            });


            bt_mostraralunos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MenuPrincipalActivity.this, AlunosActivity.class);
                    startActivity(i);
                }
            });

        }
    }
}