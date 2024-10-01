package com.sena.tuturismoneiva

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sena.tuturismoneiva.config.config
import org.json.JSONException
import org.json.JSONObject


class registrarse : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtContra: EditText
    private lateinit var txtConfirmContra: EditText
    private lateinit var btnRegistrar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        //Volver al inicio
        var btnVolverInicio2: Button =findViewById<Button>(R.id.btnVolverInicio2)
        btnVolverInicio2.setOnClickListener{
            finish()
        }

        val validacionCorreo = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        val validacionNombre = "^[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ ]+$"
        val validacionContra = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"


        txtNombre = findViewById(R.id.txtNombre)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtContra = findViewById(R.id.txtContra)
        txtConfirmContra = findViewById(R.id.txtConfirmContra)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val correo = txtCorreo.text.toString()
            val contra = txtContra.text.toString()
            val confirmContra = txtConfirmContra.text.toString()
            var formularioValido=true

            if (nombre.length !in 3..60) {
                txtNombre.error = "El nombre debe tener más de 2 y menos de 60 caracteres"
                formularioValido=false
            }

            if (!nombre.matches(Regex(validacionNombre))) {
                txtNombre.error = "El nombre no es válido"
                formularioValido=false
            }

            if (correo.length !in 4..100) {
                txtCorreo.error =
                    "El correo electrónico debe tener más de 3 y ser menor o igual a 100 caracteres"
                formularioValido=false
            }
            if (!correo.matches(Regex(validacionCorreo))) {
                txtCorreo.error = "El correo electrónico no es válido"
                formularioValido=false
            }
            if (contra.length !in 8..25) {
                txtContra.error = "La contraseña debe tener al menos 8 caracteres a 25 caracteres"
                formularioValido=false
            }

            if (!contra.matches(Regex(validacionContra))) {
                txtContra.error = "Contraseña no válida"
                formularioValido=false
            }

            if (confirmContra != contra) {
                txtConfirmContra.error = "Las contraseñas no coinciden"
                formularioValido=false
            }

            if (confirmContra.isEmpty()) {
                txtConfirmContra.error = "Este es un campo obligatorio"
                formularioValido=false
            }
            /*
            if (txtNombre.error == null && txtTelefono.error == null && txtCorreo.error == null && txtContra.error == null && txtConfirmContra.error == null) {
                registrarUsuario(nombre, correo, contra, confirmContra)
            }*/
            if(formularioValido) {
                registrarUsuario()
            }
        }
    }

    private fun registrarUsuario() {
        try {
            val parametros = JSONObject()
            parametros.put("nombreCompleto", txtNombre.text.toString())
            parametros.put("correoElectronico", txtCorreo.text.toString())
            parametros.put("contra", txtContra.text.toString())
            parametros.put("coContra", txtConfirmContra.text.toString())

            val request = JsonObjectRequest(
                Request.Method.POST,
                config.urlUsuario + "registro/",
                parametros,
                { response ->
                    try {
                        val token = response.getString("token")
                        val sharedPreferences = getSharedPreferences("MiAppPreferences", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("TOKEN", token)
                        editor.apply()

                        Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, menu::class.java)
                        startActivity(intent)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Error al obtener el token", Toast.LENGTH_LONG).show()
                    }
                },
                { error ->
                    Toast.makeText(this, "Se generó error", Toast.LENGTH_LONG).show()
                }
            )

            val queue = Volley.newRequestQueue(this)
            queue.add(request)

        } catch (error: Exception) {
            Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
        }
    }
}