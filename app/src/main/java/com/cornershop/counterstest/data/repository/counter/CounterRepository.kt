package com.cornershop.counterstest.data.repository.counter

import com.cornershop.counterstest.data.dataaccess.Resource
import com.cornershop.counterstest.data.datasource.counter.local.ICounterDataSourceLocal
import com.cornershop.counterstest.data.datasource.counter.remote.ICounterDataSourceRemote
import com.cornershop.counterstest.domain.model.counter.Counter

class CounterRepository(
    private val iCounterDataSourceRemote: ICounterDataSourceRemote,
    private val iCounterDataSourceLocal: ICounterDataSourceLocal
) : ICounterRepository {
    override suspend fun getCounters(): Resource<List<Counter>> =
        iCounterDataSourceRemote.getCounters()

    override suspend fun createCounter(titleCounter: Map<String, String>): Resource<List<Counter>> =
        iCounterDataSourceRemote.createCounter(titleCounter = titleCounter)

    override suspend fun incrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        iCounterDataSourceRemote.incrementCounter(id = id)

    override suspend fun decrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        iCounterDataSourceRemote.decrementCounter(id = id)

    override suspend fun deleteCounter(idCounterList: List<String>): Resource<List<Counter>> =
        iCounterDataSourceRemote.deleteCounter(idCounterList = idCounterList)

    override fun updateCountersLocal(listCounters: List<Counter>) =
        iCounterDataSourceLocal.updateCountersLocal(listCounters = listCounters)

    override fun getCountersLocal(): List<Counter> = iCounterDataSourceLocal.getCountersLocal()
}