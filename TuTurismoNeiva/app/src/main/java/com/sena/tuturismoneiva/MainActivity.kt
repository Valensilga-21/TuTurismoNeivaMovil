package com.sena.tuturismoneiva

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("MiAppPreferences", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        setTheme(if (isDarkMode) R.style.Theme_App_Dark else R.style.Theme_App_Light)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        aplicarIdiomaGuardado()

        verificarSesion()

        // Manejo de padding para ajustar a los insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    class TuActividad : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val button2: Button = findViewById(R.id.btn2)

            button2.setOnClickListener {
                val url = "https://www.sena.edu.co/es-co/Paginas/default.aspx"
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(intent)
            }
        }
    }

    // Función para verificar si el usuario ha iniciado sesión
    private fun verificarSesion() {
        val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            // Redirigir al menú principal si el usuario ya está autenticado
            val intent = Intent(this, menu::class.java)
            startActivity(intent)
            finish()  // Cierra la actividad actual para evitar que el usuario regrese al login
        }
    }

    // Función para cambiar de idioma y aplicar la configuración
    private fun aplicarIdiomaGuardado() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("configuracion_idioma", Context.MODE_PRIVATE)
        val idiomaGuardado = sharedPreferences.getString("idioma", "es") // Por defecto español

        val locale = Locale(idiomaGuardado!!)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)
    }

    // Funciones para los botones de la UI
    fun iniciarSesion(view: View) {
        val intent = Intent(application, iniciarSesion::class.java)
        startActivity(intent)
    }

    fun registrarse(view: View) {
        val intent = Intent(application, registrarse::class.java)
        startActivity(intent)
    }

    fun invitado(view: View) {
        val intent = Intent(application, invitado::class.java)
        startActivity(intent)
    }
}
