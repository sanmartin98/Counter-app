package com.cornershop.counterstest

import android.app.Application
import com.cornershop.counterstest.dependenceinjection.InjectionContainer

class CounterApp: Application() {
    companion object {
        lateinit var injectionContainer: InjectionContainer
    }

    override fun onCreate() {
        super.onCreate()
        injectionContainer = InjectionContainer(this)
    }

}