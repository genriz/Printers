package com.app.printers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.printers.databinding.TonersListItemBinding
import com.app.printers.model.Toner

class TonersListAdapter(private val listener: OnClickListener):
    ListAdapter<Toner, TonersListAdapter.TonerViewHolder>(Companion) {

    class TonerViewHolder(val binding: TonersListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<Toner>() {
        override fun areItemsTheSame(oldItem: Toner, newItem: Toner):
                Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Toner, newItem: Toner):
                Boolean = oldItem.name == newItem.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TonerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TonerViewHolder(TonersListItemBinding
            .inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: TonerViewHolder, position: Int) {
        val toner = getItem(position)
        holder.binding.toner = toner
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            listener.onTonerClick(toner, position)
        }
    }

    interface OnClickListener{
        fun onTonerClick(toner: Toner, position: Int)
    }
}