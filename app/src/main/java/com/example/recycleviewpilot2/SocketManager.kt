package com.example.recycleviewpilot2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recycleviewpilot2.databinding.SocketsManagerBinding
import com.example.recycleviewpilot2.sockets.PubToSocket
import com.example.recycleviewpilot2.sockets.SubToSocket

class SocketManager : Fragment() {

    private var _bindMain: MainActivity? = null
    private val bindMain get() = _bindMain!!

    private var _binding: SocketsManagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SocketsManagerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.subscribeHub.setOnClickListener {
            val sub = SubToSocket(bindMain, binding.hub.text.toString())
            sub.subscribe()
        }
        binding.sendToSocket.setOnClickListener {
            val pub = PubToSocket(binding.hub.text.toString())
            pub.sendMessageToAll(binding.textToSocket.text.toString())

        }
    }
}
