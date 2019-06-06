package com.github.johnnysc.spacex.presentation.details.adapter

import android.view.View
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import kotlinx.android.synthetic.main.title.view.*

/**
 * @author Asatryan on 06.06.19
 */
class MissionNameViewHolder(view: View) : LaunchDetailViewHolder<LaunchDataUiModel.MissionName>(view) {

    override fun bind(model: Any) {
        val value = (model as LaunchDataUiModel.MissionName).value
        itemView.textView.text = itemView.context.getString(R.string.mission_name, value)
    }
}