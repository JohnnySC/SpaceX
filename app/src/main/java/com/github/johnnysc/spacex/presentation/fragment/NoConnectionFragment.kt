package com.github.johnnysc.spacex.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import com.github.johnnysc.spacex.R
import kotlinx.android.synthetic.main.fragment_no_connection.*

/**
 * @author Asatryan on 18.05.19
 */
class NoConnectionFragment : BaseFragment(R.layout.fragment_no_connection) {

    private var retryListener: RetryListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        retryListener = context as RetryListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retryButton.setOnClickListener {
            retryListener?.retry()
        }
    }

    override fun onDetach() {
        super.onDetach()
        retryListener = null
    }
}