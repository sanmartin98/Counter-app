package com.cornershop.counterstest.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.cornershop.counterstest.R
import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.databinding.FragmentCounterBinding
import com.cornershop.counterstest.presentation.viewmodel.counter.CounterViewModel


class CounterFragment : Fragment() {
    private lateinit var binding: FragmentCounterBinding
    private val counterViewModel: CounterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_counter, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        counterViewModel.getCounters().observe(viewLifecycleOwner, {
            when (it){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.data.size.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}