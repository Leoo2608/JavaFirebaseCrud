package com.example.javafirebasecrud.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.javafirebasecrud.R;
import com.example.javafirebasecrud.model.Docente;

import java.util.List;

public class ListDocAdapter extends RecyclerView.Adapter<ListDocAdapter.ViewHolder> {
    private List<Docente> data;
    private LayoutInflater inflater;
    private Context context;
    private OnCardListener mOnCardListener;

    public ListDocAdapter(List<Docente> itemList, Context context, OnCardListener onCardListener){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = itemList;
        this.mOnCardListener = onCardListener;

    }

    @Override
    public int getItemCount(){ return data.size(); }

    @Override
    public ListDocAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,  int viewType){
        View view = inflater.inflate(R.layout.list_element_dc, null);
        return new ListDocAdapter.ViewHolder(view, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(final ListDocAdapter.ViewHolder holder, final int position){
        holder.bindData(data.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView nombres, codigo, dni, telefono, correo;
        OnCardListener onCardListener;
        ViewHolder(View itemView, OnCardListener onCardListener){
            super(itemView);
            imageView = itemView.findViewById(R.id.iconImageView1);
            nombres = itemView.findViewById(R.id.txt_nombres);
            codigo = itemView.findViewById(R.id.txt_codigo);
            dni = itemView.findViewById(R.id.txt_dni);
            telefono = itemView.findViewById(R.id.txt_telefono);
            correo = itemView.findViewById(R.id.txt_correo);
            this.onCardListener = onCardListener;
            itemView.setOnClickListener(this);
        }
        @Override public void onClick(View view ){ onCardListener.onCardClick(getAdapterPosition());}

        void bindData(final Docente item){
            nombres.setText(item.getNombres());
            codigo.setText(item.getCodigo());
            dni.setText(item.getDni());
            telefono.setText(item.getTelefono());
            correo.setText(item.getCorreo());
        }
    }
    public interface OnCardListener{
        void onCardClick(int position);
    }
}
