package com.example.intellicalc.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intellicalc.Adapters.CategoryAdapter
import com.example.intellicalc.DataModel.Category
import com.example.intellicalc.Guide1
import com.example.intellicalc.R
import com.example.intellicalc.databinding.ActivityCategoryBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        // Save a string to SharedPreferences
        val sharedPreferences = getSharedPreferences("Check", Context.MODE_PRIVATE)


        val onBoardChecker = sharedPreferences.getString("check", "").toString().trim()
        if (onBoardChecker != "checked") {

            val editor = sharedPreferences.edit()
            editor.putString("check", "checked")
            editor.apply()

            val intent = Intent(this, Guide1::class.java)
            startActivity(intent)

        }

        MobileAds.initialize(this) {}

        showBanner()
//        loadIntertialAds()

        val categoryList = listOf(
            Category("Standard", R.drawable.standard),
            Category("Discount", R.drawable.discount),
            Category("Percentage", R.drawable.percentage),
            Category("Tip", R.drawable.tip),
            Category("Unit Price", R.drawable.unitprice),
            Category("Fuel", R.drawable.fuel)
        )

        val categoryRecyclerView: RecyclerView = findViewById(R.id.categoryRcv)
        categoryRecyclerView.layoutManager = GridLayoutManager(this, 2)

        val categoryAdapter = CategoryAdapter(categoryList, this)
        categoryRecyclerView.adapter = categoryAdapter

    }

    private fun showBanner() {
        mAdView = binding.adView

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}