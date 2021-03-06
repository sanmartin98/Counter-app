package com.cornershop.counterstest.domain.usecase.counter

import com.cornershop.counterstest.data.dataaccess.Resource
import com.cornershop.counterstest.data.repository.counter.ICounterRepository
import com.cornershop.counterstest.domain.model.counter.Counter

class CounterUseCase(private val iCounterRepository: ICounterRepository) : ICounterUseCase {
    override suspend fun getCounters(): Resource<List<Counter>> =
        iCounterRepository.getCounters()

    override suspend fun createCounter(titleCounter: Map<String, String>): Resource<List<Counter>> =
        iCounterRepository.createCounter(titleCounter = titleCounter)

    override suspend fun incrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        iCounterRepository.incrementCounter(id = id)

    override suspend fun decrementCounter(id: Map<String, String>): Resource<List<Counter>> =
        iCounterRepository.decrementCounter(id = id)

    override suspend fun deleteCounter(idCounterList: List<String>): Resource<List<Counter>> =
        iCounterRepository.deleteCounter(idCounterList = idCounterList)

    override fun updateCountersLocal(listCounters: List<Counter>) =
        iCounterRepository.updateCountersLocal(listCounters = listCounters)

    override fun getCountersLocal(): List<Counter> = iCounterRepository.getCountersLocal()
}