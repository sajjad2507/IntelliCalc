package com.example.intellicalc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.intellicalc.MainActivity
import com.example.intellicalc.R
import com.example.intellicalc.databinding.ActivityMainBinding

class HistoryAdapter(
    private val historyList: List<Pair<String, String>>,
    val mainActivity: MainActivity,
    val binding: ActivityMainBinding
) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expressionTextView: TextView = itemView.findViewById(R.id.expressionTextView)
        val resultTextView: TextView = itemView.findViewById(R.id.resultTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        holder.expressionTextView.text = historyList[position].first
        holder.resultTextView.text = historyList[position].second

        holder.itemView.setOnClickListener {

            binding.input.setText(historyList[position].first)
            binding.historyCard.visibility = View.GONE

        }

    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}

