package com.cornershop.counterstest.data.datasource.counter

import com.cornershop.counterstest.constant.WS_DELETE_COUNTER
import com.cornershop.counterstest.constant.WS_POST_COUNTER
import com.cornershop.counterstest.constant.WS_POST_DEC_COUNTER
import com.cornershop.counterstest.constant.WS_POST_INC_COUNTER
import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.domain.model.counter.Counter
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface ICounterDataSource {
    suspend fun getCounters(): Resource<List<Counter>>
    suspend fun createCounter(counter: Counter): Resource<List<Counter>>
    suspend fun incrementCounter(id: String): Resource<List<Counter>>
    suspend fun decrementCounter(id: String): Resource<List<Counter>>
    suspend fun deleteCounter(id: String): Resource<List<Counter>>
}