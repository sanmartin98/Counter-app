package com.cornershop.counterstest.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.RowCounterBinding
import com.cornershop.counterstest.domain.model.counter.Counter
import com.cornershop.counterstest.presentation.interfaces.IConnectAdapterModifyCounters
import java.util.*

class CounterAdapter : RecyclerView.Adapter<CounterAdapter.ViewHolder>() {

    private var counterList = mutableListOf<Counter>()
    private var counterOriginalList = mutableListOf<Counter>()
    lateinit var iConnectAdapterModifyCounters: IConnectAdapterModifyCounters

    fun setCounterList(counterList: List<Counter>) {
        this.counterList.clear()
        this.counterList.addAll(counterList)
        counterOriginalList.addAll(this.counterList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterAdapter.ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_counter,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CounterAdapter.ViewHolder, position: Int) {
        holder.bind(item = counterList[position])
    }

    override fun getItemCount(): Int = counterList.size

    inner class ViewHolder(val binding: RowCounterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Counter) {
            binding.textViewTitleCounter.text = item.title
            if (item.isSelected!!) {
                binding.imageViewCounterSelected.visibility = View.VISIBLE
                binding.constraintLayoutModifyCounters.visibility = View.GONE
                binding.cardViewCounter.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.orange_select
                    )
                )
            } else {
                binding.imageViewCounterSelected.visibility = View.GONE
                binding.constraintLayoutModifyCounters.visibility = View.VISIBLE
                binding.cardViewCounter.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.transparent
                    )
                )
                binding.textViewCounter.text = item.count.toString()
                binding.imageViewPlusCounter.setOnClickListener {
                    iConnectAdapterModifyCounters.increaseCounter(item)
                }
            }

            if (item.count!! > 0) {
                binding.imagaViewLessCounter.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.orange
                    )
                )
                binding.imagaViewLessCounter.setOnClickListener {
                    iConnectAdapterModifyCounters.decreaseCounter(item)
                }
            } else {
                binding.imagaViewLessCounter.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gray
                    )
                )
                binding.imagaViewLessCounter.setOnClickListener {}
            }
        }
    }

    fun filter(strSearch: String): Int {
        if (strSearch.isEmpty()) {
            counterList.clear()
            counterList.addAll(counterOriginalList)
        } else {
            counterList.clear()
            for (i in counterOriginalList) {
                if (i.title?.toLowerCase(Locale.ROOT)!!.contains(strSearch)) {
                    counterList.add(i)
                }
            }

        }
        notifyDataSetChanged()
        return counterList.size
    }

}