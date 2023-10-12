package com.example.intellicalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intellicalc.Activities.CategoryActivity
import com.example.intellicalc.databinding.ActivityGuide2Binding

class Guide2 : AppCompatActivity() {

    lateinit var binding: ActivityGuide2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityGuide2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.skipBtn.setOnClickListener {

            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)

        }

        binding.nextBtn.setOnClickListener {

        val intent = Intent(this, Guide3::class.java)
        startActivity(intent)

        }

    }
}