package com.app.printers.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.app.printers.R
import com.app.printers.adapters.TonersListAdapter
import com.app.printers.databinding.FragmentTonersListBinding
import com.app.printers.model.Toner

class TonersListFragment : Fragment(), TonersListAdapter.OnClickListener {

    private lateinit var binding: FragmentTonersListBinding
    private val viewModel by lazy { ViewModelProvider(this)[TonersListViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentTonersListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adapter = TonersListAdapter(this)

        binding.fabAdd.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.TonerDetailsFragment)
        }

        viewModel.getAllToners().observe(viewLifecycleOwner){toners->
            toners?.let{
                binding.adapter!!.submitList(toners)
                binding.tonersList.isVisible = toners.isNotEmpty()
            }
        }

    }

    override fun onTonerClick(toner: Toner, position: Int) {
        val args = Bundle().apply {
            putInt("id", toner.id)
        }
        (activity as MainActivity).navController.navigate(R.id.TonerDetailsFragment, args)
    }

}