package com.example.recycleviewpilot2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.*
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import coil.size.Scale
import com.example.recycleviewpilot2.databinding.ActivityMainBinding
import com.example.recycleviewpilot2.databinding.FragmentNewsActivityBinding
import com.example.recycleviewpilot2.entities.News
import kotlinx.android.synthetic.main.fragment_news_activity.*
import kotlinx.android.synthetic.main.fragment_news_activity.view.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("StaticFieldLeak")
open class NewsFragmentActivity : Fragment() {


    companion object SaveMe{
            lateinit var context : Context
            lateinit var latestNewsLayout: LinearLayout
            lateinit var sportNewsLayout: LinearLayout
            lateinit var economyNewsLayout: LinearLayout
            lateinit var politicsNewsLayout: LinearLayout
            lateinit var worldNewsLayout: LinearLayout

            fun createCardLayout(news: List<News?>?): LinearLayout  {

                 var linearLayoutReturn = LinearLayout(SaveMe.context)
                 linearLayoutReturn.orientation = VERTICAL
                 linearLayoutReturn.layoutParams = LayoutParams(
                     LayoutParams.MATCH_PARENT,
                     LayoutParams.WRAP_CONTENT,
                 )
                 linearLayoutReturn.setPadding(0, 0, 0, 8)
                 linearLayoutReturn.visibility = VISIBLE

                 news?.forEach { new ->
                     val cardView = CardView(context)
                     val linearLayout = LinearLayout(context)
                     val title = TextView(context)
                     val text = TextView(context)
                     val image = ImageView(context)


                     val id = View.generateViewId()
                     linearLayout.id = id
                     linearLayout.orientation = VERTICAL
                     linearLayout.layoutParams = LayoutParams(
                         LayoutParams.MATCH_PARENT,
                         LayoutParams.WRAP_CONTENT,
                     )
                     linearLayout.setPadding(0, 0, 0, 8)
                     linearLayout.visibility = VISIBLE
                     title.layoutParams = ViewGroup.LayoutParams(
                         LayoutParams.MATCH_PARENT,
                         LayoutParams.WRAP_CONTENT,
                     )
                     title.setTextColor(Color.parseColor("#a48d38"))
                     title.textSize = 25F
                     title.text = new?.title
                     linearLayout.addView(title)

                     image.layoutParams = ViewGroup.LayoutParams(
                         LayoutParams.WRAP_CONTENT,
                         255,
                     )
                     val img = new?.image?.link?.let { convertImageUrl(it) }
                     image.setImageBitmap(img)

                     linearLayout.addView(image)

                     text.layoutParams = ViewGroup.LayoutParams(
                         LayoutParams.MATCH_PARENT,
                         LayoutParams.WRAP_CONTENT,
                     )
                     text.setTextColor(Color.parseColor("#000000"))
                     text.textSize = 25F
                     text.text = new?.description
                     linearLayout.addView(text)
                     cardView.addView(linearLayout)

                     cardView.setPadding(0, 0, 0, 30)
                     cardView.setCardBackgroundColor(Color.parseColor("#423f34"))
                     cardView.elevation = 150F
                     val layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                         LayoutParams.MATCH_PARENT,
                         LayoutParams.WRAP_CONTENT,
                     )
                     layoutParams.setMargins(0, 0, 0, 100)

                     cardView.layoutParams = layoutParams
                     cardView.visibility = View.VISIBLE


                     linearLayoutReturn.addView(cardView, 0)
                 }
                 return linearLayoutReturn
             }

            fun createCard(new: News): CardView {
                val cardView = CardView(context)
                val linearLayout = LinearLayout(context)
                val title = TextView(context)
                val text = TextView(context)
                val image = ImageView(context)


                val id = View.generateViewId()
                linearLayout.id = id
                linearLayout.orientation = VERTICAL
                linearLayout.layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT,
                )
                linearLayout.setPadding(0, 0, 0, 8)
                linearLayout.visibility = VISIBLE
                title.layoutParams = ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT,
                )
                title.setTextColor(Color.parseColor("#a48d38"))
                title.textSize = 25F
                title.text = new?.title
                linearLayout.addView(title)

                image.layoutParams = ViewGroup.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    255,
                )
                val img = new?.image?.link?.let { convertImageUrl(it) }
                image.setImageBitmap(img)


                linearLayout.addView(image)

                text.layoutParams = ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT,
                )
                text.setTextColor(Color.parseColor("#000000"))
                text.textSize = 25F
                text.text = new?.description
                linearLayout.addView(text)
                cardView.addView(linearLayout)

                cardView.setPadding(0, 0, 0, 30)
                cardView.setCardBackgroundColor(Color.parseColor("#423f34"))
                cardView.elevation = 150F
                val layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT,
                )
                layoutParams.setMargins(0, 0, 0, 100)

                cardView.layoutParams = layoutParams
                cardView.visibility = View.VISIBLE

                return cardView
        }

            fun convertImageUrl(url: String): Bitmap? {
                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

                 return try {
                     val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
                     connection.doInput = true
                     connection.connect()
                     val input: InputStream = connection.inputStream
                     BitmapFactory.decodeStream(input)
                 } catch (e: IOException) {
                     println(e)
                     return BitmapFactory.decodeResource(
                         context.resources,
                         R.drawable.taycan)
                 }
            }
        }

    private var _binding: FragmentNewsActivityBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(MainActivity.SaveMe2.latestNewsLayoutRestore == null) {
            latestNewsLayout = LinearLayout(requireContext())
            sportNewsLayout = LinearLayout(requireContext())
            economyNewsLayout = LinearLayout(requireContext())
            politicsNewsLayout = LinearLayout(requireContext())
            worldNewsLayout = LinearLayout(requireContext())
        }
        else{
            NewsFragmentActivity.latestNewsLayout = MainActivity.SaveMe2.latestNewsLayoutRestore!!
            SaveMe.sportNewsLayout = MainActivity.SaveMe2.sportNewsLayoutRestore!!
            SaveMe.economyNewsLayout = MainActivity.SaveMe2.economyNewsLayoutRestore!!
            SaveMe.politicsNewsLayout = MainActivity.SaveMe2.politicsNewsLayoutRestore!!
            SaveMe.worldNewsLayout = MainActivity.SaveMe2.worldNewsLayoutRestore!!
        }
        MainActivity.SaveMe2.newsCtx = requireContext()
        NewsFragmentActivity.context = MainActivity.SaveMe2.newsCtx

        Handler(Looper.getMainLooper()).post {
            binding.scroll.viewTreeObserver.addOnScrollChangedListener {
                if(!scroll.canScrollVertically(1)) {
                    var toast = Toast.makeText(context, "No more news", Toast.LENGTH_SHORT)
                    var countDown:Long = 800

                    // Set the countdown to display the toast
                    val toastCountDown: CountDownTimer
                    toastCountDown =
                        object : CountDownTimer(countDown, 1000 /*Tick duration*/) {
                            override fun onTick(millisUntilFinished: Long) {
                                toast.show()
                            }

                            override fun onFinish() {
                                toast.cancel()
                            }
                        }
                    // Show the toast and starts the countdown
                    toast.show()
                    toastCountDown.start()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MainActivity.SaveMe2.newsCtx = requireContext()
        NewsFragmentActivity.context = MainActivity.SaveMe2.newsCtx
        _binding = FragmentNewsActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(MainActivity.SaveMe2.latestNewsLayoutRestore != null) {
            NewsFragmentActivity.latestNewsLayout = MainActivity.SaveMe2.latestNewsLayoutRestore!!
            SaveMe.sportNewsLayout = MainActivity.SaveMe2.sportNewsLayoutRestore!!
            SaveMe.economyNewsLayout = MainActivity.SaveMe2.economyNewsLayoutRestore!!
            SaveMe.politicsNewsLayout = MainActivity.SaveMe2.politicsNewsLayoutRestore!!
            SaveMe.worldNewsLayout = MainActivity.SaveMe2.worldNewsLayoutRestore!!

            scroll.removeAllViews()
            scroll.addView(MainActivity.worldNewsLayoutRestore)
        }
    }

    override fun onPause() {
        super.onPause()
        MainActivity.SaveMe2.latestNewsLayoutRestore = SaveMe.latestNewsLayout
        MainActivity.SaveMe2.sportNewsLayoutRestore = SaveMe.sportNewsLayout
        MainActivity.SaveMe2.economyNewsLayoutRestore = SaveMe.economyNewsLayout
        MainActivity.SaveMe2.politicsNewsLayoutRestore = SaveMe.politicsNewsLayout
        MainActivity.SaveMe2.worldNewsLayoutRestore = SaveMe.worldNewsLayout
    }

}



















































