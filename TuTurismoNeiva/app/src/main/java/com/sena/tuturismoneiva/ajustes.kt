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
            cambiarAFragmentoContacto()
        }

        val btnContacto2 = view.findViewById<Button>(R.id.btnContacto2)
        btnContacto2.setOnClickListener {
            cambiarAFragmentoContacto()
        }

        val btnMision = view.findViewById<Button>(R.id.btnNosotros)
        btnMision.setOnClickListener {
            cambiarAFragmentoNosotros()
        }

        val btnMision2 = view.findViewById<Button>(R.id.btnNosotros2)
        btnMision2.setOnClickListener {
            cambiarAFragmentoNosotros()
        }

        val btnPerfil = view.findViewById<Button>(R.id.btnPerfil)
        btnPerfil.setOnClickListener {
            cambiarAFragmentoPerfil()
        }

        val btnIdioma = view.findViewById<Button>(R.id.btnIdioma)
        btnIdioma.setOnClickListener {
            cambiarAFragmentoIdioma()
        }

        val btnIdioma2 = view.findViewById<Button>(R.id.btnIdioma2)
        btnIdioma2.setOnClickListener {
            cambiarAFragmentoIdioma()
        }

        return view
    }

    private fun cambiarAFragmentoIdioma() {
        val fragmentIdioma = idioma()

        val fragmentManager = parentFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragmentIdioma)

        transaction.addToBackStack(null)

        transaction.commit()
    }

    private fun cambiarAFragmentoContacto() {
        val fragmentContacto = contacto()

        val fragmentManager = parentFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragmentContacto)

        transaction.addToBackStack(null)

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

    private fun cambiarAFragmentoPerfil() {
        // Crea una nueva instancia del fragmento de contacto
        val fragmentPerfil = editarPerfil()

        // Obtén el FragmentManager
        val fragmentManager = parentFragmentManager

        // Inicia una transacción de fragmentos
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Reemplaza el fragmento actual con el nuevo fragmento
        transaction.replace(R.id.fragment_container, fragmentPerfil)

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
