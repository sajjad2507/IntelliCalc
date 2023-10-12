package com.example.intellicalc.Adapters

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intellicalc.Activities.*
import com.example.intellicalc.DataModel.Category
import com.example.intellicalc.MainActivity
import com.example.intellicalc.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class CategoryAdapter(
    private val categoryList: List<Category>,
    val categoryActivity: CategoryActivity
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var mInterstitialAd: InterstitialAd? = null

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryIcon: ImageView = itemView.findViewById(R.id.categoryImage)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)

        loadIntertialAds()

        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryIcon.setImageResource(category.iconResId)
        holder.categoryName.text = category.name

        holder.itemView.setOnClickListener {


            showIntertialAds()

            if (category.name == "Standard") {
                    val intent = Intent(categoryActivity, MainActivity::class.java)
                    categoryActivity.startActivity(intent)
            } else if (category.name == "Discount") {
                val intent = Intent(categoryActivity, DiscountActivity::class.java)
                categoryActivity.startActivity(intent)
            } else if (category.name == "Percentage") {
                val intent = Intent(categoryActivity, PercentageActivity::class.java)
                categoryActivity.startActivity(intent)
            }else if (category.name == "Tip") {
                val intent = Intent(categoryActivity, TipActivity::class.java)
                categoryActivity.startActivity(intent)
            } else if (category.name == "Unit Price") {
                val intent = Intent(categoryActivity, UnitPriceActivity::class.java)
                categoryActivity.startActivity(intent)
            } else if (category.name == "Fuel") {
                val intent = Intent(categoryActivity, FuelActivity::class.java)
                categoryActivity.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }


    private fun loadIntertialAds() {

        var adRequestI = AdRequest.Builder().build()

        InterstitialAd.load(
            categoryActivity,
            "ca-app-pub-4678475583179673/4697074395",
            adRequestI,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    // Show the ad after a delay (for example, 2 seconds)
                    Handler(Looper.getMainLooper()).postDelayed({
                        showIntertialAds()
                    }, 3000)
                }
            })

//        InterstitialAd.load(
//            categoryActivity,
//            "ca-app-pub-3940256099942544/1033173712",
//            adRequestI,
//            object : InterstitialAdLoadCallback() {
//                override fun onAdFailedToLoad(adError: LoadAdError) {
//                    mInterstitialAd = null
//                }
//
//                override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                    mInterstitialAd = interstitialAd
//                }
//            })
    }

    private fun showIntertialAds() {


        if (mInterstitialAd != null) {
            mInterstitialAd?.show(categoryActivity)
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
