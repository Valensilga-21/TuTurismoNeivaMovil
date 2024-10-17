package com.sena.tuturismoneiva.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sena.tuturismoneiva.R
import com.sena.tuturismoneiva.models.SitioMonumento

class SitioAdapter(
    private val contexto: Context,
    private val espacioList: MutableList<SitioMonumento>,
    private val onItemClick: (SitioMonumento) -> Unit
) : RecyclerView.Adapter<SitioAdapter.SitioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SitioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sitios, parent, false)
        return SitioViewHolder(view)
    }

    override fun onBindViewHolder(holder: SitioViewHolder, position: Int) {
        val espacio = espacioList[position]
        holder.txtNombreSitio.text = espacio.nombreSitioMonumento
        holder.txtDireccionSitio.text = espacio.direccionSitioMonumento
        /*
        Glide.with(contexto)
            .load(espacio.imagen_base)
            .override(100, 100) // Ajustar el tamaño
            .fitCenter() // Ajustar la imagen al tamaño del ImageView
            .placeholder(R.drawable.descripcion)
            .error(R.drawable.delete_remove_uncheck_svgrepo_com)
            .into(holder.imgSitio)*/

        holder.itemView.setOnClickListener {
            onItemClick(espacio)
        }
    }

    override fun getItemCount(): Int {
        return espacioList.size
    }

    class SitioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreSitio: TextView = itemView.findViewById(R.id.txtNombreSitio)
        val txtDireccionSitio: TextView = itemView.findViewById(R.id.txtDireccionSitio)
        val imgSitio: ImageView = itemView.findViewById(R.id.imgSitio)
    }
}



