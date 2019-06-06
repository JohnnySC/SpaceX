package com.github.johnnysc.spacex.presentation.details.adapter

import android.view.View
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import kotlinx.android.synthetic.main.subtitle.view.*

/**
 * @author Asatryan on 06.06.19
 */
class RocketViewHolder(view: View) : LaunchDetailViewHolder<LaunchDataUiModel.Rocket>(view) {
    override fun bind(model: Any) {
        val modelData = model as LaunchDataUiModel.Rocket
        var firstStage = ""
        modelData.firstStageData.forEach {
            firstStage += it.first + " " + it.second + "\n"
        }
        var secondStage = modelData.secondStage.block.toString() + "\n"
        modelData.secondStage.payloads.forEach {
            secondStage += it.manufacturer + "\n" +
                    it.nationality + "\n" +
                    it.payloadType + "\n" +
                    it.payloadMassKg + "\n" +
                    it.orbit + "\n"
        }
        val rocket = "\n" + modelData.value + "\n" + modelData.type + "\n" + firstStage + "\n" + secondStage
        itemView.textView.text = itemView.context.getString(R.string.rocket, rocket)
    }
}