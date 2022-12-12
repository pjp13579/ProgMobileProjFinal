package com.example.recycleviewpilot2.sockets

import com.azure.messaging.webpubsub.WebPubSubServiceClient
import com.azure.messaging.webpubsub.WebPubSubServiceClientBuilder
import com.azure.messaging.webpubsub.models.GetClientAccessTokenOptions
import com.azure.messaging.webpubsub.models.WebPubSubClientAccessToken
import com.azure.messaging.webpubsub.models.WebPubSubContentType
import com.example.recycleviewpilot2.entities.keys
import java.text.SimpleDateFormat
import java.util.*

class PubToSocket(hub: String) {
    private val websocket: WebPubSubServiceClient
    private val token: WebPubSubClientAccessToken
    private val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    init {
        websocket = WebPubSubServiceClientBuilder()
            .connectionString(keys.getConnectionString())
            .hub(hub.trim { it <= ' ' })
            .buildClient()
        val options = GetClientAccessTokenOptions()
        token = websocket.getClientAccessToken(options)
        keys.setClientAccessURL(token.url)
        println(keys.getClientAccessURL())
    }

    fun sendMessageToAll(text: String?) {
        websocket.sendToAll(text, WebPubSubContentType.TEXT_PLAIN)
    }

    fun sendMessageToConnection(connection: String?) {
        websocket.sendToConnection(connection, sdf.format(Date()), WebPubSubContentType.TEXT_PLAIN)
    }

    fun sendMessageToUser(user: String?) {
        websocket.sendToUser(user, sdf.format(Date()), WebPubSubContentType.TEXT_PLAIN)
    } // send a text message directly to a user
    // websocket.sendToUser("jogiles", "Hi there!",
    // WebPubSubContentType.TEXT_PLAIN);
    //
    // send a text message to a specific connection
    // websocket.sendToConnection("Tn3XcrAbHI0OE36XvbWwige4ac096c1", "Hi there!",
    // WebPubSubContentType.TEXT_PLAIN);
    // websocket.sendToGroup(group, text, WebPubSubContentType.TEXT_PLAIN);
}