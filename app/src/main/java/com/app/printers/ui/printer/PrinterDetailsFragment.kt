package com.app.printers.ui.printer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.app.printers.adapters.TonersListAdapter
import com.app.printers.databinding.FragmentPrinterDetailsBinding
import com.app.printers.model.Location
import com.app.printers.model.Printer
import com.app.printers.model.Toner
import com.app.printers.ui.main.MainActivity
import com.app.printers.ui.toner.DialogLocations

class PrinterDetailsFragment : Fragment(), TonersListAdapter.OnClickListener,
    DialogToners.OnTonerSelected, DialogLocations.OnLocationSelected {

    private lateinit var binding: FragmentPrinterDetailsBinding
    private val viewModel by lazy { ViewModelProvider(this)[PrinterDetailsViewModel::class.java] }
    private lateinit var dialogToners: DialogToners
    private var toners = ArrayList<Toner>()
    private lateinit var dialogLocations: DialogLocations
    private var currentPrinter = Printer()

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
                    currentPrinter = printer
                    setView()
                }
            }
        }

        if (arguments==null) setView()

    }

    private fun setView() {

        viewModel.getAllPrinters().observe(viewLifecycleOwner){printers->
            printers?.let{
                val printerNameList = mutableListOf<String>()
                val printerManufactureList = mutableListOf<String>()
                val printerModelList = mutableListOf<String>()
                for (printer in printers){
                    printerNameList.add(printer.name)
                    printerManufactureList.add(printer.manufacturer)
                    printerModelList.add(printer.model)
                }
                val adapterName: ArrayAdapter<String> =
                    ArrayAdapter(requireContext(),
                        androidx.appcompat.R.layout.select_dialog_item_material,
                        printerNameList)
                val adapterManufacture: ArrayAdapter<String> =
                    ArrayAdapter(requireContext(),
                        androidx.appcompat.R.layout.select_dialog_item_material,
                        printerManufactureList)
                val adapterModel: ArrayAdapter<String> =
                    ArrayAdapter(requireContext(),
                        androidx.appcompat.R.layout.select_dialog_item_material,
                        printerModelList)
                binding.inputName.setAdapter(adapterName)
                binding.inputManufacture.setAdapter(adapterManufacture)
                binding.inputModel.setAdapter(adapterModel)
            }
        }

        toners.addAll(currentPrinter.toners)

        binding.adapter!!.submitList(toners.toMutableList())

        if (currentPrinter.location!=null){
            binding.btnLocation.isVisible = false
        }

        binding.printerLocation.setOnClickListener {
            viewModel.getLocations().observe(viewLifecycleOwner){locations->
                locations?.let{
                    dialogLocations = DialogLocations(requireContext(), locations, this)
                    dialogLocations.show()
                }
            }
        }

        binding.btnLocation.setOnClickListener {
            viewModel.getLocations().observe(viewLifecycleOwner){locations->
                locations?.let{
                    dialogLocations = DialogLocations(requireContext(), locations, this)
                    dialogLocations.show()
                }
            }
        }

        binding.printer = currentPrinter
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
            if (currentPrinter.name.isNotEmpty()||
                currentPrinter.manufacturer.isNotEmpty()||
                currentPrinter.model.isNotEmpty()) {
                currentPrinter.toners = toners
                viewModel.insertPrinter(currentPrinter)
            }
            back()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deletePrinter(currentPrinter)
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

    override fun onLocationSelected(location: Location) {
        binding.btnLocation.isVisible = false
        currentPrinter.location = location
        binding.printer = currentPrinter
        binding.executePendingBindings()
    }

}