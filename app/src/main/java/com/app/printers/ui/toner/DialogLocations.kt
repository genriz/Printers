package com.app.printers.ui.toner

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import com.app.printers.adapters.LocationsListAdapter
import com.app.printers.databinding.DialogLocationsBinding
import com.app.printers.model.Location

class DialogLocations(context: Context,
                      private val items: List<Location>,
                      private val listener: OnLocationSelected):
    Dialog(context), LocationsListAdapter.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogLocationsBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(this.attributes)
            layoutParams.width = (width * 0.8).toInt()
            layoutParams.height = (height * 0.8).toInt()
            this.attributes = layoutParams
            setDimAmount(0.5f)
        }

        binding.adapter = LocationsListAdapter(this)
        binding.adapter!!.submitList(items)

        binding.btnClose.setOnClickListener {
            dismiss()
        }

    }

    override fun onLocationClick(location: Location, position: Int) {
        listener.onLocationSelected(location)
        dismiss()
    }

    interface OnLocationSelected {
        fun onLocationSelected(location: Location)
    }
}