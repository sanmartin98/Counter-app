package com.cornershop.counterstest.data.datasource.counter.local

import com.cornershop.counterstest.domain.model.counter.Counter
import com.cornershop.counterstest.domain.model.counter.CounterEntity

class CounterDataSourceLocal(private val iCounterDAO: ICounterDAO): ICounterDataSourceLocal {
    override fun updateCountersLocal(listCounters: List<Counter>) {
        val listCounterEntity: MutableList<CounterEntity> = mutableListOf()
        listCounters.map { listCounterEntity.add(CounterEntity(id = it.id!!, title = it.title, count = it.count)) }
        iCounterDAO.updateCounters(listCounters = listCounterEntity)
    }

    override fun getCountersLocal(): List<Counter> = iCounterDAO.getCounters()
}