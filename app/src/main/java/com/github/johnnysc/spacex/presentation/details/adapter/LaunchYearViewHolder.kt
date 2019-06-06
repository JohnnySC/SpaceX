package com.github.johnnysc.spacex.presentation.details.adapter

import android.view.View
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import kotlinx.android.synthetic.main.subtitle.view.*

/**
 * @author Asatryan on 06.06.19
 */
class LaunchYearViewHolder(view: View) : LaunchDetailViewHolder<LaunchDataUiModel.LaunchYear>(view) {

    override fun bind(model: Any) {
        val value = (model as LaunchDataUiModel.LaunchYear).value
        itemView.textView.text = itemView.context.getString(R.string.launch_year, value)
    }
}