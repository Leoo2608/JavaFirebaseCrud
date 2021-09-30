package com.example.javafirebasecrud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.javafirebasecrud.model.Escuela;
import com.example.javafirebasecrud.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private List<Escuela> data;
    private LayoutInflater inflater;
    private Context context;
    private OnNoteListener mOnNoteListener;

    public ListAdapter(List<Escuela> itemList, Context context, OnNoteListener onNoteListener){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = itemList;
        this.mOnNoteListener = onNoteListener;
    }

    @Override
    public int getItemCount(){ return data.size(); }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(data.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iconImage;
        TextView nombre, facultad;
        OnNoteListener onNoteListener;
        ViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.txt_nom_esc);
            facultad = itemView.findViewById(R.id.txt_facultad);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        void bindData(final Escuela item){
            nombre.setText(item.getNombre());
            facultad.setText(item.getFacultad());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
