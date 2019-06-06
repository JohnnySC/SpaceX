package com.github.johnnysc.spacex.presentation.details.adapter

import android.view.View
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import kotlinx.android.synthetic.main.subtitle.view.*

/**
 * @author Asatryan on 06.06.19
 */
class LaunchPlaceViewHolder(view: View) : LaunchDetailViewHolder<LaunchDataUiModel.LaunchPlace>(view) {

    override fun bind(model: Any) {
        itemView.textView.text =
            itemView.context.getString(R.string.place, (model as LaunchDataUiModel.LaunchPlace).value)
    }
}