package com.sena.tuturismoneiva

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("MiAppPreferences", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        // Aplica el modo oscuro/claro en función de la preferencia guardada
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        aplicarIdiomaGuardado()
        verificarSesion()

        // Agrega el listener para el TextView que abrirá la página web
        val textViewWeb: TextView = findViewById(R.id.btn2)
        textViewWeb.setOnClickListener {
            val url = "http://10.192.66.60:5500/Front-end/html/RegistroEmpresa.html"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Verificar si el usuario está autenticado
    private fun verificarSesion() {
        val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            val intent = Intent(this, menu::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Aplicar configuración de idioma guardado
    private fun aplicarIdiomaGuardado() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("configuracion_idioma", Context.MODE_PRIVATE)
        val idiomaGuardado = sharedPreferences.getString("idioma", "es") // Español por defecto

        val locale = Locale(idiomaGuardado!!)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)
    }

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
