package com.app.printers.view.printerDetails

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import com.app.printers.view.adapters.TonersListAdapter
import com.app.printers.databinding.DialogTonersBinding
import com.app.printers.model.Toner

class DialogToners(context: Context,
                   private val items: List<Toner>,
                   private val listener: OnTonerSelected):
    Dialog(context), TonersListAdapter.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogTonersBinding.inflate(layoutInflater)
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

        binding.adapter = TonersListAdapter(this)
        binding.adapter!!.submitList(items)

        binding.btnClose.setOnClickListener {
            dismiss()
        }

    }

    override fun onTonerClick(toner: Toner, position: Int) {
        listener.onTonerSelected(toner)
        dismiss()
    }

    interface OnTonerSelected {
        fun onTonerSelected(toner: Toner)
    }
}