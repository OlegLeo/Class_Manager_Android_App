package com.example.class_manager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.class_manager.data.DBHelper;
import com.example.class_manager.R;
import com.example.class_manager.adapter.TurmasListAdapter;
import com.example.class_manager.model.Turmas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TurmasActivity extends AppCompatActivity {

    Button bt_atualizarturma, bt_cancelareditarturma, bt_removerturma, bt_adicionarturma, bt_ordenar_turma_maior, bt_ordenar_turma_menor;
    EditText et_editarano, et_editardesignacao;
    TextView tv_textodesignacao, tv_textoano, tv_texto_turma_alunosexistentes;
    MainActivity.ViewHolder mViewHolder = new MainActivity.ViewHolder();
    DBHelper DataBase = new DBHelper(this);
    List<Turmas> listaTurmas = new ArrayList<>();
    public Spinner spinner;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turmas);



                                                                        ////////////////////////////////////////////////////////////////////////////////////////////////////
        // Chamar os elementos da activity.xml pelo seu respetivo ID

            // Inputs
        et_editarano = findViewById(R.id.et_editarano);
        et_editardesignacao = findViewById(R.id.et_editardesignacao);

            // Botões
        bt_atualizarturma = findViewById(R.id.bt_atualizarturma);
        bt_removerturma = findViewById(R.id.bt_removerturma);
        bt_cancelareditarturma = findViewById(R.id.bt_cancelareditarturma);
        bt_adicionarturma = findViewById(R.id.bt_adicionarturma);
        bt_ordenar_turma_maior = findViewById(R.id.bt_ordenar_turma_maior);
        bt_ordenar_turma_menor = findViewById(R.id.bt_ordenar_turma_menor);
        tv_texto_turma_alunosexistentes = findViewById(R.id.tv_texto_turma_alunosexistentes);


            // Caixas de Texto
        tv_textodesignacao = findViewById(R.id.tv_textodesignacao);
        tv_textoano = findViewById(R.id.tv_textoano);

        spinner = findViewById(R.id.spinner_alunos_existentes);


        //Ocultar o Menu de Editor ao iniciar a activity
        et_editarano.setVisibility(View.GONE);
        et_editardesignacao.setVisibility(View.GONE);
        bt_atualizarturma.setVisibility(View.GONE);
        tv_textodesignacao.setVisibility(View.GONE);
        tv_textoano.setVisibility(View.GONE);
        bt_cancelareditarturma.setVisibility(View.GONE);
        bt_removerturma.setVisibility(View.GONE);
        tv_texto_turma_alunosexistentes.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);


                                                                          ////////////////////////////////////////////////////////////////////////////////////////////////////

        this.mViewHolder.rv_turmas = (RecyclerView) findViewById(R.id.rv_turmas);

        //adapter
        TurmasListAdapter turmasListAdapter = new TurmasListAdapter(listaTurmas);
        mViewHolder.rv_turmas.setAdapter(turmasListAdapter);
        //layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mViewHolder.rv_turmas.setLayoutManager(linearLayoutManager);

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

                                                                             ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Ao carregar num dos ITENS da recycle view, abre o MENU de EDITOR

        turmasListAdapter.setOnItemClickListener(new TurmasListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                //Fazer os elementos de ediação APARECER
                et_editarano.setVisibility(View.VISIBLE);
                et_editardesignacao.setVisibility(View.VISIBLE);
                bt_atualizarturma.setVisibility(View.VISIBLE);
                tv_textodesignacao.setVisibility(View.VISIBLE);
                tv_textoano.setVisibility(View.VISIBLE);
                bt_cancelareditarturma.setVisibility(View.VISIBLE);
                bt_removerturma.setVisibility(View.VISIBLE);
                tv_texto_turma_alunosexistentes.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);



                // --- SPINNER --- //
                spinnerAlunos(listaTurmas.get(pos).getTurma_id(), pos);
                //Toast.makeText(TurmasActivity.this, String.valueOf(pos), Toast.LENGTH_SHORT).show();

                // Buscar os Valores da Posição

                et_editarano.setText(String.valueOf(listaTurmas.get(pos).getAno()));
                et_editardesignacao.setText(listaTurmas.get(pos).getDescricao());
                //Toast.makeText(TurmasActivity.this, String.valueOf(listaTurmas.get(pos).getId()), Toast.LENGTH_SHORT).show();

                // Botão de SUBMETER A EDIÇÃO da TURMA e GRAVAR os Resultados

                bt_atualizarturma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String value= et_editarano.getText().toString();
                        int value1 = value.length();

                        if( value1 < 1) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_year), Toast.LENGTH_LONG).show();

                        } else {
                            int anoF = Integer.parseInt(value);

                            String designacao = et_editardesignacao.getText().toString();
                            int num = designacao.length();


                            if (TextUtils.isEmpty(et_editardesignacao.getText().toString())
                                    || num != 1
                                    || anoF > 12
                                    || anoF < 1)  {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_data), Toast.LENGTH_LONG).show();

                            } else {


                                DataBase.updateTurma(et_editarano.getText(), et_editardesignacao.getText().toString().toUpperCase(Locale.ROOT), listaTurmas.get(pos).getTurma_id());
                                listaTurmas.get(pos).setAno(Integer.parseInt(String.valueOf(et_editarano.getText())));
                                listaTurmas.get(pos).setDescricao(String.valueOf(et_editardesignacao.getText()));

                                // Limpar a parte visual da lista apresentada visualmente para não exister o acrescentamento da mesma.
                                listaTurmas.clear();

                                // Display/ Layout da Lista
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

                                //adapter
                                TurmasListAdapter turmasListAdapter = new TurmasListAdapter(listaTurmas);
                                mViewHolder.rv_turmas.setAdapter(turmasListAdapter);

                                // Menssagem de Atualização dos dados bem-sucedida
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.class_updated), Toast.LENGTH_LONG).show();

                                // Reaniciar a Activity
                                finish();
                                startActivity(getIntent());
                            }


                    }}
                });

                // Remover Turma
                bt_removerturma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Chamar o metodo de remover da class DBHelper
                        DataBase.deleteTurma(listaTurmas.get(pos).getTurma_id());

                        // Menssagem de remoção da turma bem-sucedida.
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.class_removed), Toast.LENGTH_LONG).show();

                        // Reaniciar a Activity
                        finish();
                        startActivity(getIntent());
                    }

                });
            }
        });




                                                                            ////////////////////////////////////////////////////////////////////////////////////////////////////


        // Botão Cancelar a Edição
        bt_cancelareditarturma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Reaniciar a Activity
                finish();
                startActivity(getIntent());
            }
        });

        // Botão Adicionar Nova Turma
        bt_adicionarturma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TurmasActivity.this, AddTurmaActivity.class);
                startActivity(i);

            }
        });

        Collections.sort(listaTurmas, Turmas.OrdenarCrescente);



                                                                                /////////////////////////////////////////////BOTÃO ORDEM CRESCENTE///////////////////////////////////////

        // Botão Ordenação Crescente
        bt_ordenar_turma_maior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Limpar lista visualmente destacada anteriormente
                listaTurmas.clear();

                // Display/ Layout da Lista
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

                //adapter

                TurmasListAdapter turmasListAdapter = new TurmasListAdapter(listaTurmas);
                mViewHolder.rv_turmas.setAdapter(turmasListAdapter);


                /*Collections.sort(listaTurmas, new Comparator<Turmas>(){
                    public int compare(Turmas obj1, Turmas obj2) {
                        // ## Ascending order
                        //return obj2.getAno().compareToIgnoreCase(obj1.getAno()); // To compare string values
                        return Integer.compare(obj1.getAno(), obj2.getAno()); // To compare integer values

                    }});*/
                Collections.sort(listaTurmas, Turmas.OrdenarCrescente);

                    // ------------ RECYCLE VIEW ------------ //
                turmasListAdapter.setOnItemClickListener(new TurmasListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {

                        //Fazer os elementos de ediação APARECER
                        et_editarano.setVisibility(View.VISIBLE);
                        et_editardesignacao.setVisibility(View.VISIBLE);
                        bt_atualizarturma.setVisibility(View.VISIBLE);
                        tv_textodesignacao.setVisibility(View.VISIBLE);
                        tv_textoano.setVisibility(View.VISIBLE);
                        bt_cancelareditarturma.setVisibility(View.VISIBLE);
                        bt_removerturma.setVisibility(View.VISIBLE);
                        tv_texto_turma_alunosexistentes.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.VISIBLE);




                        // --- SPINNER --- //
                        spinnerAlunos(listaTurmas.get(pos).getTurma_id(), pos);

                        //Toast.makeText(TurmasActivity.this, String.valueOf(pos), Toast.LENGTH_SHORT).show();

                        // Buscar os Valores da Posição

                        et_editarano.setText(String.valueOf(listaTurmas.get(pos).getAno()));
                        et_editardesignacao.setText(listaTurmas.get(pos).getDescricao());
                        //Toast.makeText(TurmasActivity.this, String.valueOf(listaTurmas.get(pos).getId()), Toast.LENGTH_SHORT).show();

                        // Botão de SUBMETER A EDIÇÃO da TURMA e GRAVAR os Resultados

                        bt_atualizarturma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                String value= et_editarano.getText().toString();
                                int value1 = value.length();

                                if( value1 < 1) {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_year), Toast.LENGTH_LONG).show();

                                } else {
                                    int anoF = Integer.parseInt(value);

                                    String designacao = et_editardesignacao.getText().toString();
                                    int num = designacao.length();


                                    if (TextUtils.isEmpty(et_editardesignacao.getText().toString())
                                            || num != 1
                                            || anoF > 12
                                            || anoF < 1) {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_data), Toast.LENGTH_LONG).show();

                                    } else {
                                        DataBase.updateTurma(et_editarano.getText(), et_editardesignacao.getText().toString().toUpperCase(Locale.ROOT), listaTurmas.get(pos).getTurma_id());
                                        listaTurmas.get(pos).setAno(Integer.parseInt(String.valueOf(et_editarano.getText())));
                                        listaTurmas.get(pos).setDescricao(String.valueOf(et_editardesignacao.getText()));

                                        // Limpar a parte visual da lista apresentada visualmente para não exister o acrescentamento da mesma.
                                        listaTurmas.clear();

                                        // Display/ Layout da Lista
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

                                        //adapter
                                        TurmasListAdapter turmasListAdapter = new TurmasListAdapter(listaTurmas);
                                        mViewHolder.rv_turmas.setAdapter(turmasListAdapter);

                                        // Menssagem de Atualização dos dados bem-sucedida
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.class_updated), Toast.LENGTH_LONG).show();

                                        // Reaniciar a Activity
                                        finish();
                                        startActivity(getIntent());

                                    }
                                }

                            }
                        });

                        // Remover Turma
                        bt_removerturma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // Chamar o metodo de remover da class DBHelper
                                DataBase.deleteTurma(listaTurmas.get(pos).getTurma_id());

                                // Menssagem de remoção da turma bem-sucedida.
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.class_removed), Toast.LENGTH_LONG).show();

                                // Reaniciar a Activity
                                finish();
                                startActivity(getIntent());
                            }

                        });
                    }
                });
            }
        });



                                                                    /////////////////////////////////////////////BOTÃO ORDEM DECRESCENTE///////////////////////////////////////


        // Botão ordem decrescente
        bt_ordenar_turma_menor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listaTurmas.clear();

                // Display/ Layout da Lista
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

                //adapter

                TurmasListAdapter turmasListAdapter = new TurmasListAdapter(listaTurmas);
                mViewHolder.rv_turmas.setAdapter(turmasListAdapter);


                /*Collections.sort(listaTurmas, new Comparator<Turmas>(){
                    public int compare(Turmas obj1, Turmas obj2) {
                        // ## Ascending order
                        //return obj2.getAno().compareToIgnoreCase(obj1.getAno()); // To compare string values
                        return Integer.compare(obj1.getAno(), obj2.getAno()); // To compare integer values

                    }});*/
                Collections.sort(listaTurmas, Turmas.OrdenarDecrescente);





                turmasListAdapter.setOnItemClickListener(new TurmasListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {

                        //Fazer os elementos de ediação APARECER
                        et_editarano.setVisibility(View.VISIBLE);
                        et_editardesignacao.setVisibility(View.VISIBLE);
                        bt_atualizarturma.setVisibility(View.VISIBLE);
                        tv_textodesignacao.setVisibility(View.VISIBLE);
                        tv_textoano.setVisibility(View.VISIBLE);
                        bt_cancelareditarturma.setVisibility(View.VISIBLE);
                        bt_removerturma.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.VISIBLE);
                        tv_texto_turma_alunosexistentes.setVisibility(View.VISIBLE);



                        // --- SPINNER --- //
                        spinnerAlunos(listaTurmas.get(pos).getTurma_id(), pos);

                        //Toast.makeText(TurmasActivity.this, String.valueOf(pos), Toast.LENGTH_SHORT).show();

                        // Buscar os Valores da Posição

                        et_editarano.setText(String.valueOf(listaTurmas.get(pos).getAno()));
                        et_editardesignacao.setText(listaTurmas.get(pos).getDescricao());
                        //Toast.makeText(TurmasActivity.this, String.valueOf(listaTurmas.get(pos).getId()), Toast.LENGTH_SHORT).show();

                        // Botão de SUBMETER A EDIÇÃO da TURMA e GRAVAR os Resultados

                        bt_atualizarturma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                String value= et_editarano.getText().toString();
                                int value1 = value.length();

                                if( value1 < 1) {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong_year), Toast.LENGTH_LONG).show();

                                } else {
                                    int anoF = Integer.parseInt(value);

                                    String designacao = et_editardesignacao.getText().toString();
                                    int num = designacao.length();


                                    if (TextUtils.isEmpty(et_editardesignacao.getText().toString())
                                            || num != 1
                                            || anoF > 12
                                            || anoF < 1)  {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_data), Toast.LENGTH_LONG).show();

                                    } else {
                                        DataBase.updateTurma(et_editarano.getText(), et_editardesignacao.getText().toString().toUpperCase(Locale.ROOT), listaTurmas.get(pos).getTurma_id());
                                        listaTurmas.get(pos).setAno(Integer.parseInt(String.valueOf(et_editarano.getText())));
                                        listaTurmas.get(pos).setDescricao(String.valueOf(et_editardesignacao.getText()));

                                        // Limpar a parte visual da lista apresentada visualmente para não exister o acrescentamento da mesma.
                                        listaTurmas.clear();

                                        // Display/ Layout da Lista
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

                                        //adapter
                                        TurmasListAdapter turmasListAdapter = new TurmasListAdapter(listaTurmas);
                                        mViewHolder.rv_turmas.setAdapter(turmasListAdapter);

                                        // Menssagem de Atualização dos dados bem-sucedida
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.class_updated), Toast.LENGTH_LONG).show();

                                        // Reaniciar a Activity
                                        finish();
                                        startActivity(getIntent());

                                    }}

                            }
                        });

                        // Remover Turma
                        bt_removerturma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // Chamar o metodo de remover da class DBHelper
                                DataBase.deleteTurma(listaTurmas.get(pos).getTurma_id());

                                // Menssagem de remoção da turma bem-sucedida.
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.class_removed), Toast.LENGTH_LONG).show();

                                // Reaniciar a Activity
                                finish();
                                startActivity(getIntent());
                            }

                        });
                    }
                });
            }
        });

    }




    private static class ViewHolder {

        RecyclerView rv_turmas;
    }

    private void spinnerAlunos(int idTurma, int pos) {


        // Trazer a listaTurmas para dentro desta ACTIVITY

        List<Turmas.Alunos> listaAlunos = new ArrayList<>();

        spinner = findViewById(R.id.spinner_alunos_existentes);

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
        List<String> AlunosFromTurma = new ArrayList<String>();

        for (int i = 0; i < listaAlunos.size(); i ++) {
            if (listaAlunos.get(i).getTurma_id() == listaTurmas.get(pos).getTurma_id()) {
                AlunosFromTurma.add(listaAlunos.get(i).getNome());
            }
        }
            //Toast.makeText(TurmasActivity.this, String.valueOf(AlunosFromTurma), Toast.LENGTH_SHORT).show();


        //Transportar listaTurmas para dentro do spinner

        ArrayAdapter aa = new ArrayAdapter(TurmasActivity.this, android.R.layout.simple_spinner_item, AlunosFromTurma);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent a = new Intent(TurmasActivity.this, AlunosActivity.class);
                //startActivity(a);
                //getIdFromTurma = listaTurmas.get(i).getTurma_id();
                //Toast.makeText(AlunosActivity.this, String.valueOf(getIdFromTurma), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast.makeText(MainActivity.this, "Nada selecionado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    }

