package com.cornershop.counterstest.data.datasource.counter.local

import androidx.room.*
import com.cornershop.counterstest.domain.model.counter.Counter
import com.cornershop.counterstest.domain.model.counter.CounterEntity

@Dao
interface ICounterDAO {

    @Transaction
    fun updateCounters(listCounters: List<CounterEntity>){
        deleteAll()
        listCounters.map { insertCounter(counter = it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCounter(counter: CounterEntity)

    @Query("SELECT * FROM counter")
    fun getCounters(): List<Counter>

    @Query("DELETE FROM counter")
    fun deleteAll()
}