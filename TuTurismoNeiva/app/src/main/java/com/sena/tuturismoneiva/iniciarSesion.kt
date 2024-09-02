package com.sena.tuturismoneiva

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class iniciarSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var btnVolverInicio: Button =findViewById<Button>(R.id.voverInicio)
        btnVolverInicio.setOnClickListener{
            finish()
        }

        /*
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)

        val btnCorreo = findViewById<EditText>(R.id.txtConfirmCorreo)
        val btnContraseña = findViewById<EditText>(R.id.txtConfirmContraseña)

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase

        btnIniciarSesion.setOnClickListener{
            val correo = btnCorreo.text.toString()
            val contraseña = btnContraseña.text.toString()

            val cursor = db.rawQuery("SELECT * FROM Usuario WHERE correoElectronico = ? AND contra = ?", arrayOf(correo, contraseña))

            if (cursor.moveToFirst()) {
                val intent = Intent(this, menu::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Correo electrónico o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }*/
    }

    fun iniciarSesion(view: View){
        var intent = Intent(application, registrarse::class.java)
        startActivity(intent)
    }

    fun olvidarContraseña(view: View){
        var intent = Intent(application, olvidar_contra::class.java)
        startActivity(intent)
    }

    fun ingresarInicio(view: View){
        var intent = Intent(application, menu::class.java)
        startActivity((intent))
    }
}