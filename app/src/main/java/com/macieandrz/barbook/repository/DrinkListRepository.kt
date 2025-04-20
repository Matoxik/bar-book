package com.macieandrz.barbook.repository

import android.content.Context
import com.macieandrz.barbook.data.models.DrinkList
import com.macieandrz.barbook.data.models.FilteredDrinkList
import com.macieandrz.barbook.data.remote.DrinkListRemoteSource
import retrofit2.Response

class DrinkListRepository(context: Context) {

    private val drinkListApi = DrinkListRemoteSource.api

    suspend fun loadDrinkList(drinkName: String): Response<DrinkList> {
        return drinkListApi.getDrinkList(drinkName)
    }

    suspend fun loadAllDrinkList(firstLetter: String): Response<DrinkList> {
        return drinkListApi.getAllDrinkList(firstLetter)
    }

    suspend fun loadFilteredDrinkByAlcohol(alcohol: String): Response<FilteredDrinkList> {
         return drinkListApi.getDrinkByAlcohol(alcohol)
    }

    suspend fun loadFilteredDrinkByCategory(category: String): Response<FilteredDrinkList> {
        return drinkListApi.getDrinkByCategory(category)
    }

    suspend fun loadFilteredDrinkByIngredient(ingredient: String): Response<FilteredDrinkList> {
        return drinkListApi.getDrinkByIngredient(ingredient)
    }


}