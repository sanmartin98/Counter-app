package com.cornershop.counterstest.presentation.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.counterstest.R
import com.cornershop.counterstest.data.dataaccess.Resource
import com.cornershop.counterstest.databinding.FragmentExampleCounterBinding
import com.cornershop.counterstest.domain.model.counter.ExampleCounterList
import com.cornershop.counterstest.presentation.adapter.CategoryAdapter
import com.cornershop.counterstest.presentation.interfaces.IConnectAdapterExampleCounters
import com.cornershop.counterstest.presentation.viewmodel.counter.CounterViewModel

class ExampleCounterFragment : Fragment(), IConnectAdapterExampleCounters {
    private lateinit var binding: FragmentExampleCounterBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private var exampleCounterList = mutableListOf<ExampleCounterList>()
    private val counterViewModel: CounterViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryAdapter = CategoryAdapter()
        categoryAdapter.iConnectAdapterExampleCounters = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_example_counter, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExamples()
        binding.imageViewBackExampleCounter.setOnClickListener { findNavController().popBackStack() }
        binding.recyclerViewCategoryExample.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCategoryExample.adapter = categoryAdapter
        categoryAdapter.setExampleCounterList(exampleCounterList = exampleCounterList)
    }

    private fun getExamples(){
        val example1 = ExampleCounterList(category = "Drinks", exampleList = resources.getStringArray(R.array.drinks_array))
        val example2 = ExampleCounterList(category = "Food", exampleList = resources.getStringArray(R.array.food_array))
        val example3 = ExampleCounterList(category = "Misc", exampleList = resources.getStringArray(R.array.misc_array))
        exampleCounterList.addAll(listOf(example1, example2, example3))
    }

    override fun onClickExample(title: String) {
        counterViewModel.createCounter(mapOf("title" to title))
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> {
                        lockScreen()
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        unLockScreen()
                        binding.progressBar.visibility = View.GONE
                        findNavController().popBackStack(R.id.counterFragment, false)
                    }
                    is Resource.Failure -> {
                        unLockScreen()
                        binding.progressBar.visibility = View.GONE
                        showErrorCreateCounterAlertDialog()
                    }
                }
            })
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