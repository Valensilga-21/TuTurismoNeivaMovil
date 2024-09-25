package com.sena.tuturismoneiva

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sena.tuturismoneiva.config.config.Companion.urlUsuario
import org.json.JSONException
import org.json.JSONObject

class iniciarSesion : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVolverInicio: Button =findViewById<Button>(R.id.volverInicio)
        btnVolverInicio.setOnClickListener{
            finish()
        }

        // Inicializar Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this)

        sharedPreferences = getSharedPreferences("MiAppPreferences", MODE_PRIVATE)

        // Inicializar EditTexts y Button
        usernameEditText = findViewById(R.id.txtConfirmCorreo)
        passwordEditText = findViewById(R.id.txtConfirmContraseña)
        loginButton = findViewById(R.id.btnIniciarSesion)

        // Configurar el listener del botón de inicio de sesión
        loginButton.setOnClickListener {
            login()
        }
    }

    fun olvidarContraseña(view: View) {
        val intent = Intent(application, olvidar_contra::class.java)
        startActivity(intent)
    }

    fun registrarse(view: View) {
        val intent = Intent(application, registrarse()::class.java)
        startActivity(intent)
    }

    private fun login() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val url = urlUsuario + "login/"

        val jsonBody = JSONObject().apply {
            put("correoElectronico", username)
            put("contra", password)
        }

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            Response.Listener { response ->
                try {
                    val token = response.getString("token")
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                    val editor = sharedPreferences.edit()
                    editor.putString("TOKEN", token)
                    editor.apply()

                    val intent = Intent(this, menu::class.java)
                    startActivity(intent)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
        ) {}

        requestQueue.add(jsonObjectRequest)
    }
}
