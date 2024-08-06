package com.sena.tuturismoneiva

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sena.tuturismoneiva.config.config
import org.json.JSONObject
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [registrarseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class registrarseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var txtNombre: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtContra: EditText
    private lateinit var txtConfirmContra: EditText
    private lateinit var btnRegistrar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    fun registrarUsuario() {
        //MANEJO DE EXCEPCIONES
        try {
            var parametros = JSONObject()
            parametros.put("nombreCompleto", txtNombre.text.toString())
            parametros.put("telefono", txtTelefono.text.toString())
            parametros.put("correoElectronico", txtCorreo.text.toString())
            parametros.put("contra", txtContra.text.toString())
            parametros.put("coContra", txtConfirmContra.text.toString())

            var request = JsonObjectRequest(
                Request.Method.POST,
                config.urlUsuario,
                parametros,
                { response ->
                    Toast.makeText(
                        context, "Se guardó correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                { error ->
                    Toast.makeText(
                        context, "Se generó error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )

            var queue = Volley.newRequestQueue(context)
            queue.add(request)
        } catch (error: Exception) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_registrarse, container, false)
        txtNombre=view.findViewById(R.id.txtNombre)
        txtTelefono=view.findViewById(R.id.txtTelefono)
        txtCorreo=view.findViewById(R.id.txtCorreo)
        txtContra=view.findViewById(R.id.txtContra)
        txtConfirmContra=view.findViewById(R.id.txtConfirmContra)
        btnRegistrar.setOnClickListener{
            registrarUsuario()
        }
        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment registrarseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            registrarseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }

            }

    }
}