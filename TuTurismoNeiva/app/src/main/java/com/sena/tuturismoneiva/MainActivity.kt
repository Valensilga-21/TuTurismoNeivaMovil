package com.sena.tuturismoneiva

import android.content.Intent
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
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun iniciarSesion(view: View){
        var intent = Intent(application, iniciarSesion::class.java)
        startActivity(intent)
    }

    fun registrarse(view: View){
        var intent = Intent(application, registrarse::class.java)
        startActivity(intent)
    }

    fun invitado(view: View){
        var intent = Intent(application, invitado::class.java)
        startActivity(intent)
    }

    // Add the language change functionality
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guarda datos aquí
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentView(R.layout.activity_main) // Reemplaza con tu diseño de actividad
        // Inicializa vistas y restaura su estado aquí
    }

    fun changeLanguage(locale: Locale) {
        val config = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
    }
}