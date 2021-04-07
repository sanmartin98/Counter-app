package com.cornershop.counterstest.data.datasource.counter.remote

import com.cornershop.counterstest.data.dataaccess.Resource
import com.cornershop.counterstest.domain.model.counter.Counter

interface ICounterDataSourceRemote {
    suspend fun getCounters(): Resource<List<Counter>>
    suspend fun createCounter(titleCounter: Map<String, String>): Resource<List<Counter>>
    suspend fun incrementCounter(id: Map<String, String>): Resource<List<Counter>>
    suspend fun decrementCounter(id: Map<String, String>): Resource<List<Counter>>
    suspend fun deleteCounter(idCounterList: List<String>): Resource<List<Counter>>
}