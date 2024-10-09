    import android.content.Context
    import android.content.SharedPreferences
    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import androidx.fragment.app.Fragment
    import com.android.volley.Request
    import com.android.volley.toolbox.JsonObjectRequest
    import com.android.volley.toolbox.Volley
    import com.google.gson.Gson
    import com.sena.tuturismoneiva.R
    import com.sena.tuturismoneiva.config.config
    import com.sena.tuturismoneiva.models.CambioContrasenaRequest
    import org.json.JSONObject

    class cambiarContra : Fragment() {

        private lateinit var txtContraActual: EditText
        private lateinit var txtNuevaContra: EditText
        private lateinit var txtConfirmNuevaContra: EditText
        private lateinit var btnCambiar: Button
        private lateinit var sharedPreferences: SharedPreferences
        private val apiUrl = config.urlCambiarContra

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_cambiar_contra, container, false)

            sharedPreferences = requireContext().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)

            txtContraActual = view.findViewById(R.id.contraAct)
            txtNuevaContra = view.findViewById(R.id.contraNueva)
            txtConfirmNuevaContra=view.findViewById(R.id.contraConfirmar)
            btnCambiar = view.findViewById(R.id.btnCambiar)

            btnCambiar.setOnClickListener {
                cambiarContraseña()
            }

            return view
        }

        private fun cambiarContraseña() {
            val antiguaContrasena = txtContraActual.text.toString().trim()
            val nuevaContrasena = txtNuevaContra.text.toString().trim()
            val confirmarContrasena = txtConfirmNuevaContra.text.toString().trim()

            if (antiguaContrasena.isEmpty() || nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return
            }

            if (antiguaContrasena == nuevaContrasena) {
                Toast.makeText(requireContext(), "La contraseña actual no puede ser igual a la nueva", Toast.LENGTH_SHORT).show()
                return
            }

            if (nuevaContrasena != confirmarContrasena) {
                Toast.makeText(requireContext(), "La nueva contraseña y la confirmación no coinciden", Toast.LENGTH_SHORT).show()
                return
            }

            val cambioContrasenaRequest = CambioContrasenaRequest(antiguaContrasena, nuevaContrasena, confirmarContrasena)

            val queue = Volley.newRequestQueue(requireContext())
            val gson = Gson()
            val jsonStr = gson.toJson(cambioContrasenaRequest)
            val requestBody = JSONObject(jsonStr)

            val token = sharedPreferences.getString("TOKEN", "")

            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.PUT, apiUrl, requestBody,
                { response ->
                    Log.d("Response", response.toString())
                    // Actualiza la contraseña en la caché del cliente
                    sharedPreferences.edit().putString("PASSWORD", nuevaContrasena).apply()
                    Toast.makeText(requireContext(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()
                },
                { error ->
                    Log.e("Error", error.toString())
                    Toast.makeText(requireContext(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Authorization"] = "Bearer $token"
                    headers["Content-Type"] = "application/json"
                    return headers
                }
            }

            queue.add(jsonObjectRequest)
        }
    }
