package com.cornershop.counterstest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.RowCategoryBinding
import com.cornershop.counterstest.domain.model.counter.ExampleCounterList
import com.cornershop.counterstest.presentation.interfaces.IConnectAdapterExampleCounters

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var categoryList = mutableListOf<ExampleCounterList>()
    private val viewPool = RecyclerView.RecycledViewPool()
    lateinit var iConnectAdapterExampleCounters: IConnectAdapterExampleCounters

    fun setExampleCounterList(exampleCounterList: List<ExampleCounterList>) {
        categoryList.clear()
        categoryList.addAll(exampleCounterList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item = categoryList[position])

        val exampleLayoutManager = LinearLayoutManager(
            holder.binding.recyclerViewExample.context,
            RecyclerView.HORIZONTAL,
            false
        )

        exampleLayoutManager.initialPrefetchItemCount = 4

        holder.binding.recyclerViewExample.apply {
            val exampleAdapter = ExampleAdapter()
            layoutManager = exampleLayoutManager
            adapter = exampleAdapter
            exampleAdapter.setExampleList(categoryList[position].exampleList)
            exampleAdapter.iConnectAdapterExampleCounters = iConnectAdapterExampleCounters
            setRecycledViewPool(viewPool)
        }

    }

    override fun getItemCount(): Int = categoryList.size

    inner class ViewHolder(val binding: RowCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExampleCounterList) {
            binding.categoryName = item.category
        }
    }
}