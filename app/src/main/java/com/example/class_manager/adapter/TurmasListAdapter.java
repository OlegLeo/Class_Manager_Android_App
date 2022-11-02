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

public class TurmasListAdapter extends RecyclerView.Adapter<TurmasListAdapter.TurmasViewHolder> {

    //ItemClickListener itemClickListener;
    private OnItemClickListener listener;
    private List<Turmas> listaTurmas;



    public TurmasListAdapter(List<Turmas> lista ) {
        listaTurmas = lista;
    }

    public interface ListItemClickListener{
        void onItemClick(int position);
    }
    @NonNull
    @Override
    public TurmasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View turmasView = inflater.inflate(R.layout.row_turmas_list, parent, false);
        return new TurmasViewHolder(turmasView);
    }

    @Override
    public void onBindViewHolder(@NonNull TurmasViewHolder holder, int position) {
        Turmas turmas = listaTurmas.get(position);
        holder.bindData(turmas);
    }

    @Override
    public int getItemCount() {
        return listaTurmas.size();
    }
    public  interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(TurmasListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }



    class TurmasViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_modelo;


        public TurmasViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_modelo=(TextView) itemView.findViewById(R.id.tv_modelo);
            //itemView.setOnClickListener((View.OnClickListener) this);

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




        public void bindData(Turmas turmas) {
            int ano = turmas.getAno();
            String descricao = turmas.getDescricao().toString();
            String displayTurma = ano + " - " + descricao;
            tv_modelo.setText(displayTurma);
        }

    }





}
