package com.app.printers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.printers.databinding.TonerLocationsListItemBinding
import com.app.printers.model.Location

class LocationsDetailedListAdapter(private val listener: OnClickListener):
    ListAdapter<Location, LocationsDetailedListAdapter.LocationViewHolder>(Companion) {

    class LocationViewHolder(val binding: TonerLocationsListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location):
                Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Location, newItem: Location):
                Boolean = oldItem.fullName == newItem.fullName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LocationViewHolder(TonerLocationsListItemBinding
            .inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.binding.location = location
        holder.binding.executePendingBindings()

        holder.binding.btnAddToner.setOnClickListener {
            location.tonerCount++
            holder.binding.location = location
            holder.binding.executePendingBindings()
            listener.onTonerAdd()
        }

        holder.binding.btnRemoveToner.setOnClickListener {
            if (location.tonerCount>0){
                location.tonerCount--
                holder.binding.location = location
                holder.binding.executePendingBindings()
                listener.onTonerRemove()
            }
        }

        holder.itemView.setOnClickListener {
            listener.onLocationClick(location, position)
        }
    }

    interface OnClickListener{
        fun onLocationClick(location: Location, position: Int)
        fun onTonerAdd()
        fun onTonerRemove()
    }
}