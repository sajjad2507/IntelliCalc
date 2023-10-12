package com.example.intellicalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intellicalc.databinding.ActivityGuide1Binding

class Guide1 : AppCompatActivity() {

    lateinit var binding: ActivityGuide1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuide1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.skipBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        binding.nextBtn.setOnClickListener {

            val intent = Intent(this, Guide2::class.java)
            startActivity(intent)

        }

    }
}