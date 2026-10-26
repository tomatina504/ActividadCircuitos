package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AccionesAdapter(private val listaAcciones: List<AccionModel>) :
    RecyclerView.Adapter<AccionesAdapter.AccionViewHolder>() {

    class AccionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTituloAccion)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFechaAccion)
        val tvHora: TextView = itemView.findViewById(R.id.tvHoraAccion)
        val tvUsuario: TextView = itemView.findViewById(R.id.tvUsuarioAccion)
        val tvAccion: TextView = itemView.findViewById(R.id.tvDetalleAccion)
        val tvDistancia: TextView = itemView.findViewById(R.id.tvDistanciaAccion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_accion, parent, false)
        return AccionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccionViewHolder, position: Int) {
        val accion = listaAcciones[position]
        holder.tvTitulo.text = accion.titulo
        holder.tvFecha.text = "Fecha: ${accion.fecha}"
        holder.tvHora.text = "Hora: ${accion.hora}"
        holder.tvUsuario.text = "Usuario: ${accion.usuario}"
        holder.tvAccion.text = "Acción: ${accion.accion}"
        holder.tvDistancia.text = "Distancia: ${accion.distancia}"
    }

    override fun getItemCount(): Int = listaAcciones.size
}