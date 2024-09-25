package com.sena.tuturismoneiva

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment

class sitios : Fragment() {

    private lateinit var viewFlipper: ViewFlipper
    private val handler = Handler()
    private val runnable = object : Runnable {
        override fun run() {
            viewFlipper.showNext()
            handler.postDelayed(this, 2000)
        }
    }

    // Definición de los argumentos
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Manejo de argumentos si es necesario
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sitios, container, false)

        // Inicializa el ViewFlipper
        viewFlipper = view.findViewById(R.id.viewFlipper)

        return view
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000) // Comienza el carrusel al reanudar el fragmento
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable) // Detiene el carrusel al pausar el fragmento
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            sitios().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
