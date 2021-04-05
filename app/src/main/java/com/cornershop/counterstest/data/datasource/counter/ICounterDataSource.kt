package com.cornershop.counterstest.data.datasource.counter

import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.domain.model.counter.Counter

interface ICounterDataSource {
    suspend fun getCounters(): Resource<List<Counter>>
    suspend fun createCounter(titleCounter: Map<String, String>): Resource<List<Counter>>
    suspend fun incrementCounter(id: Map<String, String>): Resource<List<Counter>>
    suspend fun decrementCounter(id: Map<String, String>): Resource<List<Counter>>
    suspend fun deleteCounter(id: Map<String, String>): Resource<List<Counter>>
}