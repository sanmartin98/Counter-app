package com.cornershop.counterstest.data.repository.counter

import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.domain.model.counter.Counter

interface ICounterRepository {
    suspend fun getCounters(): Resource<List<Counter>>
    suspend fun createCounter(counter: Counter): Resource<List<Counter>>
    suspend fun incrementCounter(id: String): Resource<List<Counter>>
    suspend fun decrementCounter(id: String): Resource<List<Counter>>
    suspend fun deleteCounter(id: String): Resource<List<Counter>>
}