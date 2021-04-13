package com.cornershop.counterstest.presentation.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.data.dataaccess.Resource
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

    @SuppressLint("SetTextI18n")
    private fun setUpView() {
        binding.textViewHelpCreateCounter.text = getString(R.string.create_counter_disclaimer) + " "
        binding.textViewHelpCreateCounterExamples.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.textViewHelpCreateCounterExamples.text =
            getString(R.string.create_counter_disclaimer_example)
        binding.textViewHelpCreateCounterExamples.setOnClickListener {
            findNavController().navigate(R.id.action_createCounterFragment_to_exampleCounterFragment)
        }
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
            counterViewModel.createCounter(mapOf("title" to titleCounter))
                .observe(viewLifecycleOwner, {
                    when (it) {
                        is Resource.Loading -> {
                            lockScreen()
                            binding.progressBarCreateCounter.visibility = View.VISIBLE
                            binding.textViewSaveCounter.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            unLockScreen()
                            findNavController().popBackStack()
                        }
                        is Resource.Failure -> {
                            unLockScreen()
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
            .setPositiveButton(getString(R.string.ok_alert), null)

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

    private fun lockScreen() {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun unLockScreen() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}