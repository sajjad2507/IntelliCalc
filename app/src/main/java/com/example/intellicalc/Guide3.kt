package com.example.intellicalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intellicalc.Activities.CategoryActivity
import com.example.intellicalc.databinding.ActivityGuide3Binding

class Guide3 : AppCompatActivity() {

    lateinit var binding: ActivityGuide3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide3)


        binding = ActivityGuide3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.skipBtn.setOnClickListener {

            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)

        }

        binding.nextBtn.setOnClickListener {

            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)

        }

    }
}