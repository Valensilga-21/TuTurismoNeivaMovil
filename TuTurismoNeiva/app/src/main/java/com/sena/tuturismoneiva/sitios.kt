package com.sena.tuturismoneiva

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class sitios : Fragment() {

    private lateinit var star1: ImageButton
    private lateinit var star2: ImageButton
    private lateinit var star3: ImageButton
    private lateinit var star4: ImageButton
    private lateinit var star5: ImageButton
    private lateinit var heart: ImageButton

    private var currentRating = 0
    private var isFavorited = false
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var viewFlipper: ViewFlipper
    private val handler = Handler()
    private val runnable = object : Runnable {
        override fun run() {
            viewFlipper.showNext()
            handler.postDelayed(this, 2000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sitios, container, false)

        viewFlipper = view.findViewById(R.id.viewFlipper)

        star1 = view.findViewById(R.id.star1)
        star2 = view.findViewById(R.id.star2)
        star3 = view.findViewById(R.id.star3)
        star4 = view.findViewById(R.id.star4)
        star5 = view.findViewById(R.id.star5)
        heart = view.findViewById(R.id.heart)

        sharedPreferences = requireContext().getSharedPreferences("CalificacionPref", Context.MODE_PRIVATE)

        currentRating = sharedPreferences.getInt("calificacion_monumento", 0)
        isFavorited = sharedPreferences.getBoolean("es_favorito", false)

        updateStars()
        updateHeart()

        star1.setOnClickListener { setRating(1) }
        star2.setOnClickListener { setRating(2) }
        star3.setOnClickListener { setRating(3) }
        star4.setOnClickListener { setRating(4) }
        star5.setOnClickListener { setRating(5) }

        heart.setOnClickListener { toggleFavorite() }

        return view
    }

    private fun setRating(rating: Int) {
        currentRating = rating
        guardarCalificacionLocal(currentRating)
        updateStars()
    }

    private fun toggleFavorite() {
        isFavorited = !isFavorited
        guardarFavoritoLocal(isFavorited)
        updateHeart()

        val mensaje = if (isFavorited) {
            "Añadido a favoritos"
        } else {
            "Eliminado de favoritos"
        }
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun updateStars() {
        // Reinicia todas las estrellas
        star1.setImageResource(R.drawable.star_svgrepo_com__1_)
        star2.setImageResource(R.drawable.star_svgrepo_com__1_)
        star3.setImageResource(R.drawable.star_svgrepo_com__1_)
        star4.setImageResource(R.drawable.star_svgrepo_com__1_)
        star5.setImageResource(R.drawable.star_svgrepo_com__1_)

        if (currentRating >= 1) star1.setImageResource(R.drawable.star_svgrepo_com__2_)
        if (currentRating >= 2) star2.setImageResource(R.drawable.star_svgrepo_com__2_)
        if (currentRating >= 3) star3.setImageResource(R.drawable.star_svgrepo_com__2_)
        if (currentRating >= 4) star4.setImageResource(R.drawable.star_svgrepo_com__2_)
        if (currentRating >= 5) star5.setImageResource(R.drawable.star_svgrepo_com__2_)
    }

    private fun updateHeart() {
        if (isFavorited) {
            heart.setImageResource(R.drawable.heart_fill_svgrepo_com)
        } else {
            heart.setImageResource(R.drawable.heart_fill_svgrepo_com__1_)
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    private fun guardarCalificacionLocal(calificacion: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("calificacion_monumento", calificacion)
        editor.apply()
    }

    private fun guardarFavoritoLocal(favorito: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("es_favorito", favorito)
        editor.apply()
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ajustes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
