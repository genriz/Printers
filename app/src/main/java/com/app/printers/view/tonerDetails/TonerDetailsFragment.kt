package com.app.printers.view.tonerDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.printers.R
import com.app.printers.view.adapters.LocationsDetailedListAdapter
import com.app.printers.view.adapters.PrintersListAdapter
import com.app.printers.databinding.FragmentTonerDetailsBinding
import com.app.printers.model.Location
import com.app.printers.model.Printer
import com.app.printers.model.Toner
import com.app.printers.view.main.MainActivity
import com.app.printers.viewmodel.TonerDetailsViewModel
import com.google.android.material.snackbar.Snackbar

class TonerDetailsFragment : Fragment(), LocationsDetailedListAdapter.OnClickListener,
    DialogLocations.OnLocationSelected, PrintersListAdapter.OnClickListener {

    private lateinit var binding: FragmentTonerDetailsBinding
    private val viewModel by lazy { ViewModelProvider(this)[TonerDetailsViewModel::class.java] }
    private lateinit var dialogLocations: DialogLocations

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentTonerDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adapter = LocationsDetailedListAdapter(this)
        binding.adapter2 = PrintersListAdapter(this)

        arguments?.let{
            viewModel.getTonerById(it.getInt("id")).observe(viewLifecycleOwner){toner->
                toner?.let{
                    viewModel.initCurrentToner(toner, true)
                    binding.tonerNameEdit.isEnabled = false
                    binding.tonerNameEdit.setTextColor(ContextCompat
                        .getColor(requireContext(),R.color.black))
                    setView()
                }
            }
        }

        if (arguments==null) {
            viewModel.initCurrentToner(Toner(), false)
            setView()
        }

    }

    private fun setView(){

        binding.adapter!!.submitList(viewModel.currentLocations.toMutableList())

        viewModel.getAllPrinters().observe(viewLifecycleOwner){printers->
            printers?.let{
                binding.adapter2!!.submitList(viewModel.getCurrentPrinters(printers).toMutableList())
                binding.executePendingBindings()
            }
        }

        binding.toner = viewModel.currentToner
        binding.executePendingBindings()


        binding.btnAddLocation.setOnClickListener {
            viewModel.getLocations().observe(viewLifecycleOwner){locations->
                locations?.let{
                    dialogLocations = DialogLocations(requireContext(),
                        viewModel.getDialogLocationsList(locations), this)
                    dialogLocations.show()
                }
            }
        }


        binding.btnBack.setOnClickListener {
            back()
        }

        binding.btnDone.setOnClickListener {
            if (viewModel.validateToner()){
                back()
            } else {
                Snackbar.make(binding.btnDelete, R.string.toner_exist,
                    Snackbar.LENGTH_LONG).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteToner()
            back()
        }
    }

    private fun back() {
        (activity as MainActivity).navController.navigateUp()
    }

    override fun onLocationClick(location: Location, position: Int) {
        viewModel.removeLocation(location)
        binding.adapter!!.submitList(viewModel.currentLocations.toMutableList())
        binding.toner = viewModel.currentToner
        binding.executePendingBindings()
    }

    override fun onTonerAdd() {
        viewModel.addToner()
        binding.toner = viewModel.currentToner
        binding.executePendingBindings()
    }

    override fun onTonerRemove() {
        viewModel.removeToner()
        binding.toner = viewModel.currentToner
        binding.executePendingBindings()
    }

    override fun onLocationSelected(location: Location) {
        viewModel.addLocation(location)
        binding.adapter!!.submitList(viewModel.currentLocations.toMutableList())
    }

    override fun onPrinterClick(printer: Printer, position: Int) {}

}