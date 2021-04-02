package com.cornershop.counterstest.data.datasource.counter

import com.cornershop.counterstest.constant.*
import com.cornershop.counterstest.domain.model.counter.Counter
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface IApiCounter {
    @GET(WS_GET_COUNTERS)
    suspend fun getCounter(): List<Counter>

    @POST(WS_POST_COUNTER)
    suspend fun createCounter(
        @Body counter: Counter
    ): List<Counter>

    @POST(WS_POST_INC_COUNTER)
    suspend fun incrementCounter(
        @Body id: String
    ): List<Counter>

    @POST(WS_POST_DEC_COUNTER)
    suspend fun decrementCounter(
        @Body id: String
    ): List<Counter>

    @DELETE(WS_DELETE_COUNTER)
    suspend fun deleteCounter(
        @Body id: String
    ): List<Counter>
}