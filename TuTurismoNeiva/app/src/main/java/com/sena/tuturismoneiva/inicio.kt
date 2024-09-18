package com.sena.tuturismoneiva

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class inicio : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        // Inicializa los botones
        val btnCambioSitios = view.findViewById<Button>(R.id.btnSitios)
        val btnCambioMonumentos = view.findViewById<Button>(R.id.btnMonumentos)

        btnCambioSitios.setOnClickListener {
            cambioFragment(1)
        }

        btnCambioMonumentos.setOnClickListener {
            cambioFragment(2)
        }

        return view
    }

    private fun cambioFragment(position: Int) {
        val fragment: Fragment = when (position) {
            1 -> sitios()
            2 -> monumentos()
            else -> sitios()
        }

        // Usar parentFragmentManager en lugar de supportFragmentManager
        val fragmentManager = parentFragmentManager

        // Crear la transacción del fragmento
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            inicio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
