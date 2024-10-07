package com.sena.tuturismoneiva

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import cambiarContra

class confirmCorreo : Fragment() {
    private var param1: String? = null // Aquí guardamos el correo del usuario logueado
    private var param2: String? = null // No se utiliza, pero se puede usar según tu lógica

    // Define las constantes para los parámetros
    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            confirmCorreo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1) // El correo electrónico del usuario logueado
            param2 = it.getString(ARG_PARAM2) // Puede ser utilizado para otro propósito
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño para este fragmento
        val view = inflater.inflate(R.layout.fragment_confirm_correo, container, false)

        // Encontrar los elementos del diseño
        val emailInput: EditText = view.findViewById(R.id.editTextTextEmailAddress)
        val confirmButton: Button = view.findViewById(R.id.btnConfirm)

        confirmButton.setOnClickListener {
            val enteredEmail = emailInput.text.toString()

            if (enteredEmail == param1) {
                cambiarAFragmentoCambiarContra()
            } else {
                Toast.makeText(context, "El correo electrónico no coincide", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun cambiarAFragmentoCambiarContra() {
        val fragmentConfirmCo = cambiarContra()

        val fragmentManager = parentFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragmentConfirmCo)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
