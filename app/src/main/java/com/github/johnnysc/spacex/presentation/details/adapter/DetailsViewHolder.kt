package com.github.johnnysc.spacex.presentation.details.adapter

import android.view.View
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import kotlinx.android.synthetic.main.subtitle.view.*

/**
 * @author Asatryan on 06.06.19
 */
class DetailsViewHolder(view: View) : LaunchDetailViewHolder<LaunchDataUiModel.Details>(view) {

    override fun bind(model: Any) {
        itemView.textView.text =
            itemView.context.getString(R.string.details, (model as LaunchDataUiModel.Details).value)
    }
}