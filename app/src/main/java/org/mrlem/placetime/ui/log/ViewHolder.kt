package org.mrlem.placetime.ui.log

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_event.view.*
import org.mrlem.placetime.core.domain.model.EventAndPlace
import java.text.SimpleDateFormat
import java.util.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(event: EventAndPlace) {
        // TODO - improve that
        itemView.description.text = "${event.place.label} ${event.event.type}"
        itemView.time.text = formatter.format(Date(event.event.time))
    }

    companion object {
        private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    }
}
