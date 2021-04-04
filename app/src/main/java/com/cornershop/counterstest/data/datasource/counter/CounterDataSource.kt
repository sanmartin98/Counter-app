package com.cornershop.counterstest.data.datasource.counter

import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.data.config.Retrofit
import com.cornershop.counterstest.domain.model.counter.Counter

class CounterDataSource : ICounterDataSource {
    private val retrofitInstance = Retrofit().getRetrofitInstance().create(IApiCounter::class.java)

    override suspend fun getCounters(): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.getCounter())

    override suspend fun createCounter(counter: Counter): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.createCounter(counter = counter))

    override suspend fun incrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.incrementCounter(id = id))

    override suspend fun decrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.decrementCounter(id = id))

    override suspend fun deleteCounter(id: Map<String, String>): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.deleteCounter(id = id))
}