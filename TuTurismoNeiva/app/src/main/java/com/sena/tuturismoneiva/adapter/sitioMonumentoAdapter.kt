package com.sena.tuturismoneiva.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sena.tuturismoneiva.R
import com.sena.tuturismoneiva.models.SitioMonumento

class sitioMonumentoAdapter(
    private val monumentList: List<SitioMonumento>,
    private val listener: OnMonumentClickListener
) : RecyclerView.Adapter<sitioMonumentoAdapter.MonumentViewHolder>() {

    interface OnMonumentClickListener {
        fun onMoreDetailsClick(monument: SitioMonumento)
    }

    inner class MonumentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val monumentImage: ImageView = view.findViewById(R.id.imgSitio)
        val monumentName: TextView = view.findViewById(R.id.txtNombreSitio)
        val monumentDireccion: TextView = view.findViewById(R.id.txtDireccionSitio)
        val moreDetailsButton: View = view.findViewById(R.id.masDetalles)

        fun bind(monument: SitioMonumento) {
            monumentName.text = monument.nombreSitioMonumento
            monumentDireccion.text = monument.direccionSitioMonumento
            Glide.with(itemView.context)
                .load(monument.imagen)
                .into(monumentImage)

            moreDetailsButton.setOnClickListener {
                listener.onMoreDetailsClick(monument)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonumentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_sitios, parent, false)
        return MonumentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonumentViewHolder, position: Int) {
        holder.bind(monumentList[position])
    }

    override fun getItemCount(): Int = monumentList.size
}
