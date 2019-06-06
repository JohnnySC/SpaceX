package com.github.johnnysc.spacex.presentation.details.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import kotlinx.android.synthetic.main.link.view.*
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import com.github.johnnysc.spacex.R

/**
 * @author Asatryan on 06.06.19
 */
class PDFViewHolder(view: View) : LaunchDetailViewHolder<LaunchDataUiModel.PDF>(view) {

    override fun bind(model: Any) {
        val modelData = (model as LaunchDataUiModel.PDF)
        with(itemView.linkTextView) {
            paintFlags = this.paintFlags or UNDERLINE_TEXT_FLAG
            text = itemView.context.getString(R.string.pdf, modelData.title)
        }
        itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(modelData.value)
            }
            itemView.context.startActivity(intent)
        }
    }
}