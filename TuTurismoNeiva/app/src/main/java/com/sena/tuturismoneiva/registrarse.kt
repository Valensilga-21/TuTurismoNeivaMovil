package com.sena.tuturismoneiva

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import com.sena.tuturismoneiva.config.config
import org.json.JSONObject

class registrarse : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtContra: EditText
    private lateinit var txtConfirmContra: EditText
    private lateinit var btnRegistrar: Button
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        txtNombre = findViewById(R.id.txtNombre)
        txtTelefono = findViewById(R.id.txtTelefono)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtContra = findViewById(R.id.txtContra)
        txtConfirmContra = findViewById(R.id.txtConfirmContra)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val telefono = txtTelefono.text.toString()
            val correo = txtCorreo.text.toString()
            val contra = txtContra.text.toString()
            val confirmContra = txtConfirmContra.text.toString()

            if (nombre.length !in 3..60) {
                txtNombre.error = "El nombre debe tener más de 2 y menos de 60 caracteres"
            }
            if (telefono.length !in 10..13) {
                txtTelefono.error = "El teléfono debe tener más de 9 y menos de 15 números"
            }
            if (correo.length !in 4..100) {
                txtCorreo.error = "El correo electrónico debe tener más de 3 y ser menor o igual a 100 caracteres"
            }
            if (contra.length !in 5..25) {
                txtContra.error = "La contraseña debe tener más de 5 y ser menor o igual a 25 caracteres"
            }
            if (confirmContra != contra) {
                txtConfirmContra.error = "Las contraseñas no coinciden"
            }

            if (txtNombre.error == null && txtTelefono.error == null && txtCorreo.error == null && txtContra.error == null && txtConfirmContra.error == null) {
                registrarUsuario(nombre, telefono, correo, contra, confirmContra)
            }
        }

        val btnVolverInicio2: Button = findViewById(R.id.btnVolverInicio2)
        btnVolverInicio2.setOnClickListener {
            finish()
        }
    }

    private fun registrarUsuario(nombre: String, telefono: String, correo: String, contra: String, confirmContra: String) {
        try {
            val parametros = JSONObject().apply {
                put("nombreCompleto", nombre)
                put("telefono", telefono)
                put("correoElectronico", correo)
                put("contra", contra)
                put("coContra", confirmContra)
            }

            val request = JsonObjectRequest(
                if (id.isEmpty()) Request.Method.POST else Request.Method.PUT,
                if (id.isEmpty()) config.urlUsuario else "${config.urlUsuario}$id",
                parametros,
                { response ->
                    Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_LONG).show()
                },
                { error ->
                    Toast.makeText(this, "Se generó error", Toast.LENGTH_LONG).show()
                }
            )

            Volley.newRequestQueue(this).add(request)
        } catch (error: Exception) {
            error.printStackTrace()
            Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}
