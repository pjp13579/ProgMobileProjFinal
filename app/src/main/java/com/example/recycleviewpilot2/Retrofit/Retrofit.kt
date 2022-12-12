package com.example.recycleviewpilot2.Retrofit


import android.os.Looper
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.get
import com.example.recycleviewpilot2.MainActivity
import com.example.recycleviewpilot2.NewsFragmentActivity
import com.example.recycleviewpilot2.entities.News
import kotlinx.android.synthetic.main.fragment_news_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Handler

class Retrofit(val activityMainBinding: MainActivity) {

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://workgaddammit.azurewebsites.net/api/")
        .build()
        .create(RetrofitInterface::class.java)

    lateinit var retrofitData:  Call<List<News?>>

    fun getByCat(category: String) {

        when(category) {
            "Desporto" -> retrofitData = retrofitBuilder.getSport()
            "Economia" -> retrofitData = retrofitBuilder.getEconomic()
            "Política" -> retrofitData = retrofitBuilder.getPolitics()
            "Mundo" -> retrofitData = retrofitBuilder.getWorld()
        }

        retrofitData.enqueue(object : Callback<List<News?>> {
            override fun onResponse(call: Call<List<News?>>, response: Response<List<News?>>) {
                val responseBody = response.body()
                when(category) {
                    "Desporto" -> NewsFragmentActivity.SaveMe.sportNewsLayout.addView(NewsFragmentActivity.SaveMe.createCardLayout(responseBody))
                    "Economia" -> NewsFragmentActivity.SaveMe.economyNewsLayout.addView(NewsFragmentActivity.SaveMe.createCardLayout(responseBody))
                    "Política" -> NewsFragmentActivity.SaveMe.politicsNewsLayout.addView(NewsFragmentActivity.SaveMe.createCardLayout(responseBody))
                    "Mundo"    -> {
                        NewsFragmentActivity.SaveMe.worldNewsLayout.addView(NewsFragmentActivity.SaveMe.createCardLayout(responseBody))
                        android.os.Handler(Looper.getMainLooper()).post{
                            Toast.makeText(NewsFragmentActivity.SaveMe.context, "All set up", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<News?>>, t: Throwable) {

                    Toast.makeText(activityMainBinding, category + " failed to load", Toast.LENGTH_SHORT).show()
                    getByCat(category)
            }
        })
    }


    fun getLatest() {
        retrofitData = retrofitBuilder.getLatest()
        retrofitData.enqueue(object : Callback<List<News?>> {
            override fun onResponse(call: Call<List<News?>>, response: Response<List<News?>>) {
                val responseBody = response.body()
                NewsFragmentActivity.SaveMe.latestNewsLayout.addView(NewsFragmentActivity.SaveMe.createCardLayout(responseBody))
                activityMainBinding.setUpRetro()

                //activityMainBinding.setLatest()
            }
            override fun onFailure(call: Call<List<News?>>, t: Throwable) {
                Toast.makeText(activityMainBinding, "failed to load latest", Toast.LENGTH_SHORT).show()
                getLatest()
            }
        })
    }

}