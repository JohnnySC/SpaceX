package com.github.johnnysc.spacex.presentation.details.adapter

import android.view.View
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image.view.*

/**
 * @author Asatryan on 06.06.19
 */
class ImageViewHolder(view: View) : LaunchDetailViewHolder<LaunchDataUiModel.Image>(view) {

    override fun bind(model: Any) = Picasso.get()
        .load((model as LaunchDataUiModel.Image).value)
        .into(itemView.imageView)
}