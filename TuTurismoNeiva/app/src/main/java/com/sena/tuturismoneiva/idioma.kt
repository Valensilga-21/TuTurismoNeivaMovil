package com.sena.tuturismoneiva

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.util.Locale

class idioma : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_idioma, container, false)

        // Configura el botón para volver
        val btnBack = view.findViewById<Button>(R.id.volverIdioma)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Inicializar SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("configuracion_idioma", Context.MODE_PRIVATE)

        // Configuración del RadioGroup para cambiar el idioma
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupIdioma)
        val idiomaGuardado = sharedPreferences.getString("idioma", "es")

        // Configura el estado del RadioButton según el idioma guardado
        val radioButtonEspañol = view.findViewById<RadioButton>(R.id.radioEspañol)
        val radioButtonIngles = view.findViewById<RadioButton>(R.id.radioIngles)

        radioButtonEspañol.isChecked = idiomaGuardado == "es"
        radioButtonIngles.isChecked = idiomaGuardado == "en"

        // Listener para el RadioGroup
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioEspañol -> cambiarIdioma("es")
                R.id.radioIngles -> cambiarIdioma("en")
            }
        }

        return view
    }

    // Método para cambiar el idioma de la aplicación
    private fun cambiarIdioma(idioma: String) {
        val locale = Locale(idioma)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        // Actualiza la configuración del contexto
        context?.resources?.updateConfiguration(config, context?.resources?.displayMetrics)

        // Guarda el idioma seleccionado en SharedPreferences
        guardarIdioma(idioma)

        // Cambiar al fragmento de editar perfil
        cambiarAFragmentoEditarPerfil()
    }

    // Método para cambiar al fragmento de editar perfil
    private fun cambiarAFragmentoEditarPerfil() {
        val fragmentAjustes = ajustes()
        val fragmentManager = parentFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragmentAjustes)
        transaction.addToBackStack(null) // Para que el usuario pueda regresar
        transaction.commit()
    }

    // Método para guardar el idioma seleccionado en SharedPreferences
    private fun guardarIdioma(idioma: String) {
        with(sharedPreferences.edit()) {
            putString("idioma", idioma)
            apply()  // Guarda de forma asíncrona
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            idioma().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
