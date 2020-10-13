package org.mrlem.placetime.ui.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mrlem.placetime.R
import org.mrlem.placetime.core.domain.model.EventAndPlace

class EventsAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val events = mutableListOf<EventAndPlace>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount() = events.count()

    fun updateEvents(events: List<EventAndPlace>) {
        this.events.apply {
            clear()
            addAll(events)
        }
        notifyDataSetChanged()
    }
}
