package com.macieandrz.barbook.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilteredDrinkList(
    @Json(name = "drinks")
    val drinks: List<FilteredDrink>
)