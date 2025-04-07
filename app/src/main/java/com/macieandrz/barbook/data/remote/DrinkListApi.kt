package com.macieandrz.barbook.data.remote


import com.macieandrz.barbook.data.models.DrinkList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkListApi {
    //https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita

    @GET("search.php")
    suspend fun getDrinkList(
        @Query("s") drinkName: String,
    ) : Response<DrinkList>

}

object DrinkListRemoteSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val api = retrofit.create(DrinkListApi::class.java)
}
