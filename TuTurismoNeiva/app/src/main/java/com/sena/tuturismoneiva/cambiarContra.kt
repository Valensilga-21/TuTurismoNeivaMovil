import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.sena.tuturismoneiva.R
import com.sena.tuturismoneiva.config.config
import com.sena.tuturismoneiva.iniciarSesion
import com.sena.tuturismoneiva.models.CambioContrasenaRequest
import org.json.JSONObject

class cambiarContra : Fragment() {

    private lateinit var txtContraActual: EditText
    private lateinit var txtNuevaContra: EditText
    private lateinit var txtConfirmNuevaContra: EditText
    private lateinit var btnCambiar: Button
    private lateinit var sharedPreferences: SharedPreferences
    private val apiUrl = config.urlCambiarContra

    private lateinit var txtErrorActContra: TextView
    private lateinit var txtErrorNuevaContra: TextView
    private lateinit var txtErrorConfirmNuevaContra: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cambiar_contra, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)

        txtContraActual = view.findViewById(R.id.contraAct)
        txtNuevaContra = view.findViewById(R.id.contraNueva)
        txtConfirmNuevaContra = view.findViewById(R.id.contraConfirmar)
        btnCambiar = view.findViewById(R.id.btnCambiar)

        txtErrorActContra=view.findViewById(R.id.errorActualContra)
        txtErrorNuevaContra=view.findViewById(R.id.errorNuevaContra)
        txtErrorConfirmNuevaContra=view.findViewById(R.id.errorConfirmNuevaContra)

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
            txtErrorNuevaContra.text = getString(R.string.actnopuedeserigualnueva)
            txtErrorNuevaContra.visibility = View.VISIBLE
        }

        if (nuevaContrasena != confirmarContrasena) {
            txtErrorConfirmNuevaContra.text = getString(R.string.nocinciden)
            txtErrorConfirmNuevaContra.visibility = View.VISIBLE
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
                sharedPreferences.edit().putString("PASSWORD", nuevaContrasena).apply()
                Toast.makeText(requireContext(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(requireContext(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), iniciarSesion::class.java)
                startActivity(intent)
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
