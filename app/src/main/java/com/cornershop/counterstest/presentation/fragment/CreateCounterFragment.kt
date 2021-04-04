package com.cornershop.counterstest.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.FragmentCreateCounterBinding

class CreateCounterFragment : Fragment() {
    private lateinit var binding: FragmentCreateCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_create_counter, container, false
            )
        return binding.root
    }

}