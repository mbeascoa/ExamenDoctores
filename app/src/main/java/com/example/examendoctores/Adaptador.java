package com.example.examendoctores;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    private List<Departamentos> listaDepartamentos;
    int posicionseleccionada = 0;
    public Adaptador(List<Departamentos> ListaDepartamento) {
        this.listaDepartamentos= ListaDepartamento;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_datos_cardview,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String numerod= listaDepartamentos.get(position).getNumero();
        holder.tvNumeroDep.setText(numerod);

        String nombred= listaDepartamentos.get(position).getNombre();
        holder.tvNombreDep.setText(nombred);

        String localidadd = listaDepartamentos.get(position).getLocalidad();
        holder.tvLocalidadDep.setText(localidadd);

        holder.cvRelleno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                posicionseleccionada = position;

                if (posicionseleccionada == position) {
                    holder.tvNombreDep.setTextColor(Color.RED);


                } else {
                    holder.tvNombreDep.setTextColor(Color.DKGRAY);
                }
                notifyDataSetChanged();
                //Notificamos cambios para que el contenedr se entere y refresque los datos
                Intent i = new Intent(holder.itemView.getContext(), Detalle_departamento.class);

                i.putExtra("NUMERODEPARTAMENTO", listaDepartamentos.get(position).getNumero());
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount(){
        return listaDepartamentos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNombreDep, tvNumeroDep, tvLocalidadDep;
        private CardView cvRelleno;
        public ViewHolder(View v){
            super(v);
            tvNumeroDep=(TextView) v.findViewById(R.id.tv_Numero_CardView);
            tvNombreDep =(TextView) v.findViewById(R.id.tv_Nombre_CardView);
            tvLocalidadDep = (TextView) v.findViewById(R.id.tv_Localidad_CardView);
            cvRelleno = (CardView) v.findViewById(R.id.card_datos);

        }
    }
}

