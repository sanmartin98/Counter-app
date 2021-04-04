package com.cornershop.counterstest.presentation.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.counterstest.R
import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.databinding.FragmentCounterBinding
import com.cornershop.counterstest.domain.model.counter.Counter
import com.cornershop.counterstest.presentation.adapter.CounterAdapter
import com.cornershop.counterstest.presentation.interfaces.IConnectAdapterModifyCounters
import com.cornershop.counterstest.presentation.viewmodel.counter.CounterViewModel


class CounterFragment : Fragment(), IConnectAdapterModifyCounters,
    android.widget.SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentCounterBinding
    private val counterViewModel: CounterViewModel by activityViewModels()
    private lateinit var counterAdapter: CounterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        counterAdapter = CounterAdapter()
        counterAdapter.iConnectAdapterModifyCounters = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_counter, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(this)
        binding.includeLayoutErrorLoadCounters.textViewRetryLoadCounters.setOnClickListener {
            getCounters()
        }
        getCounters()
    }

    private fun getCounters() {
        counterViewModel.getCounters().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.includeProgressBar.progressBar.visibility = View.VISIBLE
                    binding.constraintLayoutCounters.visibility = View.GONE
                    binding.includeLayoutErrorLoadCounters.constraintLayoutErrorLoadCounters.visibility =
                        View.GONE
                }
                is Resource.Success -> {
                    binding.includeProgressBar.progressBar.visibility = View.GONE
                    binding.includeLayoutErrorLoadCounters.constraintLayoutErrorLoadCounters.visibility =
                        View.GONE
                    binding.includeLayoutNoCounterYet.constraintLayoutNoCounterYet.visibility =
                        View.GONE
                    if (it.data.isEmpty()) {
                        binding.includeLayoutNoCounterYet.constraintLayoutNoCounterYet.visibility =
                            View.VISIBLE
                    } else {
                        setTotalItemsAndTimes(counterList = it.data)
                        setCounterAdapter(counterList = it.data)
                    }
                }
                is Resource.Failure -> {
                    binding.includeProgressBar.progressBar.visibility = View.GONE
                    binding.constraintLayoutCounters.visibility = View.GONE
                    binding.includeLayoutNoCounterYet.constraintLayoutNoCounterYet.visibility =
                        View.GONE
                    binding.includeLayoutErrorLoadCounters.constraintLayoutErrorLoadCounters.visibility =
                        View.VISIBLE
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalItemsAndTimes(counterList: List<Counter>) {
        binding.textViewTotalCounters.text = "${counterList.size} items"
        binding.textViewTotalTimes.text = "${counterList.sumBy { it.count!! }} times"

    }

    private fun setCounterAdapter(counterList: List<Counter>) {
        binding.constraintLayoutCounters.visibility = View.VISIBLE
        binding.reciclerViewCounter.layoutManager = LinearLayoutManager(requireContext())
        binding.reciclerViewCounter.adapter = counterAdapter
        counterAdapter.setCounterList(counterList)
    }

    override fun increaseCounter(counter: Counter) {
        counterViewModel.incrementCounter(id = mapOf("id" to counter.id!!))
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> {
                        binding.includeProgressBar.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.includeProgressBar.progressBar.visibility = View.GONE
                        counterAdapter.setCounterList(it.data)
                    }
                    is Resource.Failure -> {
                        binding.includeProgressBar.progressBar.visibility = View.GONE
                        showErrorUpdateCounterAlertDialog(
                            counter.count!! + 1,
                            counter,
                            ::increaseCounter
                        )
                    }
                }
            })
    }

    override fun decreaseCounter(counter: Counter) {
        counterViewModel.decrementCounter(id = mapOf("id" to counter.id!!))
            .observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> {
                        binding.includeProgressBar.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.includeProgressBar.progressBar.visibility = View.GONE
                        counterAdapter.setCounterList(it.data)
                    }
                    is Resource.Failure -> {
                        binding.includeProgressBar.progressBar.visibility = View.GONE
                        showErrorUpdateCounterAlertDialog(
                            counter.count!! - 1,
                            counter,
                            ::decreaseCounter
                        )
                    }
                }
            })
    }

    private fun showErrorUpdateCounterAlertDialog(
        valueToUpdate: Int,
        counter: Counter,
        funcRetry: (counter: Counter) -> Unit
    ) {
        val builder = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle(
                getString(
                    R.string.error_updating_counter_title,
                    counter.title,
                    valueToUpdate
                )
            )
            .setMessage(getString(R.string.connection_error_description))
            .setPositiveButton(getString(R.string.retry)) { _, _ -> funcRetry(counter) }
            .setNegativeButton(getString(R.string.dismiss), null)

        val alert = builder.create()
        alert.show()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        alert.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
        alert.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText!!.isNotEmpty()) {
            val sizeListCounter = counterAdapter.filter(newText)
            if (sizeListCounter == 0) {
                binding.includeLayoutNoResultsCounters.constraintLayoutNoResultsCounters.visibility =
                    View.VISIBLE
                binding.constraintLayoutCounters.visibility = View.GONE
            } else if (binding.includeLayoutNoResultsCounters.constraintLayoutNoResultsCounters.visibility == View.VISIBLE) {
                binding.includeLayoutNoResultsCounters.constraintLayoutNoResultsCounters.visibility =
                    View.GONE
                binding.constraintLayoutCounters.visibility = View.VISIBLE
            }
        }
        return false
    }
}