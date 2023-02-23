package com.app.printers.ui.toner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.printers.R
import com.app.printers.adapters.LocationsDetailedListAdapter
import com.app.printers.databinding.FragmentTonerDetailsBinding
import com.app.printers.model.Location
import com.app.printers.model.Toner
import com.app.printers.ui.main.MainActivity

class TonerDetailsFragment : Fragment(), LocationsDetailedListAdapter.OnClickListener,
    DialogLocations.OnLocationSelected {

    private lateinit var binding: FragmentTonerDetailsBinding
    private val viewModel by lazy { ViewModelProvider(this)[TonerDetailsViewModel::class.java] }
    private lateinit var dialogLocations: DialogLocations
    private var locations = ArrayList<Location>()
    private var currentToner = Toner()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentTonerDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adapter = LocationsDetailedListAdapter(this)

        arguments?.let{
            viewModel.getTonerById(it.getInt("id")).observe(viewLifecycleOwner){toner->
                toner?.let{
                    currentToner = toner
                    binding.tonerNameEdit.isEnabled = false
                    binding.tonerNameEdit.setTextColor(ContextCompat
                        .getColor(requireContext(),R.color.black))
                    setView()
                }
            }
        }

        if (arguments==null) {
            currentToner = Toner()
            setView()
        }

    }

    private fun setView(){

        locations.addAll(currentToner.locations)
        binding.adapter!!.submitList(locations.toMutableList())

        binding.toner = currentToner
        binding.executePendingBindings()


        binding.btnAddLocation.setOnClickListener {
            viewModel.getLocations().observe(viewLifecycleOwner){
                it?.let{
                    val locationsList = ArrayList<Location>()
                    it.forEach { location ->
                        var exist = false
                        locations.forEach{ l->
                            if (l.id==location.id){
                                exist = true
                            }
                        }
                        if (!exist){
                            locationsList.add(location)
                        }
                    }
                    dialogLocations = DialogLocations(requireContext(), locationsList, this)
                    dialogLocations.show()
                }
            }
        }


        binding.btnBack.setOnClickListener {
            back()
        }

        binding.btnDone.setOnClickListener {
            if (currentToner.name.isNotEmpty()) {
                currentToner.locations = locations
                viewModel.insertToner(currentToner)
            }
            back()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteToner(currentToner)
            back()
        }
    }

    private fun back() {
        (activity as MainActivity).navController.navigateUp()
    }

    override fun onLocationClick(location: Location, position: Int) {
        locations.remove(location)
        binding.adapter!!.submitList(locations.toMutableList())
        currentToner.count -= location.tonerCount
        binding.toner = currentToner
        binding.executePendingBindings()
    }

    override fun onTonerAdd() {
        currentToner.count++
        binding.toner = currentToner
        binding.executePendingBindings()
    }

    override fun onTonerRemove() {
        currentToner.count--
        binding.toner = currentToner
        binding.executePendingBindings()
    }

    override fun onLocationSelected(location: Location) {
        locations.add(location)
        binding.adapter!!.submitList(locations.toMutableList())
    }

}