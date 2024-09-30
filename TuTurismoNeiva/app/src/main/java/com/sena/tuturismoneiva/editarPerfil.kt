package com.sena.tuturismoneiva

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.sena.tuturismoneiva.config.config
import com.sena.tuturismoneiva.models.usuario
import org.json.JSONObject

class editarPerfil : Fragment() {

    private val PICK_IMAGE = 100
    private lateinit var imageViewPerfil: ImageView

    private lateinit var txtNombreCompleto:EditText
    private lateinit var txtCorreoElectronico: EditText
    var idUsuario: String = ""

    private lateinit var btnGuardarCambios:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_perfil, container, false)

        txtNombreCompleto = view.findViewById(R.id.txtNombreCompleto)
        txtCorreoElectronico = view.findViewById(R.id.txtCorreoElectronico)

        mostrarPerfil()

        btnGuardarCambios = view.findViewById(R.id.btnGuardarCambios)
        btnGuardarCambios.setOnClickListener {
            editarPerfil()
        }

        // Configura el botón para volver
        val btnBack = view.findViewById<Button>(R.id.volverPerfil)
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Configura el botón para seleccionar la foto
        imageViewPerfil = view.findViewById(R.id.fotoPerfil)
        val btnCamara = view.findViewById<Button>(R.id.btnCamara)
        btnCamara.setOnClickListener {
            selectImage()
        }

        // Configura el botón para eliminar la foto
        val btnEliminarFoto = view.findViewById<Button>(R.id.btnEliminarFoto)
        btnEliminarFoto.setOnClickListener {
            deleteImage()
        }

        // Cargar la imagen guardada al iniciar el fragmento
        loadImageFromInternalStorage()

        val btnCambiarContra = view.findViewById<Button>(R.id.btnCambiarContra)
        btnCambiarContra.setOnClickListener {
            cambiarAFragmentoCambiarContra()
        }

        return view
    }

    fun mostrarPerfil(){
        val sharedPreferences = requireActivity().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("TOKEN", "")

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val request = object : JsonObjectRequest(
            Request.Method.GET,
            config.urlProfile + "profile/",
            null,
            Response.Listener { response ->
                val gson = Gson()
                val usuario: usuario = gson.fromJson(response.toString(), usuario::class.java)
                txtNombreCompleto.setText(usuario.nombreCompleto)
                txtCorreoElectronico.setText(usuario.correoElectronico)
                idUsuario = usuario.idUsuario

            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    context,
                    "Error al consultar",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        val queue = Volley.newRequestQueue(context)
        queue.add(request)
    }

    /*
    fun editarPerfil() {
        try {
            var parametros = JSONObject()
            parametros.put("nombreCompleto", txtNombreCompleto.text.toString())
            parametros.put("correoElectronico", txtCorreoElectronico.text.toString())

            var request = JsonObjectRequest(
                Request.Method.PUT,
                config.urlEditarProfile + idUsuario,
                parametros,
                { response ->
                    Toast.makeText(context, "Se actualizó correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                { error ->
                    Toast.makeText(context, "Se generó error al actualizar",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
            var queue = Volley.newRequestQueue(context)
            queue.add(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/
    private fun cambiarAFragmentoCambiarContra() {
        val fragmentCambiarContra = cambiarContra()

        val fragmentManager = parentFragmentManager

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragmentCambiarContra)

        transaction.addToBackStack(null)

        transaction.commit()
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageViewPerfil.setImageURI(imageUri)

            // Guardar la imagen en almacenamiento interno
            imageUri?.let { saveImageToInternalStorage(it) }
        } else {
            Toast.makeText(context, "Error al seleccionar la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImageToInternalStorage(imageUri: Uri) {
        try {
            val inputStream = requireActivity().contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            val filename = "profile_image.png"
            val outputStream = requireActivity().openFileOutput(filename, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()

            Toast.makeText(context, "Imagen guardada exitosamente", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImageFromInternalStorage() {
        try {
            val filename = "profile_image.png"
            val fileInputStream = requireActivity().openFileInput(filename)
            val bitmap = BitmapFactory.decodeStream(fileInputStream)
            imageViewPerfil.setImageBitmap(bitmap)
            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "No se encontró la imagen guardada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteImage() {
        try {
            val filename = "profile_image.png"
            requireActivity().deleteFile(filename)
            imageViewPerfil.setImageResource(0) // Limpiar la imagen del ImageView
            Toast.makeText(context, "Imagen eliminada exitosamente", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al eliminar la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = editarPerfil()
    }
}
