package com.cornershop.counterstest.data.repository.counter

import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.data.datasource.counter.CounterDataSource
import com.cornershop.counterstest.data.datasource.counter.ICounterDataSource
import com.cornershop.counterstest.domain.model.counter.Counter

class CounterRepository(private val iCounterDataSource: ICounterDataSource) : ICounterRepository {
    override suspend fun getCounters(): Resource<List<Counter>> =
        iCounterDataSource.getCounters()

    override suspend fun createCounter(counter: Counter): Resource<List<Counter>> =
        iCounterDataSource.createCounter(counter = counter)

    override suspend fun incrementCounter(id: String): Resource<List<Counter>> =
        iCounterDataSource.incrementCounter(id = id)

    override suspend fun decrementCounter(id: String): Resource<List<Counter>> =
        iCounterDataSource.decrementCounter(id = id)

    override suspend fun deleteCounter(id: String): Resource<List<Counter>> =
        iCounterDataSource.deleteCounter(id = id)
}