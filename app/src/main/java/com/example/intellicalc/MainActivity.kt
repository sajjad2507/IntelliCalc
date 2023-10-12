package com.example.intellicalc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intellicalc.Activities.CategoryActivity
import com.example.intellicalc.Adapters.HistoryAdapter
import com.example.intellicalc.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val calculatorHistory = mutableListOf<Pair<String, String>>()
    private var pr = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()


        binding.input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Trigger evaluation whenever the input text changes
                showResult()
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing after text changes
            }
        })

        binding.allClear.setOnClickListener {
            binding.input.text = ""
            binding.output.text = ""
        }

        binding.startBracket.setOnClickListener {

            // Check the value of pr
            if (pr == 0) {
                // If pr is 0, insert a starting parenthesis
                binding.input.text = addToInputText("(")
                pr = 1 // Set pr to 1
            } else {
                // If pr is 1, insert an ending parenthesis
                binding.input.text = addToInputText(")")
                pr = 0 // Set pr back to 0
            }

        }
        binding.closeBracket.setOnClickListener {

            binding.input.text = addToInputText("%")

        }

        binding.back.setOnClickListener {
            val removedLast = binding.input.text.toString().dropLast(1)
            binding.input.text = removedLast
        }

        binding.zero.setOnClickListener {
            binding.input.text = addToInputText("0")
        }
        binding.doubleZero.setOnClickListener {
            binding.input.text = addToInputText("00")
        }
        binding.one.setOnClickListener {
            binding.input.text = addToInputText("1")
        }
        binding.two.setOnClickListener {
            binding.input.text = addToInputText("2")
        }
        binding.three.setOnClickListener {
            binding.input.text = addToInputText("3")
        }
        binding.four.setOnClickListener {
            binding.input.text = addToInputText("4")
        }
        binding.five.setOnClickListener {
            binding.input.text = addToInputText("5")
        }
        binding.six.setOnClickListener {
            binding.input.text = addToInputText("6")
        }
        binding.seven.setOnClickListener {
            binding.input.text = addToInputText("7")
        }
        binding.eight.setOnClickListener {
            binding.input.text = addToInputText("8")
        }
        binding.nine.setOnClickListener {
            binding.input.text = addToInputText("9")
        }
        binding.dot.setOnClickListener {
            binding.input.text = addToInputText(".")
        }
        binding.division.setOnClickListener {
            binding.input.text = addToInputText("÷") // ALT + 0247
        }
        binding.multiply.setOnClickListener {
            binding.input.text = addToInputText("×") // ALT + 0215
        }

        binding.minus.setOnClickListener {
            binding.input.text = addToInputText("-")
        }
        binding.plus.setOnClickListener {
            binding.input.text = addToInputText("+")
        }

        binding.equal.setOnClickListener {
//            showResult()
            calculatorHistory.add(Pair(binding.input.text.toString(), binding.output.text.toString()))
            // Call this function to update the calculator history display

            saveCalculatorHistoryToSharedPreferences() // Save the history to SharedPreferences

            binding.input.text = binding.output.text
            binding.output.text = ""
        }

        binding.category.setOnClickListener {

            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)

        }

        binding.history.setOnClickListener {

            loadCalculatorHistoryFromSharedPreferences() // Load the saved history

        }

        binding.cancelBtn.setOnClickListener {

            binding.historyCard.visibility = View.GONE

        }

    }

    private fun addToInputText(buttonValue: String): String {
        val buttonValue = binding.input.text.toString() + "" + buttonValue
        return buttonValue
    }

    private fun getInputExpression(): String {
        var expression = binding.input.text.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("×"), "*")
        return expression as String
    }

    private fun showResult() {
        try {
            val expression = getInputExpression()
            val result = Expression(expression).calculate()
            if (result.isNaN()) {
                // Show Error Message
                binding.output.text = ""
            } else {
                // Show Result
                val formattedResult = DecimalFormat("0.######").format(result).toString()
                binding.output.text = formattedResult
//                calculatorHistory.add(Pair(expression, formattedResult))
////                updateCalculatorHistory() // Notify the RecyclerView about the data change
            }
        } catch (e: Exception) {
            // Show Error Message
            binding.output.text = ""
        }
    }

//    private fun updateCalculatorHistory() {
//
//        val adapter = HistoryAdapter(calculatorHistory, this)
//        binding.historyRcv.layoutManager = LinearLayoutManager(this)
//        binding.historyRcv.adapter = adapter
//    }
    private fun saveCalculatorHistoryToSharedPreferences() {
        val sharedPreferences = getSharedPreferences("CalculatorHistory", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(calculatorHistory)
        editor.putString("history_list", json)
        editor.apply()
    }
    private fun loadCalculatorHistoryFromSharedPreferences() {
        val sharedPreferences = getSharedPreferences("CalculatorHistory", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("history_list", null)
        val type = object : TypeToken<List<Pair<String, String>>>() {}.type
        val gson = Gson()
        calculatorHistory.clear()
        if (!json.isNullOrEmpty()) {
            val historyList: List<Pair<String, String>> = gson.fromJson(json, type)
            calculatorHistory.addAll(historyList)

            binding.historyCard.visibility = View.VISIBLE

            val adapter = HistoryAdapter(historyList, this, binding)
            binding.historyRcv.layoutManager = LinearLayoutManager(this)
            binding.historyRcv.adapter = adapter
        }
    }

}