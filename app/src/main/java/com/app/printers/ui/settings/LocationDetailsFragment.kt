package com.app.printers.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.printers.databinding.FragmentLocationDetailsBinding
import com.app.printers.model.Location
import com.app.printers.ui.main.MainActivity

class LocationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationDetailsBinding
    private val viewModel by lazy { ViewModelProvider(this)[SettingsViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentLocationDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            viewModel.getLocationById(it.getInt("id")).observe(viewLifecycleOwner){location->
                location?.let{
                    setView(location)
                }
            }
        }

        if (arguments==null) setView(Location())

    }

    private fun setView(location: Location) {

        binding.location = location
        binding.executePendingBindings()

        binding.btnBack.setOnClickListener {
            back()
        }

        binding.btnDone.setOnClickListener {
            if (location.office.isNotEmpty()||
                location.floor.isNotEmpty()||
                location.room.isNotEmpty()) {
                viewModel.insertLocation(location)
            }
            back()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteLocation(location)
            back()
        }

    }

    private fun back() {
        (activity as MainActivity).navController.navigateUp()
    }

}