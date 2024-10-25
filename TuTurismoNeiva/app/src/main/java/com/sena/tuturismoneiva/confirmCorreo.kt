import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sena.tuturismoneiva.R

class confirmCorreo : Fragment() {

    private var emailUsuario: String? = null // El correo del usuario que inició sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            emailUsuario = it.getString("emailUsuario") // Recibir el correo desde el perfil
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_confirm_correo, container, false)

        val etConfirmEmail = view.findViewById<EditText>(R.id.txtEmail)
        val btnConfirmEmail = view.findViewById<Button>(R.id.btnPassword)

        // Al hacer clic en el botón de confirmar
        btnConfirmEmail.setOnClickListener {
            val emailIngresado = etConfirmEmail.text.toString().trim()

            if (emailIngresado.equals(emailUsuario?.trim(), ignoreCase = true)) {
                cambiarAFragmentoCambiarContra()
            } else {
                Toast.makeText(requireContext(), "El correo no coincide", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun cambiarAFragmentoCambiarContra() {
        val fragmentCambiarContra = cambiarContra()

        val fragmentManager = parentFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragmentCambiarContra)

        transaction.addToBackStack(null)

        transaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(emailUsuario: String) =
            confirmCorreo().apply {
                arguments = Bundle().apply {
                    putString("emailUsuario", emailUsuario)
                }
            }
    }
}