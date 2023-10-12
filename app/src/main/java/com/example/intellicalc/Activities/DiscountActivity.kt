package com.example.intellicalc.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.example.intellicalc.R
import com.example.intellicalc.databinding.ActivityDiscountBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat

class DiscountActivity : AppCompatActivity() {

    lateinit var binding: ActivityDiscountBinding
    lateinit var mAdView: AdView

    // Define a variable to keep track of the EditText in focus
    private var editTextInFocus: EditText? = null
    private var isCalculating = false
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)

        binding = ActivityDiscountBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        MobileAds.initialize(this) {}

//        showBanner()
        loadIntertialAds()
        showIntertialAds()


//        binding.addedTax.showSoftInputOnFocus = false
        binding.orignalPrice.showSoftInputOnFocus = false
        binding.discountedPercent.showSoftInputOnFocus = false
//        binding.savedAmount.showSoftInputOnFocus = false
//        binding.finalPrice.showSoftInputOnFocus = false

//        binding.addedTax.isCursorVisible = true
        binding.orignalPrice.isCursorVisible = true
        binding.discountedPercent.isCursorVisible = true
//        binding.savedAmount.isCursorVisible = true
//        binding.finalPrice.isCursorVisible = true

//
//        binding.addedTax.setOnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                editTextInFocus = v as EditText
//            }
//        }

        binding.orignalPrice.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                editTextInFocus = v as EditText
            }
        }

        binding.discountedPercent.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                editTextInFocus = v as EditText
            }
        }

//        binding.savedAmount.setOnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                editTextInFocus = v as EditText
//            }
//        }

//        binding.finalPrice.setOnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                editTextInFocus = v as EditText
//            }
//        }

        binding.allClear.setOnClickListener {
//            binding.addedTax.setText("")
            binding.orignalPrice.setText("")
            binding.discountedPercent.setText("")
            binding.savedAmount.setText("")
            binding.finalPrice.setText("")
        }

        binding.back.setOnClickListener {

            removeFromEditTextInFocus()
        }

        binding.zero.setOnClickListener {

            addToEditTextInFocus("0")

        }
        binding.doubleZero.setOnClickListener {

            addToEditTextInFocus("00")

        }
        binding.one.setOnClickListener {

            addToEditTextInFocus("1")

        }
        binding.two.setOnClickListener {


            addToEditTextInFocus("2")

        }
        binding.three.setOnClickListener {

            addToEditTextInFocus("3")

        }
        binding.four.setOnClickListener {

            addToEditTextInFocus("4")

        }
        binding.five.setOnClickListener {

            addToEditTextInFocus("5")

        }
        binding.six.setOnClickListener {

            addToEditTextInFocus("6")

        }
        binding.seven.setOnClickListener {

            addToEditTextInFocus("7")

        }
        binding.eight.setOnClickListener {

            addToEditTextInFocus("8")

        }
        binding.nine.setOnClickListener {

            addToEditTextInFocus("9")

        }
        binding.dot.setOnClickListener {

            addToEditTextInFocus(".")

        }

        binding.category.setOnClickListener {

            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)

        }

        binding.equal.setOnClickListener {

            showResult()

        }

    }

    private fun showResult() {

        // Check if both originalPrice and finalPrice are not empty
        if (binding.orignalPrice.text.isNotEmpty() && binding.discountedPercent.text.isNotEmpty()) {
            try {
                // Get the values from the EditTexts
                val originalPriceStr = binding.orignalPrice.text.toString()
                val discountPercentStr = binding.discountedPercent.text.toString()

                // Convert the values to double
                val originalPrice = originalPriceStr.toDoubleOrNull() ?: 0.0
                val discountPercent = discountPercentStr.toDoubleOrNull() ?: 0.0

                // Calculate the saved amount and discounted percent
                val finalValue = originalPrice - (originalPrice / 100) * discountPercent
                val savedAmountValue = originalPrice - finalValue

                // Set the values to the respective EditTexts
                binding.savedAmount.setText(savedAmountValue.toString())
                binding.finalPrice.setText(finalValue.toString())

            } catch (e: Exception) {
                // Show Error Message or handle the exception as per your requirements
                Toast.makeText(
                    this,
                    "Error calculating saved amount and discounted percent",
                    Toast.LENGTH_SHORT
                ).show()
            }
//            }

        }

    }

    private fun addToEditTextInFocus(value: String) {
        // Check if there is an EditText in focus
        editTextInFocus?.let { editText ->
            // Add the value to the EditText that is currently in focus
            val currentValue = editText.text.toString()
            val newValue = currentValue + value
            editText.setText(newValue)
        }
    }

    private fun removeFromEditTextInFocus() {
        editTextInFocus?.let { editText ->
            val currentValue = editText.text.toString().dropLast(1)
            val newValue = currentValue
            editText.setText(newValue)
        }
    }

    override fun onResume() {
        super.onResume()
        // Set a focus change listener to update the editTextInFocus variable
        val inputEditText: EditText = findViewById(R.id.orignalPrice)
        inputEditText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                editTextInFocus = v as EditText
            } else {
                // Clear the editTextInFocus when the EditText loses focus
                editTextInFocus = null
            }
        }

        // Request focus for the orignalPrice EditText
        inputEditText.requestFocus()
    }

//    private fun showBanner() {
//        mAdView = binding.adView
//
//        val adRequest = AdRequest.Builder().build()
//        mAdView.loadAd(adRequest)
//    }

    private fun loadIntertialAds() {

        var adRequestI = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequestI,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showIntertialAds() {


        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
        }
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                mInterstitialAd = null
            }

            override fun onAdImpression() {
            }

            override fun onAdShowedFullScreenContent() {
            }
        }
    }

}