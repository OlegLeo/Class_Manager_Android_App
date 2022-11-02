package com.example.class_manager.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.class_manager.R;
import com.example.class_manager.model.Turmas;

import kotlin.Unit;

public class TurmasViewHolder extends RecyclerView.ViewHolder {

    private TextView tv_modelo;


    public TurmasViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_modelo=(TextView) itemView.findViewById(R.id.tv_modelo);
        //itemView.setOnClickListener((View.OnClickListener) this);

    }

    public void bindData(Turmas turmas) {
        int ano = (turmas.getAno());
        String descricao = turmas.getDescricao().toString();
        String displayTurma = ano + " - " + descricao;
        tv_modelo.setText(displayTurma);
    }

}
