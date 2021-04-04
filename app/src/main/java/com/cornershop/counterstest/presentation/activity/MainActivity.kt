package com.cornershop.counterstest.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.cornershop.counterstest.CounterApp
import com.cornershop.counterstest.R
import com.cornershop.counterstest.presentation.viewmodel.counter.CounterViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var counterViewModel: CounterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val counterViewModelFactory = CounterApp.injectionContainer.counterViewModelFactory
        counterViewModel = ViewModelProvider(this.viewModelStore, counterViewModelFactory).get()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }
}