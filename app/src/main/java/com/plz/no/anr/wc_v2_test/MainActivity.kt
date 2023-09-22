package com.plz.no.anr.wc_v2_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.plz.no.anr.wc_v2_test.databinding.ActivityMainBinding
import com.plz.no.anr.wc_v2_test.v2.WCManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val wcManager = WCManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel by viewModels<MainViewModel>()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)

            btnApprove.setOnClickListener {
                approve()
            }
            btnReject.setOnClickListener {
                reject()
            }
            btnDisconnect.setOnClickListener {
                wcManager.disconnect()
            }
        }

        wcManager.pair("wc:0e876060072918a751f6ff469f2677060da3dcb05ece52334cd3e1e35f6ce833@2?relay-protocol=irn&symKey=cf9981ca04dd913a3ecdb3f9206b3da12499691e36cabab0acd4c429f84ed879")

        viewModel.event
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                println("SocketEvent: $it")
                binding.textView.text = it.toString()
            }.launchIn(lifecycleScope)

        viewModel.coreEvent
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                println(it)
            }.launchIn(lifecycleScope)
     }

    private fun approve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                wcManager.approveSession()
            }
        }
    }

    private fun reject() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                wcManager.rejectSession()
            }
        }
    }

    override fun onDestroy() {
        wcManager.disconnect()
        super.onDestroy()
    }

}