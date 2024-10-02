package com.sena.tuturismoneiva

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.sena.tuturismoneiva.config.config
import com.sena.tuturismoneiva.models.usuario

class ajustes : Fragment() {

    private lateinit var txtNombrePerfil: TextView
    private lateinit var txtCorreoPerfil: TextView
    private lateinit var imageViewPerfil: ImageView

    private lateinit var switchDarkMode: Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_ajustes, container, false)

        imageViewPerfil = view.findViewById(R.id.fotoPerfilAjustes)

        loadImageFromInternalStorage()

        txtNombrePerfil = view.findViewById(R.id.txtNombreUsuario)
        txtCorreoPerfil = view.findViewById(R.id.txtCorreoUsuario)

        // Configura el Switch
        switchDarkMode = view.findViewById(R.id.switchDarkMode)
        val sharedPreferences = requireActivity().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        switchDarkMode.isChecked = isDarkMode

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("dark_mode", isChecked)
            editor.apply()
            requireActivity().recreate()
        }

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

        val btnLogout = view.findViewById<Button>(R.id.btnCerrrarSesion)
        btnLogout.setOnClickListener {
            cerrarSesion()
        }

        mostrarPerfil()
        return view
    }

    fun cerrarSesion() {
        val sharedPreferences = requireActivity().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun loadImageFromInternalStorage() {
        try {
            val filename = "profile_image.png"
            val fileInputStream = requireActivity().openFileInput(filename)
            val bitmap = BitmapFactory.decodeStream(fileInputStream)
            imageViewPerfil.setImageBitmap(bitmap)
            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "No se encontró la imagen guardada", Toast.LENGTH_SHORT).show()
        }
    }

    fun mostrarPerfil() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", "")

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val request = object : JsonObjectRequest(
            Request.Method.GET,
            config.urlProfile + "profile/",
            null,
            Response.Listener { response ->
                val gson = Gson()
                val usuario: usuario = gson.fromJson(response.toString(), usuario::class.java)
                txtNombrePerfil.text = usuario.nombreCompleto
                txtCorreoPerfil.text = usuario.correoElectronico
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    context,
                    "Error al consultar",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        val queue = Volley.newRequestQueue(context)
        queue.add(request)
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
        val fragmentNosotros = nosotros()

        val fragmentManager = parentFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragmentNosotros)

        transaction.addToBackStack(null)

        transaction.commit()
    }

    private fun cambiarAFragmentoPerfil() {
        val fragmentPerfil = editarPerfil()

        val fragmentManager = parentFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragmentPerfil)

        transaction.addToBackStack(null)

        transaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ajustes()
    }
}
