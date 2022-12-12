package com.example.recycleviewpilot2.sockets

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.cardview.widget.CardView
import com.azure.messaging.webpubsub.WebPubSubServiceClientBuilder
import com.azure.messaging.webpubsub.models.GetClientAccessTokenOptions
import com.example.recycleviewpilot2.MainActivity
import com.example.recycleviewpilot2.NewsFragmentActivity
import com.example.recycleviewpilot2.entities.News
import com.example.recycleviewpilot2.entities.keys
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_news_activity.view.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.io.IOException
import java.net.URI

class SubToSocket(val activityMainBinding: MainActivity, hub: String){

    var webSocketClient: WebSocketClient

    var service = WebPubSubServiceClientBuilder()
        .connectionString(keys.getConnectionString())
        .hub(hub)
        .buildClient()
    var token = service.getClientAccessToken(GetClientAccessTokenOptions())



    init {

        webSocketClient = object : WebSocketClient(URI(token.url)) {
            override fun onMessage(message: String) {
                val new: News = Gson().fromJson(message, News::class.java)

                println(String.format("Message received: %s", message))
                Log.i("newSocket", new.toString())

                Handler(Looper.getMainLooper()).post {
                    val cardView:CardView = NewsFragmentActivity.createCard(new)

                    var cats:HashMap<String, MutableList<News?>?> = HashMap()
                    cats.put("Desporto", MainActivity.SaveMe2.sportNewsList)
                    cats.put("Economia", MainActivity.SaveMe2.economyNewsList)
                    cats.put("Política", MainActivity.SaveMe2.politicsNewsList)
                    cats.put("Mundo", MainActivity.SaveMe2.worldNewsList)

                    /*add to latest list*/
                    MainActivity.SaveMe2.latestNewsList?.add(new)

                    //add to layout
                    if(MainActivity.SaveMe2.current in new.categories){
                        activityMainBinding.addToLayout(cardView, 0)
                    }

                    for((key, value) in cats){
                        if(key in new.categories){
                            value?.add(new)
                        }
                    }
                }
            }

            override fun onClose(arg0: Int, arg1: String, arg2: Boolean) {}

            override fun onError(arg0: Exception) {}

            override fun onOpen(arg0: ServerHandshake) {}
        }
    }

    @Throws(IOException::class)
    fun subscribe() {
        webSocketClient.connect()
        System.`in`.read()
    }
}