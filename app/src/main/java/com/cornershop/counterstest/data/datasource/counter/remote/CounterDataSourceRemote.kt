package com.cornershop.counterstest.data.datasource.counter.remote

import com.cornershop.counterstest.data.dataaccess.Resource
import com.cornershop.counterstest.data.dataaccess.retrofit.Retrofit
import com.cornershop.counterstest.domain.model.counter.Counter

class CounterDataSourceRemote : ICounterDataSourceRemote {
    private val retrofitInstance = Retrofit().getRetrofitInstance().create(IApiCounter::class.java)

    override suspend fun getCounters(): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.getCounter())

    override suspend fun createCounter(titleCounter: Map<String, String>): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.createCounter(titleCounter = titleCounter))

    override suspend fun incrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.incrementCounter(id = id))

    override suspend fun decrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.decrementCounter(id = id))

    override suspend fun deleteCounter(id: Map<String, String>): Resource<List<Counter>> =
        Resource.Success(retrofitInstance.deleteCounter(id = id))
}