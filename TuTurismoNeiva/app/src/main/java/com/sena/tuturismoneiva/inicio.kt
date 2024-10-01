package com.sena.tuturismoneiva

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.sena.tuturismoneiva.adapter.sitioMonumentoAdapter
import com.sena.tuturismoneiva.config.config
import com.sena.tuturismoneiva.models.SitioMonumento
import java.sql.Date
import java.sql.Time

class inicio : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMonuments)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        fetchMonuments()

        return view
    }

    private fun fetchMonuments() {
        val url = config.urlSitios
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val monumentList = ArrayList<SitioMonumento>()
                for (i in 0 until response.length()) {
                    val monumentJson = response.getJSONObject(i)
                    val monument = SitioMonumento(
                        monumentJson.getString("id"),
                        monumentJson.getString("clasificacionSitioMonumento")[0],
                        monumentJson.getString("nombreSitioMonumento"),
                        monumentJson.getString("ubicacionSitioMonumento"),
                        monumentJson.getString("calificacionSitioMonumento"),
                        monumentJson.getString("direccionSitioMonumento"),
                        monumentJson.getString("descripcionSitioMonumento"),
                        monumentJson.getString("detalladaSitioMonumento"),
                        Time.valueOf(monumentJson.getString("horarioSitioMonumento")),
                        Date.valueOf(monumentJson.getString("fechaCreacionSitioMonumento")),
                        Date.valueOf(monumentJson.getString("fechaModificacionSitioMonumento")),
                        monumentJson.getString("idAutor"),
                        monumentJson.getString("contactoSitioMonumento"),
                        monumentJson.getString("imagen")
                    )
                    monumentList.add(monument)
                }
                recyclerView.adapter = sitioMonumentoAdapter(monumentList, object : sitioMonumentoAdapter.OnMonumentClickListener {
                    override fun onMoreDetailsClick(monument: SitioMonumento) {

                    }
                })
            },
            { error ->

            })

        Volley.newRequestQueue(requireContext()).add(request)
    }
}
