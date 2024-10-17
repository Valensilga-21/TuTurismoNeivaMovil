package com.sena.tuturismoneiva

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.sena.tuturismoneiva.adapter.SitioAdapter
import com.sena.tuturismoneiva.config.config
import com.sena.tuturismoneiva.models.SitioMonumento
import org.json.JSONException

class inicio : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sitioAdapter: SitioAdapter
    private var sitioList = mutableListOf<SitioMonumento>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMonuments)

        sitioAdapter = SitioAdapter(requireContext(), sitioList) { sitio ->
            val intent = Intent(requireContext(), inicio::class.java).apply {
                putExtra("nombreSitioMonumento", sitio.nombreSitioMonumento)
                putExtra("direccionSitioMonumento", sitio.direccionSitioMonumento)
            }
            startActivity(intent)
        }

        recyclerView.adapter = sitioAdapter

        // Cambia LinearLayoutManager a GridLayoutManager
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columnas

        consultarAPI()

        return view
    }

    private fun consultarAPI() {
        val url = config.urlSitios
        val queue = Volley.newRequestQueue(requireContext())

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    sitioList.clear()
                    for (i in 0 until response.length()) {
                        val espacioJson = response.getJSONObject(i)

                        // Manejo de excepciones para cada campo
                        val nombreSitio = espacioJson.optString("nombreSitioMonumento", "Nombre no disponible")
                        val direccionSitio = espacioJson.optString("direccionSitioMonumento", "Dirección no disponible")
                        //val imagen_base = espacioJson.optString("imagen_base", "Imagen no disponible")

                        val espacio = SitioMonumento(
                            nombreSitioMonumento = nombreSitio,
                            direccionSitioMonumento = direccionSitio,
                            //imagen_base= imagen_base
                        )
                        sitioList.add(espacio)
                    }
                    sitioAdapter.notifyDataSetChanged()
                    Toast.makeText(requireContext(), "Datos cargados exitosamente!", Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                error.printStackTrace()
                Toast.makeText(requireContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonArrayRequest)
    }
}
