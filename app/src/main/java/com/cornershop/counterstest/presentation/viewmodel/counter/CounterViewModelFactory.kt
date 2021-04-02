package com.cornershop.counterstest.presentation.viewmodel.counter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornershop.counterstest.domain.usecase.counter.ICounterUseCase
import java.lang.Exception

@Suppress("UNCHECKED_CAST")
class CounterViewModelFactory(private val iCounterUseCase: ICounterUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            if (modelClass.isAssignableFrom(CounterViewModel::class.java)){
                return CounterViewModel(iCounterUseCase = iCounterUseCase) as T
            }
        } catch (ex: Exception){
            Log.e("CounterViewModelFactory", ex.message!!)
        }

        throw IllegalAccessException("Unknown View Model Class")
    }

}