package com.github.johnnysc.spacex.presentation.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import com.github.johnnysc.spacex.R
import kotlinx.android.synthetic.main.fragment_no_connection.*

/**
 * @author Asatryan on 18.05.19
 */
class NoConnectionFragment : BaseFragment(R.layout.fragment_no_connection) {

    private var retryListener: RetryListener? = null
    private var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            retryListener?.retry()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        retryListener = context as RetryListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retryButton.setOnClickListener {
            retryListener?.retry()
        }

        val intentFilter = IntentFilter().apply {
            addAction("android.net.conn.CONNECTIVITY_CHANGE")
        }
        context?.registerReceiver(receiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(receiver)
    }

    override fun onDetach() {
        super.onDetach()
        retryListener = null
    }
}