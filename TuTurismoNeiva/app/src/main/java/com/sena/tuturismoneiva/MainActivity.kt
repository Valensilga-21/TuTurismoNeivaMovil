package com.sena.tuturismoneiva

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplicar el idioma guardado antes de cargar el layout
        aplicarIdiomaGuardado()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Manejo de padding para ajustar a los insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
