package com.example.pruebapaises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.pruebapaises.R.layout.ly_item;

public class AdaptadorPaises extends ArrayAdapter<Paises> {

    public AdaptadorPaises(Context context, ArrayList<Paises> datos) {
        super(context, ly_item, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(ly_item, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.txtTitulo);
        lblTitulo.setText(getItem(position).getNombre());

        ImageView imageView = (ImageView)item.findViewById(R.id.imageView);
        Glide.with(this.getContext()).load(getItem(position).getImagen()).into(imageView);
        return(item);
    }
}
