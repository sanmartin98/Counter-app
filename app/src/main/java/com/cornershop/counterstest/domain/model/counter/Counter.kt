package com.cornershop.counterstest.domain.model.counter

import com.google.gson.annotations.SerializedName

data class Counter (
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("count")
    val count: Int? = null
)