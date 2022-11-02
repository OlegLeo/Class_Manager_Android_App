package com.example.class_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.class_manager.R;
import com.example.class_manager.model.Turmas;

import java.util.List;

public class AlunosListAdapter extends RecyclerView.Adapter<AlunosListAdapter.AlunosViewHolder>{


    //ItemClickListener itemClickListener;
    private AlunosListAdapter.OnItemClickListener listener;
    private List<Turmas.Alunos> listaAlunos;



    public AlunosListAdapter(List<Turmas.Alunos> lista ) {
        listaAlunos = lista;
    }

    public interface ListItemClickListener{
        void onItemClick(int position);
    }
    @NonNull
    @Override
    public AlunosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View alunosView = inflater.inflate(R.layout.row_alunos_list, parent, false);
        return new AlunosViewHolder(alunosView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunosViewHolder holder, int position) {
        Turmas.Alunos alunos = listaAlunos.get(position);
        holder.bindData(alunos);
    }

    @Override
    public int getItemCount() {
        return listaAlunos.size();
    }
    public  interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(AlunosListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }



    class AlunosViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_row_alunos;


        public AlunosViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_row_alunos=(TextView) itemView.findViewById(R.id.tv_row_alunos);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && listener !=  null) {
                        listener.onItemClick(pos);
                    }
                }
            });

        }




        public void bindData(Turmas.Alunos alunos) {
           // int ano = turmas.getAno();
            int id = alunos.getId();
            String nome = alunos.getNome().toString();
            String morada = alunos.getMorada().toString();
            String email = alunos.getEmail().toString();
            String telefone = alunos.getTelefone().toString();
            String displayAluno = nome;
            tv_row_alunos.setText(displayAluno);
        }

    }





}


