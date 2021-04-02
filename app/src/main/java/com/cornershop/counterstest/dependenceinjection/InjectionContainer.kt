package com.cornershop.counterstest.dependenceinjection

import com.cornershop.counterstest.data.datasource.counter.CounterDataSource
import com.cornershop.counterstest.data.datasource.counter.ICounterDataSource
import com.cornershop.counterstest.data.repository.counter.CounterRepository
import com.cornershop.counterstest.data.repository.counter.ICounterRepository
import com.cornershop.counterstest.domain.usecase.counter.CounterUseCase
import com.cornershop.counterstest.domain.usecase.counter.ICounterUseCase
import com.cornershop.counterstest.presentation.viewmodel.counter.CounterViewModelFactory

class InjectionContainer {

    //--Counter-----------------------------
    private val counterDataSource = CounterDataSource()
    private val iCounterDataSource: ICounterDataSource = counterDataSource
    private val counterRepository = CounterRepository(iCounterDataSource = iCounterDataSource)
    private val iCounterRepository: ICounterRepository = counterRepository
    private val counterUseCase = CounterUseCase(iCounterRepository = iCounterRepository)
    private val iCounterUseCase: ICounterUseCase = counterUseCase
    val counterViewModelFactory = CounterViewModelFactory(iCounterUseCase = iCounterUseCase)

}