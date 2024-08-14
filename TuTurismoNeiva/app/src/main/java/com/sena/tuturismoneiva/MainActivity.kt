package com.sena.tuturismoneiva

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
}