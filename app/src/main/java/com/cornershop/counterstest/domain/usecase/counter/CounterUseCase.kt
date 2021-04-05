package com.cornershop.counterstest.domain.usecase.counter

import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.data.repository.counter.ICounterRepository
import com.cornershop.counterstest.domain.model.counter.Counter

class CounterUseCase(private val iCounterRepository: ICounterRepository): ICounterUseCase {
    override suspend fun getCounters(): Resource<List<Counter>> =
        iCounterRepository.getCounters()

    override suspend fun createCounter(titleCounter: Map<String, String>): Resource<List<Counter>> =
        iCounterRepository.createCounter(titleCounter = titleCounter)

    override suspend fun incrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        iCounterRepository.incrementCounter(id = id)

    override suspend fun decrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        iCounterRepository.decrementCounter(id = id)

    override suspend fun deleteCounter(id: Map<String, String>): Resource<List<Counter>> =
        iCounterRepository.deleteCounter(id = id)
}