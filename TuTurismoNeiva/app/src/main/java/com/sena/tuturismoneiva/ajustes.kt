package com.sena.tuturismoneiva

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class ajustes : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Obtén los parámetros si es necesario
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_ajustes, container, false)

        // Encuentra el botón en el layout del fragmento
        val btnContacto = view.findViewById<Button>(R.id.btnContacto)
        btnContacto.setOnClickListener {
            // Cambia al fragmento de contacto
            cambiarAFragmentoContacto()
        }

        val btnContacto2 = view.findViewById<Button>(R.id.btnContacto2)
        btnContacto2.setOnClickListener {
            // Cambia al fragmento de contacto
            cambiarAFragmentoContacto()
        }

        val btnMision = view.findViewById<Button>(R.id.btnNosotros)
        btnMision.setOnClickListener {
            // Cambia al fragmento de contacto
            cambiarAFragmentoNosotros()
        }

        val btnMision2 = view.findViewById<Button>(R.id.btnNosotros2)
        btnMision2.setOnClickListener {
            // Cambia al fragmento de contacto
            cambiarAFragmentoNosotros()
        }

        return view
    }

    private fun cambiarAFragmentoContacto() {
        // Crea una nueva instancia del fragmento de contacto
        val fragmentContacto = contacto()

        // Obtén el FragmentManager
        val fragmentManager = parentFragmentManager

        // Inicia una transacción de fragmentos
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Reemplaza el fragmento actual con el nuevo fragmento
        transaction.replace(R.id.fragment_container, fragmentContacto)

        // Agrega la transacción a la pila de retroceso (opcional)
        transaction.addToBackStack(null)

        // Confirma la transacción
        transaction.commit()
    }

    private fun cambiarAFragmentoNosotros() {
        // Crea una nueva instancia del fragmento de contacto
        val fragmentNosotros = nosotros()

        // Obtén el FragmentManager
        val fragmentManager = parentFragmentManager

        // Inicia una transacción de fragmentos
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Reemplaza el fragmento actual con el nuevo fragmento
        transaction.replace(R.id.fragment_container, fragmentNosotros)

        // Agrega la transacción a la pila de retroceso (opcional)
        transaction.addToBackStack(null)

        // Confirma la transacción
        transaction.commit()
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ajustes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
