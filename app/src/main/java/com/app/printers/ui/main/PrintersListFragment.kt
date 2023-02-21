package com.app.printers.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.app.printers.R
import com.app.printers.adapters.PrintersListAdapter
import com.app.printers.databinding.FragmentPrintersListBinding
import com.app.printers.model.Printer

class PrintersListFragment : Fragment(), PrintersListAdapter.OnClickListener {

    private lateinit var binding: FragmentPrintersListBinding
    private val viewModel by lazy { ViewModelProvider(this)[PrintersListViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentPrintersListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adapter = PrintersListAdapter(this)

        binding.fabAdd.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.PrinterDetailsFragment)
        }

        viewModel.getAllPrinters().observe(viewLifecycleOwner){printers->
            printers?.let{
                binding.adapter!!.submitList(printers)
                binding.printersList.isVisible = printers.isNotEmpty()
            }
        }
    }

    override fun onPrinterClick(printer: Printer, position: Int) {
        val args = Bundle().apply {
            putInt("id", printer.id)
        }
        (activity as MainActivity).navController.navigate(R.id.PrinterDetailsFragment, args)
    }

}