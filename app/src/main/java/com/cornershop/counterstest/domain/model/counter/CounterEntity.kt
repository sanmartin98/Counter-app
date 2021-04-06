package com.cornershop.counterstest.domain.model.counter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "counter")
data class CounterEntity (
    @PrimaryKey
    val id: String,
    val title: String? = null,
    val count: Int? = null,
    val isSelected: Boolean? = false
)