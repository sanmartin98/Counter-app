package com.cornershop.counterstest.presentation.fragment

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.databinding.FragmentCreateCounterBinding
import com.cornershop.counterstest.presentation.viewmodel.counter.CounterViewModel

class CreateCounterFragment : Fragment() {
    private lateinit var binding: FragmentCreateCounterBinding
    private val counterViewModel: CounterViewModel by activityViewModels()
    private lateinit var keyBoard: InputMethodManager

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        binding.imageViewCloseCreateCounter.setOnClickListener {
            binding.textInputCreateCounter.hideKeyboard()
            findNavController().popBackStack()
        }
        binding.textViewSaveCounter.setOnClickListener {
            binding.textInputCreateCounter.hideKeyboard()
            createCounter()
        }
    }

    private fun createCounter() {
        val titleCounter = binding.textInputCreateCounter.text!!.toString()
        if (titleCounter.isEmpty()) {
            binding.textInputCreateCounterContainer.error =
                getString(R.string.error_write_name_counter)
        } else {
            counterViewModel.createCounter(mapOf("title" to titleCounter)).observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBarCreateCounter.visibility = View.VISIBLE
                        binding.textViewSaveCounter.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        findNavController().popBackStack()
                    }
                    is Resource.Failure -> {
                        binding.progressBarCreateCounter.visibility = View.GONE
                        binding.textViewSaveCounter.visibility = View.VISIBLE
                        showErrorCreateCounterAlertDialog()
                    }
                }
            })
        }
    }

    private fun showErrorCreateCounterAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle(getString(R.string.error_creating_counter_title))
            .setMessage(getString(R.string.connection_error_description))
            .setPositiveButton(getString(R.string.ok), null)

        val alert = builder.create()
        alert.show()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        alert.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
    }

    private fun View.hideKeyboard() {
        keyBoard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyBoard.hideSoftInputFromWindow(windowToken, 0)
    }

}