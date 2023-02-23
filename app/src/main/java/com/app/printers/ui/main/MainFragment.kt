package com.app.printers.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.printers.R
import com.app.printers.adapters.PagerMainAdapter
import com.app.printers.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pagerMain.adapter = PagerMainAdapter(requireActivity())
        TabLayoutMediator(binding.tabsMain, binding.pagerMain) { tab, position ->
            tab.text = when (position) {
                1 -> getString(R.string.printers)
                else -> getString(R.string.toners)
            }
        }.attach()

        binding.fabSettings.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.SettingsFragment)
        }
    }


}