package com.sena.tuturismoneiva

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.sena.tuturismoneiva.adapter.sitioMonumentoAdapter
import com.sena.tuturismoneiva.config.config
import com.sena.tuturismoneiva.models.SitioMonumento
import org.json.JSONException
import org.json.JSONObject
import java.sql.Date
import java.sql.Time

class inicio : Fragment(), sitioMonumentoAdapter.OnMonumentClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sitiosList: MutableList<SitioMonumento>
    private lateinit var sitioAdapter: sitioMonumentoAdapter
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        // Inicializar el RecyclerView y el adaptador
        recyclerView = view.findViewById(R.id.recyclerViewMonuments)
        sitiosList = mutableListOf()

        // Configurar el RecyclerView con un GridLayout de 2 columnas
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Inicializar el adaptador
        sitioAdapter = sitioMonumentoAdapter(sitiosList, this)
        recyclerView.adapter = sitioAdapter

        // Inicializar la cola de peticiones de Volley
        requestQueue = Volley.newRequestQueue(requireContext())

        // Cargar los datos desde la API
        //cargarDatosSitios()

        return view
    }

    /*
    private fun cargarDatosSitios() {
        val url = config.urlSitios

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    sitiosList.clear()

                    for (i in 0 until response.length()) {
                        val sitioObject: JSONObject = response.getJSONObject(i)
                        val id = sitioObject.getString("idSitioMonumento")
                        val nombre = sitioObject.getString("nombreSitioMonumento")
                        val direccion = sitioObject.getString("direccionSitioMonumento")
                        val imagen = sitioObject.getString("imagen")

                        val sitio = SitioMonumento(id, nombre, direccion, imagen)
                        sitiosList.add(sitio)
                    }

                    sitioAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace() // Manejo de errores
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

*/

    override fun onMoreDetailsClick(monument: SitioMonumento) {
        // Aquí puedes manejar el evento de clic para mostrar más detalles del sitio
    }
}

