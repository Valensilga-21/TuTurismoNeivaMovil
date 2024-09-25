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
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

class editarPerfil : Fragment() {

    private val PICK_IMAGE = 100
    private lateinit var imageViewPerfil: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_perfil, container, false)

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
