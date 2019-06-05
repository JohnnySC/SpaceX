package com.github.johnnysc.spacex.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import com.github.johnnysc.spacex.R
import kotlinx.android.synthetic.main.fragment_service_unavailable.*

/**
 * @author Asatryan on 18.05.19
 */
class ServiceUnavailableFragment : BaseFragment(R.layout.fragment_service_unavailable) {

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