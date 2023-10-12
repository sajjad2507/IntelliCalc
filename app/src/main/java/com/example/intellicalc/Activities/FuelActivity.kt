package com.example.intellicalc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.example.intellicalc.R
import com.example.intellicalc.databinding.ActivityFuelBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class FuelActivity : AppCompatActivity() {

    lateinit var binding: ActivityFuelBinding
    // Define a variable to keep track of the EditText in focus
    private var editTextInFocus: EditText? = null
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)

        binding = ActivityFuelBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        MobileAds.initialize(this) {}

//        showBanner()

        //        binding.addedTax.showSoftInputOnFocus = false
        binding.mileageVehicle.showSoftInputOnFocus = false
        binding.totalDistance.showSoftInputOnFocus = false
        binding.fuelUnitPrice.showSoftInputOnFocus = false

//        binding.addedTax.isCursorVisible = true
        binding.mileageVehicle.isCursorVisible = true
        binding.totalDistance.isCursorVisible = true
        binding.fuelUnitPrice.isCursorVisible = true


        binding.mileageVehicle.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                editTextInFocus = v as EditText
            }
        }

        binding.totalDistance.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                editTextInFocus = v as EditText
            }
        }

        binding.fuelUnitPrice.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                editTextInFocus = v as EditText
            }
        }


        binding.allClear.setOnClickListener {
            binding.mileageVehicle.setText("")
            binding.totalDistance.setText("")
            binding.fuelUnitPrice.setText("")
            binding.totalFuelPrice.setText("")
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
        if (binding.mileageVehicle.text.isNotEmpty() && binding.fuelUnitPrice.text.isNotEmpty() && binding.totalDistance.text.isNotEmpty()) {
            try {
                // Get the values from the EditTexts
                val mileageVehicleStr = binding.mileageVehicle.text.toString()
                val distanceStr = binding.totalDistance.text.toString()
                val fuelUnitPriceStr = binding.fuelUnitPrice.text.toString()

                // Convert the values to double
                val mileageVehicle = mileageVehicleStr.toDoubleOrNull() ?: 0.0
                val distance = distanceStr.toDoubleOrNull() ?: 0.0
                val fuelUnitPrice = fuelUnitPriceStr.toDoubleOrNull() ?: 0.0

                // Calculate the saved amount and discounted percent
                val totalFuelPrice = (distance / mileageVehicle) * fuelUnitPrice

                // Set the values to the respective EditTexts
                binding.totalFuelPrice.setText(totalFuelPrice.toString())

            } catch (e: Exception) {
                // Show Error Message or handle the exception as per your requirements
                Toast.makeText(
                    this,
                    "Error calculating saved amount and discounted percent",
                    Toast.LENGTH_SHORT
                ).show()
            }

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
        val inputEditText: EditText = findViewById(R.id.mileageVehicle)
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

}