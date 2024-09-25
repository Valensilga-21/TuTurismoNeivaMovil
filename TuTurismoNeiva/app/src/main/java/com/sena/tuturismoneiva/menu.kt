package com.sena.tuturismoneiva

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.sena.tuturismoneiva.databinding.ActivityMenuBinding

class menu : AppCompatActivity() {
    private lateinit var binding : ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(inicio())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.inicio -> replaceFragment(inicio())
                R.id.mis_sitios -> replaceFragment(mis_sitios())
                R.id.ajustes -> replaceFragment(ajustes())
                else ->{
                }
            }
            true
        }
    }
    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}