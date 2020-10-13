package org.mrlem.placetime.ui.log

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_event.view.*
import org.mrlem.placetime.core.domain.model.Event

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(event: Event) {
        // TODO - improve that
        itemView.description.text = "${event.placeUid} ${event.type}"
    }
}
