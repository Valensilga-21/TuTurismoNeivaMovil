import android.os.Bundle
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
import com.sena.tuturismoneiva.R
import com.sena.tuturismoneiva.config.config
import org.json.JSONObject

class cambiarContra : Fragment() {

    private lateinit var txtNuevaContra: EditText
    private lateinit var txtConfirmarNuevaContra: EditText
    private lateinit var btnCambiar: Button
    private val apiUrl = config.urlCambiarContra

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cambiar_contra, container, false)

        txtNuevaContra = view.findViewById(R.id.contraNueva)
        txtConfirmarNuevaContra = view.findViewById(R.id.txtConfirmCambiarContra)
        btnCambiar = view.findViewById(R.id.btnCambiar)

        btnCambiar.setOnClickListener {
            cambiarContraseña()
        }

        return view
    }

    private fun cambiarContraseña() {
        val nuevaContra = txtNuevaContra.text.toString().trim()
        val confirmarContra = txtConfirmarNuevaContra.text.toString().trim()

        if (nuevaContra != confirmarContra) {
            Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        val queue = Volley.newRequestQueue(requireContext())
        val requestBody = JSONObject().apply {
            put("newPassword", nuevaContra)
            put("confirmPassword", confirmarContra)
        }

        val token = "tu_token_jwt"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.POST, apiUrl, requestBody,
            { response ->
                Toast.makeText(requireContext(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()
            },
            { error ->
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
