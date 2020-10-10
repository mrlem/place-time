package org.mrlem.placetime.ui.map

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import org.mrlem.placetime.core.domain.model.Place

class DiffPlaces(private val oldPlaces: List<Place>, private val newPlaces: List<Place>) {

    fun diff(callback: Callback) {
        val result = calculate()
        update(result, callback)
    }

    private fun calculate(): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldPlaces[oldItemPosition].uid == newPlaces[newItemPosition].uid

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldPlaces[oldItemPosition] == newPlaces[newItemPosition]

            override fun getOldListSize() = oldPlaces.size

            override fun getNewListSize() = newPlaces.size
        }, false)
    }

    private fun update(result: DiffUtil.DiffResult, callback: Callback) {
        result.dispatchUpdatesTo(object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {
                (0 until count).forEach { i ->
                    callback.onPlaceAdded(newPlaces[position + i])
                }
            }

            override fun onRemoved(position: Int, count: Int) {
                (0 until count).forEach { i ->
                    callback.onPlaceRemoved(oldPlaces[position + i])
                }
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {}

            override fun onMoved(fromPosition: Int, toPosition: Int) {}
        })
    }

    interface Callback {
        fun onPlaceAdded(place: Place)
        fun onPlaceRemoved(place: Place)
    }
}
