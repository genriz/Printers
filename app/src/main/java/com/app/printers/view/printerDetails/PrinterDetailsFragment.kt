package com.app.printers.view.printerDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.app.printers.R
import com.app.printers.view.adapters.TonersListAdapter
import com.app.printers.databinding.FragmentPrinterDetailsBinding
import com.app.printers.model.Location
import com.app.printers.model.Printer
import com.app.printers.model.Toner
import com.app.printers.view.main.MainActivity
import com.app.printers.view.tonerDetails.DialogLocations
import com.app.printers.viewmodel.PrinterDetailsViewModel
import com.google.android.material.snackbar.Snackbar

class PrinterDetailsFragment : Fragment(), TonersListAdapter.OnClickListener,
    DialogToners.OnTonerSelected, DialogLocations.OnLocationSelected {

    private lateinit var binding: FragmentPrinterDetailsBinding
    private val viewModel by lazy { ViewModelProvider(this)[PrinterDetailsViewModel::class.java] }
    private lateinit var dialogToners: DialogToners
    private lateinit var dialogLocations: DialogLocations

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
                    viewModel.initCurrentPrinter(printer, true)
                    setView()
                }
            }
        }

        if (arguments==null) {
            viewModel.initCurrentPrinter(Printer(), false)
            setView()
        }

    }

    private fun setView() {

        viewModel.getAllPrinters().observe(viewLifecycleOwner){printers->
            printers?.let{
                binding.inputName.setAdapter(viewModel.getAdapterName(requireContext()))
                binding.inputManufacture.setAdapter(viewModel.getAdapterManufacture(requireContext()))
                binding.inputModel.setAdapter(viewModel.getAdapterModel(requireContext()))
            }
        }

        viewModel.getAllToners().observe(viewLifecycleOwner){toners->
            toners?.let{
                binding.adapter!!.submitList(viewModel.getPrinterToners(toners))
            }
        }

        if (viewModel.currentPrinter.location!=null){
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

        binding.printer = viewModel.currentPrinter
        binding.executePendingBindings()

        binding.btnAddToner.setOnClickListener {
            viewModel.getAllToners().observe(viewLifecycleOwner){allToners->
                allToners?.let{
                    dialogToners = DialogToners(requireContext(),
                        viewModel.getTonersList(allToners), this)
                    dialogToners.show()
                }
            }
        }

        binding.btnBack.setOnClickListener {
            back()
        }

        binding.btnDone.setOnClickListener {
            if (viewModel.validatePrinter()){
                back()
            } else {
                Snackbar.make(binding.btnDelete, R.string.printer_exist,
                    Snackbar.LENGTH_LONG).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deletePrinter()
            back()
        }

    }

    private fun back() {
        (activity as MainActivity).navController.navigateUp()
    }

    override fun onTonerClick(toner: Toner, position: Int) {
        viewModel.removeToner(toner)
        binding.adapter!!.submitList(viewModel.toners.toMutableList())
    }

    override fun onTonerSelected(toner: Toner) {
        viewModel.addToner(toner)
        binding.adapter!!.submitList(viewModel.toners.toMutableList())
    }

    override fun onLocationSelected(location: Location) {
        binding.btnLocation.isVisible = false
        viewModel.setPrinterLocation(location)
        binding.printer = viewModel.currentPrinter
        binding.executePendingBindings()
    }

}