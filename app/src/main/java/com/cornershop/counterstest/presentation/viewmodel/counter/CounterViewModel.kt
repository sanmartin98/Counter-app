package com.cornershop.counterstest.presentation.viewmodel.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cornershop.counterstest.data.config.Resource
import com.cornershop.counterstest.domain.model.counter.Counter
import com.cornershop.counterstest.domain.usecase.counter.ICounterUseCase
import kotlinx.coroutines.Dispatchers

class CounterViewModel(private val iCounterUseCase: ICounterUseCase) : ViewModel() {

    fun getCounters() = liveData<Resource<List<Counter>>>(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(iCounterUseCase.getCounters())
        } catch (ex: Exception) {
            emit(Resource.Failure(ex))
        }
    }

    fun createCounter(counter: Counter) = liveData<Resource<List<Counter>>>(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(iCounterUseCase.createCounter(counter = counter))
        } catch (ex: Exception){
            emit(Resource.Failure(exception = ex))
        }
    }

    fun incrementCounter(id: Map<String, String>) = liveData<Resource<List<Counter>>>(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(iCounterUseCase.incrementCounter(id = id))
        } catch (ex: Exception){
            emit(Resource.Failure(exception = ex))
        }
    }

    fun decrementCounter(id: Map<String, String>) = liveData<Resource<List<Counter>>>(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(iCounterUseCase.decrementCounter(id = id))
        } catch (ex: Exception){
            emit(Resource.Failure(exception = ex))
        }
    }

    fun deleteCounter(id: Map<String, String>) = liveData<Resource<List<Counter>>>(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(iCounterUseCase.deleteCounter(id = id))
        } catch (ex: Exception){
            emit(Resource.Failure(exception = ex))
        }
    }

}