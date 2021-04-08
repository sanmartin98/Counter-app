package com.cornershop.counterstest.presentation.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornershop.counterstest.R
import com.cornershop.counterstest.data.dataaccess.Resource
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
    private var counterList = mutableListOf<Counter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        counterAdapter = CounterAdapter()
        counterAdapter.iConnectAdapterModifyCounters = this

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
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
        setUpView()
        getCounters()
    }

    private fun setUpView() {
        binding.searchView.setOnQueryTextListener(this)
        binding.swipeRefresh.setColorSchemeResources(R.color.orange)
        binding.swipeRefresh.setOnRefreshListener { getCounters() }
        binding.buttonAddCounter.setOnClickListener {
            binding.buttonAddCounter.isEnabled = false
            findNavController().navigate(R.id.action_counterFragment_to_createCounterFragment)
        }
        binding.includeLayoutErrorLoadCounters.textViewRetryLoadCounters.setOnClickListener {
            getCounters()
        }
        binding.imageViewCloseSelectCounter.setOnClickListener {
            counterList.filter { it.isSelected!! }.forEach { it.isSelected = false }
            counterAdapter.setCounterList(counterList)
            binding.searchView.visibility = View.VISIBLE
            binding.constraintLayoutBarSelectedCounter.visibility = View.INVISIBLE
        }
        binding.imageViewDeleteCounter.setOnClickListener { showDeleteCounterAlertDialog(counterList.filter { it.isSelected!! }) }
        binding.imageViewShareCounter.setOnClickListener { shareCounters(counterList.filter { it.isSelected!! }) }
    }

    private fun getCounters() {
        counterViewModel.getCounters().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    lockScreen()
                    binding.includeProgressBar.relativeLayoutProgressBar.visibility = View.VISIBLE
                    binding.constraintLayoutCounters.visibility = View.GONE
                    hideAlertMessageViews()
                    binding.swipeRefresh.isRefreshing = false
                }
                is Resource.Success -> {
                    unLockScreen()
                    binding.includeProgressBar.relativeLayoutProgressBar.visibility = View.GONE
                    hideAlertMessageViews()
                    counterViewModel.updateCountersLocal(it.data)
                    if (it.data.isEmpty()) {
                        binding.includeLayoutNoCounterYet.constraintLayoutNoCounterYet.visibility =
                            View.VISIBLE
                    } else {
                        counterList.clear()
                        counterList.addAll(it.data)
                        setCounterAdapter()
                        setTotalItemsAndTimes()
                    }
                }
                is Resource.Failure -> {
                    unLockScreen()
                    getCountersLocal()
                }
            }
        })
    }

    private fun getCountersLocal() {
        counterList.clear()
        counterList.addAll(counterViewModel.getCountersLocal())
        binding.includeProgressBar.relativeLayoutProgressBar.visibility = View.GONE
        hideAlertMessageViews()
        if (counterList.isEmpty()) {
            binding.includeLayoutErrorLoadCounters.constraintLayoutErrorLoadCounters.visibility =
                View.VISIBLE
        } else {
            setTotalItemsAndTimes()
            setCounterAdapter()
        }
    }

    private fun hideAlertMessageViews() {
        binding.includeLayoutErrorLoadCounters.constraintLayoutErrorLoadCounters.visibility =
            View.GONE
        binding.includeLayoutNoCounterYet.constraintLayoutNoCounterYet.visibility =
            View.GONE
        binding.includeLayoutNoResultsCounters.constraintLayoutNoResultsCounters.visibility =
            View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalItemsAndTimes() {
        binding.textViewTotalCounters.text = "${counterList.size} items"
        binding.textViewTotalTimes.text = "${counterList.sumBy { it.count!! }} times"
    }

    private fun setCounterAdapter() {
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
                        lockScreen()
                        binding.includeProgressBar.relativeLayoutProgressBar.visibility =
                            View.VISIBLE
                    }
                    is Resource.Success -> {
                        unLockScreen()
                        binding.includeProgressBar.relativeLayoutProgressBar.visibility = View.GONE
                        updateData(it.data)
                    }
                    is Resource.Failure -> {
                        unLockScreen()
                        binding.includeProgressBar.relativeLayoutProgressBar.visibility = View.GONE
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
                        lockScreen()
                        binding.includeProgressBar.relativeLayoutProgressBar.visibility =
                            View.VISIBLE
                    }
                    is Resource.Success -> {
                        unLockScreen()
                        binding.includeProgressBar.relativeLayoutProgressBar.visibility = View.GONE
                        updateData(it.data)
                    }
                    is Resource.Failure -> {
                        unLockScreen()
                        binding.includeProgressBar.relativeLayoutProgressBar.visibility = View.GONE
                        showErrorUpdateCounterAlertDialog(
                            counter.count!! - 1,
                            counter,
                            ::decreaseCounter
                        )
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    override fun selectCounter(counter: Counter) {
        counterList.find { it.id.equals(counter.id) }?.isSelected = counter.isSelected?.not()
        counterAdapter.setCounterList(counterList = counterList)

        val sizeSelectedCounterList = counterList.count() { it.isSelected!! }
        if (sizeSelectedCounterList == 0) {
            binding.searchView.visibility = View.VISIBLE
            binding.constraintLayoutBarSelectedCounter.visibility = View.INVISIBLE
        } else {
            binding.searchView.visibility = View.INVISIBLE
            binding.constraintLayoutBarSelectedCounter.visibility = View.VISIBLE
        }
        binding.textViewCounterSelected.text = "$sizeSelectedCounterList selected"
    }

    private fun deleteCounters(counterList: List<Counter>) {
        val idCountersList = counterList.map { it.id!! }.toList()
        counterViewModel.deleteCounter(idCounterList = idCountersList).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    lockScreen()
                    binding.includeProgressBar.relativeLayoutProgressBar.visibility =
                        View.VISIBLE
                }
                is Resource.Success -> {
                    unLockScreen()
                    if (it.data.isEmpty()) {
                        binding.includeLayoutNoCounterYet.constraintLayoutNoCounterYet.visibility =
                            View.VISIBLE
                    }
                    binding.includeProgressBar.relativeLayoutProgressBar.visibility = View.GONE
                    updateData(it.data)
                    binding.searchView.visibility = View.VISIBLE
                    binding.constraintLayoutBarSelectedCounter.visibility = View.INVISIBLE
                }
                is Resource.Failure -> {
                    unLockScreen()
                    binding.includeProgressBar.relativeLayoutProgressBar.visibility = View.GONE
                    showErrorDeleteCounterAlertDialog()
                }
            }
        })
    }

    private fun shareCounters(counterList: List<Counter>) {
        var countersShareListString = ""
        counterList.map { countersShareListString += "${it.count.toString()} x ${it.title} \r" }
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, countersShareListString)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share))
        startActivity(shareIntent)
    }

    private fun showDeleteCounterAlertDialog(
        counterList: List<Counter>
    ) {
        var countersDeleteListString = ""
        counterList.map { countersDeleteListString += "\"${it.title}\" " }
        val builder = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle("Delete $countersDeleteListString?")
            .setPositiveButton(getString(R.string.delete)) { _, _ -> deleteCounters(counterList = counterList) }
            .setNegativeButton(getString(R.string.cancel), null)

        val alert = builder.create()
        alert.show()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        alert.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
        alert.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
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

    private fun showErrorDeleteCounterAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle(getString(R.string.error_deleting_counter_title))
            .setMessage(getString(R.string.connection_error_description))
            .setPositiveButton(getString(R.string.ok), null)

        val alert = builder.create()
        alert.show()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        alert.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
    }

    private fun updateData(data: List<Counter>) {
        counterList.clear()
        counterList.addAll(data)
        counterViewModel.updateCountersLocal(data)
        counterAdapter.setCounterList(data)
        setTotalItemsAndTimes()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val sizeListCounterFilter = counterAdapter.filter(newText!!)
        if (sizeListCounterFilter == 0) {
            binding.includeLayoutNoResultsCounters.constraintLayoutNoResultsCounters.visibility =
                View.VISIBLE
            binding.constraintLayoutCounters.visibility = View.GONE
        } else if (binding.includeLayoutNoResultsCounters.constraintLayoutNoResultsCounters.visibility == View.VISIBLE) {
            binding.includeLayoutNoResultsCounters.constraintLayoutNoResultsCounters.visibility =
                View.GONE
            binding.constraintLayoutCounters.visibility = View.VISIBLE
        }
        return false
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