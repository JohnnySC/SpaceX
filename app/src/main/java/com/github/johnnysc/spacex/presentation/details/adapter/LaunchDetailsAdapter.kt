package com.github.johnnysc.spacex.presentation.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.details.model.LaunchDataUiModel
import java.lang.UnsupportedOperationException

/**
 * @author Asatryan on 06.06.19
 */
class LaunchDetailsAdapter(private val items: List<LaunchDataUiModel<*>>) :
    RecyclerView.Adapter<LaunchDetailViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchDetailViewHolder<*> = when (viewType) {
        LaunchDetailsViewType.MISSION_NAME.ordinal -> MissionNameViewHolder(parent.makeView(R.layout.title))
        LaunchDetailsViewType.FLIGHT_NUMBER.ordinal -> FlightNumberViewHolder(parent.makeView(R.layout.subtitle))
        LaunchDetailsViewType.LAUNCH_YEAR.ordinal -> LaunchYearViewHolder(parent.makeView(R.layout.subtitle))
        LaunchDetailsViewType.LAUNCH_DATE.ordinal -> LaunchDateViewHolder(parent.makeView(R.layout.subtitle))
        LaunchDetailsViewType.ROCKET.ordinal -> RocketViewHolder(parent.makeView(R.layout.subtitle))
        LaunchDetailsViewType.SHIPS.ordinal -> ShipsViewHolder(parent.makeView(R.layout.subtitle))
        LaunchDetailsViewType.LAUNCH_PLACE.ordinal -> LaunchPlaceViewHolder(parent.makeView(R.layout.subtitle))
        LaunchDetailsViewType.LAUNCH_SUCCESS.ordinal -> LaunchSuccessViewHolder(parent.makeView(R.layout.checkbox))
        LaunchDetailsViewType.LINK.ordinal -> LinkViewHolder(parent.makeView(R.layout.link))
        LaunchDetailsViewType.IMAGE.ordinal -> ImageViewHolder(parent.makeView(R.layout.image))
        LaunchDetailsViewType.PDF.ordinal -> PDFViewHolder(parent.makeView(R.layout.link))
        LaunchDetailsViewType.DETAILS.ordinal -> DetailsViewHolder(parent.makeView(R.layout.subtitle))
        else -> throw UnsupportedOperationException("unknown type of item")
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: LaunchDetailViewHolder<*>, position: Int) =
        holder.bind(items[position])

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is LaunchDataUiModel.MissionName -> LaunchDetailsViewType.MISSION_NAME.ordinal
        is LaunchDataUiModel.FlightNumber -> LaunchDetailsViewType.FLIGHT_NUMBER.ordinal
        is LaunchDataUiModel.LaunchYear -> LaunchDetailsViewType.LAUNCH_YEAR.ordinal
        is LaunchDataUiModel.LaunchDate -> LaunchDetailsViewType.LAUNCH_DATE.ordinal
        is LaunchDataUiModel.Rocket -> LaunchDetailsViewType.ROCKET.ordinal
        is LaunchDataUiModel.Ships -> LaunchDetailsViewType.SHIPS.ordinal
        is LaunchDataUiModel.LaunchPlace -> LaunchDetailsViewType.LAUNCH_PLACE.ordinal
        is LaunchDataUiModel.LaunchSuccess -> LaunchDetailsViewType.LAUNCH_SUCCESS.ordinal
        is LaunchDataUiModel.LinkTitle -> LaunchDetailsViewType.LINK.ordinal
        is LaunchDataUiModel.Image -> LaunchDetailsViewType.IMAGE.ordinal
        is LaunchDataUiModel.PDF -> LaunchDetailsViewType.PDF.ordinal
        is LaunchDataUiModel.Details -> LaunchDetailsViewType.DETAILS.ordinal
    }
}

abstract class LaunchDetailViewHolder<T : LaunchDataUiModel<*>>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(model: Any)
}

private enum class LaunchDetailsViewType {
    MISSION_NAME,
    FLIGHT_NUMBER,
    LAUNCH_YEAR,
    LAUNCH_DATE,
    ROCKET,
    SHIPS,
    LAUNCH_PLACE,
    LAUNCH_SUCCESS,
    LINK,
    IMAGE,
    PDF,
    DETAILS
}

fun ViewGroup.makeView(@LayoutRes layoutResId: Int): View =
    LayoutInflater.from(this.context).inflate(layoutResId, this, false)