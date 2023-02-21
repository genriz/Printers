package com.app.printers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.printers.databinding.LocationsListItemBinding
import com.app.printers.model.Location

class LocationsListAdapter(private val listener: OnClickListener):
    ListAdapter<Location, LocationsListAdapter.LocationViewHolder>(Companion) {

    class LocationViewHolder(val binding: LocationsListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location):
                Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Location, newItem: Location):
                Boolean = oldItem.fullName == newItem.fullName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LocationViewHolder(LocationsListItemBinding
            .inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.binding.location = location
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            listener.onLocationClick(location, position)
        }
    }

    interface OnClickListener{
        fun onLocationClick(location: Location, position: Int)
    }
}