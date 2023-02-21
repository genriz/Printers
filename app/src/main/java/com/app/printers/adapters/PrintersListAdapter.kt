package com.app.printers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.printers.databinding.PrintersListItemBinding
import com.app.printers.model.Printer

class PrintersListAdapter(private val listener: OnClickListener):
    ListAdapter<Printer, PrintersListAdapter.PrinterViewHolder>(Companion) {

    class PrinterViewHolder(val binding: PrintersListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<Printer>() {
        override fun areItemsTheSame(oldItem: Printer, newItem: Printer):
                Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Printer, newItem: Printer):
                Boolean = oldItem.name == newItem.name &&
                oldItem.manufacturer == newItem.manufacturer &&
                oldItem.model == newItem.model
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrinterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PrinterViewHolder(PrintersListItemBinding
            .inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: PrinterViewHolder, position: Int) {
        val printer = getItem(position)
        holder.binding.printer = printer
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            listener.onPrinterClick(printer, position)
        }
    }

    interface OnClickListener{
        fun onPrinterClick(printer: Printer, position: Int)
    }
}