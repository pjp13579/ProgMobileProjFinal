package com.example.recycleviewpilot2

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.recycleviewpilot2.Retrofit.Retrofit
import com.example.recycleviewpilot2.databinding.ActivityMainBinding
import com.example.recycleviewpilot2.databinding.FragmentNewsActivityBinding
import com.example.recycleviewpilot2.entities.News
import com.example.recycleviewpilot2.sockets.SubToSocket
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news_activity.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var binding2 : FragmentNewsActivityBinding
    private var sub: SubToSocket = SubToSocket(this, "global")
    private lateinit var retro: Retrofit

    companion object SaveMe2{
        lateinit var newsCtx: Context

        var latestNewsLayoutRestore: LinearLayout? = null
        var sportNewsLayoutRestore: LinearLayout? = null
        var economyNewsLayoutRestore: LinearLayout? = null
        var politicsNewsLayoutRestore: LinearLayout? = null
        var worldNewsLayoutRestore: LinearLayout? = null

        var latestNewsList: MutableList<News?>? = mutableListOf()
        var sportNewsList: MutableList<News?>? = mutableListOf()
        var economyNewsList: MutableList<News?>? = mutableListOf()
        var politicsNewsList: MutableList<News?>? = mutableListOf()
        var worldNewsList: MutableList<News?>? = mutableListOf()

        var current:String = "Latest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding2 = FragmentNewsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "404 NotFound"


        replaceFragment(NewsFragmentActivity(), "first")
        retro = Retrofit(this)
        //replaceFragment(Loading())
        retro.getLatest()

        binding.latest.setOnClickListener {
            scroll.fullScroll(ScrollView.FOCUS_UP)
            scroll.removeAllViews()
            scroll.addView(NewsFragmentActivity.SaveMe.latestNewsLayout)
            current = "Latest"
        }
        binding.sports.setOnClickListener {
            scroll.fullScroll(ScrollView.FOCUS_UP)
            scroll.removeAllViews()
            scroll.addView(NewsFragmentActivity.SaveMe.sportNewsLayout)
            current = "Desporto"
        }
        binding.economics.setOnClickListener {
            scroll.fullScroll(ScrollView.FOCUS_UP)
            scroll.removeAllViews()
            scroll.addView(NewsFragmentActivity.SaveMe.economyNewsLayout)
            current = "Economia"
        }
        binding.politics.setOnClickListener {
            scroll.fullScroll(ScrollView.FOCUS_UP)
            scroll.removeAllViews()
            scroll.addView(NewsFragmentActivity.SaveMe.politicsNewsLayout)
            current = "Política"
        }
        binding.world.setOnClickListener {
            replaceFragment(NewsFragmentActivity(), "newsFragTag")
            scroll.fullScroll(ScrollView.FOCUS_UP)
            current = "Mundo"
        }
        binding.sockets.setOnClickListener{
            replaceFragment(SocketManager(), "socketFragTag")
        }
        sub.subscribe()
    }

    fun setUpRetro(){
        retro.getByCat("Desporto")
        retro.getByCat("Economia")
        retro.getByCat("Mundo")
        retro.getByCat("Política")
    }

    fun replaceFragment(fragment: Fragment, name: String) {
        val fragmentManager = supportFragmentManager
        val fragTransaction = fragmentManager.beginTransaction()
        fragTransaction.replace(R.id.fragmentContainerView, fragment)
        fragTransaction.addToBackStack(name)
        fragTransaction.commit()
    }

    fun switchFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragTransaction = fragmentManager.beginTransaction()
        fragTransaction.replace(R.id.fragmentContainerView, fragment, "0")
        fragTransaction.commit()
    }

    fun setLatest(){
        binding2.scroll.removeAllViews()
        //var lay = NewsFragmentActivity.createCardLayout(latestNewsList)
        //binding2.scroll.addView(lay)
    }

    fun addToLayout(cardView: CardView, index:Int){
        binding2.newsLinearLayout.addView(cardView, index)
    }
}