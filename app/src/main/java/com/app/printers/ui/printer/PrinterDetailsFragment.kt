package com.app.printers.ui.printer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.printers.adapters.TonersListAdapter
import com.app.printers.databinding.FragmentPrinterDetailsBinding
import com.app.printers.model.Printer
import com.app.printers.model.Toner
import com.app.printers.ui.main.MainActivity

class PrinterDetailsFragment : Fragment(), TonersListAdapter.OnClickListener,
    DialogToners.OnTonerSelected {

    private lateinit var binding: FragmentPrinterDetailsBinding
    private val viewModel by lazy { ViewModelProvider(this)[PrinterDetailsViewModel::class.java] }
    private lateinit var dialogToners: DialogToners
    private var toners = ArrayList<Toner>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentPrinterDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adapter = TonersListAdapter(this)

        arguments?.let{
            viewModel.getPrinterById(it.getInt("id")).observe(viewLifecycleOwner){printer->
                printer?.let{
                    setView(printer)
                }
            }
        }

        if (arguments==null) setView(Printer())

    }

    private fun setView(printer: Printer) {

        toners.addAll(printer.toners)

        binding.adapter!!.submitList(toners.toMutableList())

        binding.printer = printer
        binding.executePendingBindings()

        binding.btnAddToner.setOnClickListener {
            viewModel.getAllToners().observe(viewLifecycleOwner){
                it?.let{
                    val tonersList = ArrayList<Toner>()
                    it.forEach { toner ->
                        var exist = false
                        toners.forEach{ t->
                            if (t.id==toner.id){
                                exist = true
                            }
                        }
                        if (!exist){
                            tonersList.add(toner)
                        }
                    }
                    dialogToners = DialogToners(requireContext(), tonersList, this)
                    dialogToners.show()
                }
            }
        }

        binding.btnBack.setOnClickListener {
            back()
        }

        binding.btnDone.setOnClickListener {
            if (printer.name.isNotEmpty()||
                printer.manufacturer.isNotEmpty()||
                printer.model.isNotEmpty()) {
                printer.toners = toners
                viewModel.insertPrinter(printer)
            }
            back()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deletePrinter(printer)
            back()
        }

    }

    private fun back() {
        (activity as MainActivity).navController.navigateUp()
    }

    override fun onTonerClick(toner: Toner, position: Int) {
        toners.remove(toner)
        binding.adapter!!.submitList(toners.toMutableList())
    }

    override fun onTonerSelected(toner: Toner) {
        toners.add(toner)
        binding.adapter!!.submitList(toners.toMutableList())
    }

}