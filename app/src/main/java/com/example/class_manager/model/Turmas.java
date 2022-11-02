package com.example.class_manager.model;

import java.util.Comparator;

public class Turmas {
    private int turma_id;
    private int ano;
    private String descricao;



    public Turmas(int turma_id, int ano, String descricao) {
        this.turma_id = turma_id;
        this.ano = ano;
        this.descricao = descricao;


    }



    public int getTurma_id() {
        return turma_id;
    }
    public void setId(int turma_id) {
        this.turma_id = turma_id;
    }


    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public static Comparator<Turmas> OrdenarCrescente = new Comparator<Turmas>() {
        @Override
        public int compare(Turmas obj1, Turmas obj2) {
            // ## Ascending order
            //return obj2.getAno().compareToIgnoreCase(obj1.getAno()); // To compare string values
            return Integer.compare(obj1.getAno(), obj2.getAno()); // To compare integer values
        }
    };

    public static Comparator<Turmas> OrdenarDecrescente = new Comparator<Turmas>() {
        @Override
        public int compare(Turmas obj1, Turmas obj2) {
            // ## Ascending order
            //return obj2.getAno().compareToIgnoreCase(obj1.getAno()); // To compare string values
            return Integer.compare(obj2.getAno(), obj1.getAno()); // To compare integer values

        }
    };

    @Override
    public String toString() {
        return  ano + "  -  " + descricao;
    }

    public static class Alunos {

        private int turma_id;
        private int id;
        private String nome;
        private String morada;
        private String telefone;
        private String email;


        public Alunos(int turma_id, int id, String nome, String morada, String telefone, String email) {
            this.turma_id = turma_id;
            this.id = id;
            this.nome = nome;
            this.morada = morada;
            this.telefone = telefone;
            this.email = email;

        }


        public int getTurma_id() {
            return turma_id;
        }

        public void setTurma_id(int turma_id) {
            this.turma_id = turma_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getMorada() {
            return morada;
        }

        public void setMorada(String morada) {
            this.morada = morada;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }



        public static Comparator<Alunos> OrdenarCrescente = new Comparator<Alunos>() {
            @Override
            public int compare(Alunos obj1, Alunos obj2) {
                // ## Ascending order
                return obj1.getNome().compareToIgnoreCase(obj2.getNome()); // To compare string values
               // return Integer.compare(obj1.getNome(), obj2.getNome()); // To compare integer values

            }
        };
        public static Comparator<Alunos> OrdenarDecrescente = new Comparator<Alunos>() {
            @Override
            public int compare(Alunos obj1, Alunos obj2) {
                // ## Ascending order
                return obj2.getNome().compareToIgnoreCase(obj1.getNome()); // To compare string values
                // return Integer.compare(obj1.getNome(), obj2.getNome()); // To compare integer values

            }
        };

        @Override
        public String toString() {
            return  nome;
        }
    }
}
