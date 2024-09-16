package com.sena.tuturismoneiva

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [inicio.newInstance] factory method to
 * create an instance of this fragment.
 */
class inicio : Fragment() {
    // TODO: Rename and change types of parameters
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

        // Encuentra el botón en el layout del fragmento
        val btnSitios = view.findViewById<Button>(R.id.btnSitios)
        btnSitios.setOnClickListener {
            // Cambia al fragmento de contacto
            cambiarAFragmentoSitios()
        }

        val btnMonumentos = view.findViewById<Button>(R.id.btnMonumentos)
        btnMonumentos.setOnClickListener {
            // Cambia al fragmento de contacto
            cambiarAFragmentoMonumentos()
        }

        return view
    }

    private fun cambiarAFragmentoSitios() {
        // Crea una nueva instancia del fragmento de contacto
        val fragmentSitios = sitios()

        // Obtén el FragmentManager
        val fragmentManager = parentFragmentManager

        // Inicia una transacción de fragmentos
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Reemplaza el fragmento actual con el nuevo fragmento
        transaction.replace(R.id.frameSitios, fragmentSitios)

        // Agrega la transacción a la pila de retroceso (opcional)
        transaction.addToBackStack(null)

        // Confirma la transacción
        transaction.commit()
    }

    private fun cambiarAFragmentoMonumentos() {
        // Crea una nueva instancia del fragmento de contacto
        val fragmentMonumentos = monumentos()

        // Obtén el FragmentManager
        val fragmentManager = parentFragmentManager

        // Inicia una transacción de fragmentos
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Reemplaza el fragmento actual con el nuevo fragmento
        transaction.replace(R.id.frameSitios, fragmentMonumentos)

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