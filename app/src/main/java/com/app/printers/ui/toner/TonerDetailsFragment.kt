package com.app.printers.ui.toner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.printers.databinding.FragmentTonerDetailsBinding
import com.app.printers.model.Toner
import com.app.printers.ui.main.MainActivity

class TonerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTonerDetailsBinding
    private val viewModel by lazy { ViewModelProvider(this)[TonerDetailsViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentTonerDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            viewModel.getTonerById(it.getInt("id")).observe(viewLifecycleOwner){toner->
                toner?.let{
                    setView(toner)
                }
            }
        }

        if (arguments==null) setView(Toner())

    }

    private fun setView(currentToner: Toner){
        binding.toner = currentToner
        binding.executePendingBindings()

        binding.btnRemoveToner.setOnClickListener {
            if (currentToner.count>0){
                currentToner.count--
                binding.toner = currentToner
                binding.executePendingBindings()
            }
        }

        binding.btnAddToner.setOnClickListener {
            currentToner.count++
            binding.toner = currentToner
            binding.executePendingBindings()
        }

        binding.btnBack.setOnClickListener {
            back()
        }

        binding.btnDone.setOnClickListener {
            if (currentToner.name.isNotEmpty()) {
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

}