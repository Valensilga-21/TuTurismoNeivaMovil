    package com.sena.tuturismoneiva

    import android.content.Intent
    import android.os.Bundle
    import android.view.View
    import android.widget.Button
    import android.widget.EditText
    import android.widget.TextView
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

        private lateinit var txtErrorNombre: TextView
        private lateinit var txtErrorCorreo: TextView
        private lateinit var txtErrorContra: TextView
        private lateinit var txtErrorConfirmContra: TextView


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

            txtErrorNombre=findViewById(R.id.errorNombre)
            txtErrorCorreo=findViewById(R.id.errorCorreo)
            txtErrorContra=findViewById(R.id.errorContra)
            txtErrorConfirmContra=findViewById(R.id.errorConfirmContra)

            btnRegistrar.setOnClickListener {
                val nombre = txtNombre.text.toString()
                val correo = txtCorreo.text.toString()
                val contra = txtContra.text.toString()
                val confirmContra = txtConfirmContra.text.toString()
                var formularioValido=true

                formularioValido = validarCampo(nombre, validacionNombre, "El nombre no es válido", txtErrorNombre) && formularioValido
                formularioValido = validarCampo(correo, validacionCorreo, "El correo electrónico no es válido", txtErrorCorreo) && formularioValido

                if (contra.length !in 8..25) {
                    txtErrorContra.text = "La contraseña debe tener entre 8 y 25 caracteres."
                    txtErrorContra.visibility = View.VISIBLE
                    formularioValido = false
                } else if (!contra.matches(Regex(validacionContra))) {
                    txtErrorContra.text = "Contraseña no válida."
                    txtErrorContra.visibility = View.VISIBLE
                    formularioValido = false
                } else {
                    txtErrorContra.visibility = View.INVISIBLE
                }

                if (confirmContra != contra) {
                    txtErrorConfirmContra.text = "Las contraseñas no coinciden."
                    txtErrorConfirmContra.visibility = View.VISIBLE
                    formularioValido = false
                } else if (confirmContra.isEmpty()) {
                    txtErrorConfirmContra.text = "Este es un campo obligatorio."
                    txtErrorConfirmContra.visibility = View.VISIBLE
                    formularioValido = false
                } else {
                    txtErrorConfirmContra.visibility = View.INVISIBLE
                }

                if (formularioValido) {
                    registrarUsuario()
                }
            }
        }
        private fun validarCampo(campo: String, regex: String, mensajeError: String, errorTextView: TextView): Boolean {
            return when {
                campo.length !in 3..59 -> {
                    errorTextView.text = "El campo debe tener entre 3 y 59 caracteres."
                    errorTextView.visibility = View.VISIBLE
                    false
                }
                !campo.matches(Regex(regex)) -> {
                    errorTextView.text = mensajeError
                    errorTextView.visibility = View.VISIBLE
                    false
                }
                else -> {
                    errorTextView.visibility = View.INVISIBLE
                    true
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