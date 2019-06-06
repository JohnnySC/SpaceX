package com.github.johnnysc.spacex.presentation.details.adapter

import android.view.View
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import kotlinx.android.synthetic.main.checkbox.view.*

/**
 * @author Asatryan on 06.06.19
 */
class LaunchSuccessViewHolder(view: View) : LaunchDetailViewHolder<LaunchDataUiModel.LaunchSuccess>(view) {

    override fun bind(model: Any) {
        with(itemView.checkbox) {
            isChecked = (model as LaunchDataUiModel.LaunchSuccess).value
            setText(R.string.launch_success)
        }
    }
}