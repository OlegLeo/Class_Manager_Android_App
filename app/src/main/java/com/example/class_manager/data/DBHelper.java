package com.example.class_manager.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper<yourclass> extends SQLiteOpenHelper {

    private static int versao = 1;
    private static String DataBase = "DataBase.db";
    SQLiteDatabase db;
    //private static final String DATABASE_PATH = "/data/data/com.example.class_manager/databases";

    String[] sql = {

            "CREATE TABLE Turmas (turma_id INTEGER PRIMARY KEY AUTOINCREMENT, ano INTEGER NOT NULL, descricao TEXT NOT NULL);",
            "CREATE TABLE Alunos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, morada TEXT NOT NULL, telefone INTEGER NOT NULL, email TEXT NOT NULL UNIQUE, turma_id INTEGER, CONSTRAINT fk_Turmas FOREIGN KEY (turma_id) REFERENCES Turmas(turma_id));",


            "CREATE TABLE Utilizadores (utilizador TEXT PRIMARY KEY, password TEXT);",
            "INSERT INTO Turmas (turma_id, ano, descricao) VALUES (1, 12, 'A');",
            "INSERT INTO Turmas (turma_id, ano, descricao) VALUES (2, 11, 'B');",
            "INSERT INTO Turmas (turma_id, ano, descricao) VALUES (3, 10, 'C');",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (1, 'José Almeida', 'Rua Gil Vicente 222, Porto', '932123123', 'aaaa@test.com', 1);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (2, 'Beatriz Antunes', 'Rua Salgueiro, Porto', '932123123', 'bbbbb@test.com', 1);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (3, 'Joaquim Torres', 'Rua Serpa Pinto Viseu', '932123123', 'bbbb1b@test.com', 1);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (4, 'Sonia Matos', 'Rua da Eira , Porto', '932123123', 'bbb2bb@test.com', 1);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (5, 'Francisco Soares', 'Rua Gil Vicente 222, Porto', '932123123', 'bbbb3b@test.com', 2);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (6, 'António Costa', 'Rua Gil Vicente 222, Porto', '932123123', 'bbbb4b@test.com', 2);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (7, 'Fernando Laurentino', 'Rua Gil Vicente 222, Porto', '932123123', 'bbb5bb@test.com', 2);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (8, 'Hugo Claro', 'Rua João de Cima 123, Coimbra', '932123123', 'bbb6bb@test.com', 2);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (9, 'João Almeida', 'Avenida de baixo, Lisboa', '932123123', 'bbbb7b@test.com', 2);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (10, 'Pedro Salgueiro', 'Avenida do cimo, Lisboa', '932123123', 'bbbb8b@test.com', 2);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (11, 'Joana Eira', 'Avenida Augusto, Porto', '932123123', 'bbb11bb@test.com', 3);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (12, 'Francisca Almeida', 'Rua do alem 123, Porto', '932123123', 'bb1b22bb@test.com', 3);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (13, 'Aluno Test 1', 'Rua Camões, Porto', '932123123', 'bbb33bb@test.com', 3);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (14, 'Aluno Test 2', 'Rua Camões, Porto', '932123123', 'bbb332bb@test.com', 3);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (15, 'Aluno Test 3', 'Rua Camões, Porto', '932123123', 'bbb33b3b@test.com', 3);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (16, 'Aluno Test 4', 'Rua Camões, Porto', '932123123', 'bbb334bb@test.com', 3);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (17, 'Aluno Test 5', 'Rua Camões, Porto', '932123123', 'bbb335bb@test.com', 3);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (18, 'Aluno Test 6', 'Rua Camões, Porto', '932123123', 'bbb336bb@test.com', 3);",
            "INSERT INTO Alunos (id, nome, morada, telefone, email, turma_id) VALUES (19, 'Aluno Test 7', 'Rua Camões, Porto', '932123123', 'bbb337bb@test.com', 3);",
            "INSERT INTO Utilizadores (utilizador, password) VALUES ('admin', '123');",
            "INSERT INTO Utilizadores (utilizador, password) VALUES ('diretor', '123');",
            "INSERT INTO Utilizadores (utilizador, password) VALUES ('subdiretor', '123');",

    };

    public DBHelper(Context context) {

        super(context, DataBase, null, versao);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < sql.length; i++) {
            db.execSQL(sql[i]);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        versao++;
        db.execSQL("DROP TABLE IF EXISTS Turmas;");
        db.execSQL("DROP TABLE IF EXISTS Utilizadores;");
        db.execSQL("DROP TABLE IF EXISTS Alunos;");
        onCreate(db);
    }



    public Cursor selectAllTurmas() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM Turmas", null,null);
    }

    public Cursor selectAllAlunos() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM Alunos", null,null);
    }

    public Cursor selectAllUtilizadores() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM Utilizadores", null,null);
    }







    public int updateTurma(Editable ano, String designacao, int turma_id) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ano", String.valueOf(ano));
        cv.put("descricao",designacao);
        return db.update("Turmas", cv, "turma_id = ?", new String[]{String.valueOf(turma_id)});

    }
    public int updateAluno(int turma_id, String nome, String morada, String email, String telefone, int id) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("turma_id", String.valueOf(turma_id));
        cv.put("nome",nome);
        cv.put("morada",morada);
        cv.put("email",email);
        cv.put("telefone",telefone);
        return db.update("Alunos", cv, "id = ?", new String[]{String.valueOf(id)});

    }

    public int deleteTurma(int id) {

        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Turmas","turma_id = ?",new String[]{String.valueOf(id)});
    }

    public int deleteAluno(int id) {

        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Alunos","id = ?",new String[]{String.valueOf(id)});
    }

    public void insertTurma(Editable ano, String designacao) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put("Ano", String.valueOf(ano));
        values.put("descricao", designacao);

        // after adding all values we are passing
        // content values to our table.
        db.insert("Turmas", null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    public void insertAluno( int turma_id, String nome, String morada, String email, String telefone) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put("nome", nome);
        values.put("morada", morada);
        values.put("email", email);
        values.put("telefone", telefone);
        values.put("turma_id", turma_id);

        // after adding all values we are passing
        // content values to our table.
        db.insert("Alunos", null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

}

