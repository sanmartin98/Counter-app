package com.cornershop.counterstest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.RowExampleBinding
import com.cornershop.counterstest.presentation.interfaces.IConnectAdapterExampleCounters

class ExampleAdapter : RecyclerView.Adapter<ExampleAdapter.ViewHolder>() {

    private var exampleList = mutableListOf<String>()
    lateinit var iConnectAdapterExampleCounters: IConnectAdapterExampleCounters

    fun setExampleList(exampleList: Array<String>) {
        this.exampleList.clear()
        this.exampleList.addAll(exampleList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_example,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            iConnectAdapterExampleCounters.onClickExample(
                exampleList[position]
            )
        }
        holder.bind(item = exampleList[position])
    }

    override fun getItemCount(): Int = exampleList.size

    inner class ViewHolder(val binding: RowExampleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.example = item
        }
    }

}