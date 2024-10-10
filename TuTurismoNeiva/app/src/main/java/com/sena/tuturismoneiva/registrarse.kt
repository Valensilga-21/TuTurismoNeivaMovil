    package com.sena.tuturismoneiva

    import android.content.Intent
    import android.os.Bundle
    import android.view.View
    import android.view.ViewGroup
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

                formularioValido = validarCampo(nombre, validacionNombre, getString(R.string.nombre_no_valido), txtErrorNombre) && formularioValido
                formularioValido = validarCampo(correo, validacionCorreo, getString(R.string.correo_no_valido), txtErrorCorreo) && formularioValido

                if (contra.length !in 8..25) {
                    txtErrorContra.text = getString(R.string.contra_longitud)
                    txtErrorContra.visibility = View.VISIBLE
                    formularioValido = false
                } else if (!contra.matches(Regex(validacionContra))) {
                    txtErrorContra.text = getString(R.string.contra_no_valida)
                    txtErrorContra.visibility = View.VISIBLE
                    formularioValido = false
                } else {
                    txtErrorContra.visibility = View.INVISIBLE
                }

                if (confirmContra != contra) {
                    txtErrorConfirmContra.text = getString(R.string.contrasenas_no_coinciden)
                    txtErrorConfirmContra.visibility = View.VISIBLE
                    formularioValido = false
                } else if (confirmContra.isEmpty()) {
                    txtErrorConfirmContra.text = getString(R.string.campo_obligatorio)
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
                    errorTextView.text = getString(R.string.longitud_invalida)
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

                            val alertaView = layoutInflater.inflate(R.layout.alertaregistrarse, null)

                            val rootView = findViewById<View>(android.R.id.content) as ViewGroup
                            rootView.addView(alertaView)

                            android.os.Handler().postDelayed({
                                rootView.removeView(alertaView)

                                val intent = Intent(this, menu::class.java)
                                startActivity(intent)
                                finish()

                            }, 2000) // 2000 milisegundos = 2 segundos
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(this, "Error al obtener el token", Toast.LENGTH_LONG).show()
                        }
                    },
                    { error ->
                        error.networkResponse?.let {
                            if (it.statusCode == 409) {
                                val alertaView = layoutInflater.inflate(R.layout.alertaerrorcorreo, null)

                                val rootView = findViewById<View>(android.R.id.content) as ViewGroup
                                rootView.addView(alertaView)

                                android.os.Handler().postDelayed({
                                    rootView.removeView(alertaView)

                                }, 2000)
                            } else {
                                // Otros errores
                                val alertaView = layoutInflater.inflate(R.layout.alertaerrorregistro, null)

                                val rootView = findViewById<View>(android.R.id.content) as ViewGroup
                                rootView.addView(alertaView)

                                android.os.Handler().postDelayed({
                                    rootView.removeView(alertaView)

                                }, 2000)
                            }
                        }
                    }
                )

                val queue = Volley.newRequestQueue(this)
                queue.add(request)

            } catch (error: Exception) {
                Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
            }
        }
    }