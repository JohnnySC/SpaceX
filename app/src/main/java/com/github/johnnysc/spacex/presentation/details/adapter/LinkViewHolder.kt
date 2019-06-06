package com.github.johnnysc.spacex.presentation.details.adapter

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.View
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import kotlinx.android.synthetic.main.link.view.*

/**
 * @author Asatryan on 06.06.19
 */
class LinkViewHolder(view: View) : LaunchDetailViewHolder<LaunchDataUiModel.LinkTitle>(view) {

    override fun bind(model: Any) {
        itemView.linkTextView.text = (model as LaunchDataUiModel.LinkTitle).title
        itemView.linkTextView.paintFlags = itemView.linkTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(model.value)
            }
            itemView.context.startActivity(intent)
        }
    }
}