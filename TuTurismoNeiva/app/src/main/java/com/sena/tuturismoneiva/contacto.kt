package com.sena.tuturismoneiva

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class contacto : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_contacto, container, false)

        val btnBack = view.findViewById<Button>(R.id.volverContacto)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val imageButtonCall = view.findViewById<Button>(R.id.btnLlamar)
        imageButtonCall.setOnClickListener {
            val phoneNumber = "tel:3232323591"
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse(phoneNumber)
            }
            startActivity(intent)
        }

        val buttonEmail = view.findViewById<Button>(R.id.btnCorreo)
        buttonEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:tuturismoneiva@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "Asunto")
                putExtra(Intent.EXTRA_TEXT, "¡Hola TuTurismo Neiva!")
            }
            startActivity(Intent.createChooser(emailIntent, "Enviar Email"))
        }

        val buttonInstagram = view.findViewById<Button>(R.id.btnIg)
        buttonInstagram.setOnClickListener {
            val instagramUrl = "https://www.instagram.com/tuturismoneiva?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw=="
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
            startActivity(intent)
        }

        val buttonWhatsApp = view.findViewById<Button>(R.id.btnWhatsApp)
        buttonWhatsApp.setOnClickListener {
            val phoneNumber = "3232323591"
            val message = "¡Hola TuTurismo Neiva!"
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            contacto().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
