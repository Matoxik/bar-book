package com.macieandrz.barbook.data.remote


import com.macieandrz.barbook.data.models.DrinkList
import com.macieandrz.barbook.data.models.FilteredDrinkList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkListApi {
    //https://www.thecocktaildb.com/api/json/v1/1/search.php?a=margarita

    @GET("search.php")
    suspend fun getDrinkList(
        @Query("s") drinkName: String,
    ) : Response<DrinkList>

    @GET("search.php")
    suspend fun getAllDrinkList(
        @Query("f") firstLetter: String,
    ) : Response<DrinkList>

    //Filter by alcohol
    @GET("filter.php")
    suspend fun getDrinkByAlcohol(
        @Query("a") alcohol: String,
    ) : Response<FilteredDrinkList>

    // Filter by category
    @GET("filter.php")
    suspend fun getDrinkByCategory(
        @Query("c") category: String,
    ) : Response<FilteredDrinkList>

    // Filter by ingredients
    @GET("filter.php")
    suspend fun getDrinkByIngredient(
        @Query("i") ingredient: String,
    ) : Response<FilteredDrinkList>

}

object DrinkListRemoteSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val api = retrofit.create(DrinkListApi::class.java)
}
