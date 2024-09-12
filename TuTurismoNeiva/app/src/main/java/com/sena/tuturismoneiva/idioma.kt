package com.sena.tuturismoneiva

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import java.util.Locale

class idioma : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_idioma, container, false)

        // Configura el RadioGroup
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupLanguages)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioEspañol -> (requireActivity() as MainActivity).changeLanguage(Locale("es"))
                R.id.radioIngles -> (requireActivity() as MainActivity).changeLanguage(Locale("en"))
            }
        }

        // Configura el botón para volver
        val btnBack = view.findViewById<Button>(R.id.volverIdioma)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
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