package com.cornershop.counterstest.dependenceinjection

import android.content.Context
import com.cornershop.counterstest.data.dataaccess.room.DataBase
import com.cornershop.counterstest.data.datasource.counter.local.CounterDataSourceLocal
import com.cornershop.counterstest.data.datasource.counter.local.ICounterDataSourceLocal
import com.cornershop.counterstest.data.datasource.counter.remote.CounterDataSourceRemote
import com.cornershop.counterstest.data.datasource.counter.remote.ICounterDataSourceRemote
import com.cornershop.counterstest.data.repository.counter.CounterRepository
import com.cornershop.counterstest.data.repository.counter.ICounterRepository
import com.cornershop.counterstest.domain.usecase.counter.CounterUseCase
import com.cornershop.counterstest.domain.usecase.counter.ICounterUseCase
import com.cornershop.counterstest.presentation.viewmodel.counter.CounterViewModelFactory

class InjectionContainer(context: Context) {

    //--Counter-----------------------------
    private val iCounterDAO = DataBase.getInstance(context = context).ICounterDAO()
    private val counterDataSourceLocal = CounterDataSourceLocal(iCounterDAO = iCounterDAO)
    private val iCounterDataSourceLocal: ICounterDataSourceLocal = counterDataSourceLocal

    private val counterDataSourceRemote = CounterDataSourceRemote()
    private val iCounterDataSourceRemote: ICounterDataSourceRemote = counterDataSourceRemote

    private val counterRepository = CounterRepository(
        iCounterDataSourceRemote = iCounterDataSourceRemote,
        iCounterDataSourceLocal = iCounterDataSourceLocal
    )
    private val iCounterRepository: ICounterRepository = counterRepository

    private val counterUseCase = CounterUseCase(iCounterRepository = iCounterRepository)

    private val iCounterUseCase: ICounterUseCase = counterUseCase
    val counterViewModelFactory = CounterViewModelFactory(iCounterUseCase = iCounterUseCase)

}