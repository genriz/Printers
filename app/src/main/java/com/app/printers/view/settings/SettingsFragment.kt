package com.app.printers.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.printers.R
import com.app.printers.view.adapters.LocationsListAdapter
import com.app.printers.databinding.FragmentSettingsBinding
import com.app.printers.model.Location
import com.app.printers.view.main.MainActivity
import com.app.printers.viewmodel.SettingsViewModel

class SettingsFragment:Fragment(), LocationsListAdapter.OnClickListener {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by lazy { ViewModelProvider(this)[SettingsViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adapter = LocationsListAdapter(this)

        viewModel.getLocations().observe(viewLifecycleOwner){locations->
            locations?.let{
                binding.adapter!!.submitList(locations)
                binding.locationsList.isVisible = locations.isNotEmpty()
            }
        }

        binding.btnAddLocation.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.LocationDetailsFragment)
        }

        binding.btnBack.setOnClickListener {
            (activity as MainActivity).navController.navigateUp()
        }
    }

    override fun onLocationClick(location: Location, position: Int) {
        val args = Bundle().apply {
            putInt("id", location.id)
        }
        (activity as MainActivity).navController.navigate(R.id.LocationDetailsFragment, args)
    }
}