package com.macieandrz.barbook.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.macieandrz.barbook.data.models.Drink
import com.macieandrz.barbook.data.models.DrinkList
import com.macieandrz.barbook.data.models.FilteredDrinkList
import com.macieandrz.barbook.data.models.toDrink
import com.macieandrz.barbook.repository.DrinkListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DrinkListViewModel(app: Application) : AndroidViewModel(app){
    private val repo = DrinkListRepository(app.applicationContext)

    private val _drinkList = MutableStateFlow<DrinkList?>(null)
    val drinkList = _drinkList.asStateFlow()

    private val _currentDrink = MutableStateFlow<String?>(null)
    val currentDrink = _currentDrink.asStateFlow()


    fun performFetchDrinkList(drinkName: String) = viewModelScope.launch {

        try {
            val remote = repo.loadDrinkList(drinkName.trim().lowercase())
            if (remote.isSuccessful) {
                val data = remote.body()
                Log.d("DEBUG", "Fetched drink data: drinkName")
                if (data != null) {
                   _drinkList.update { data }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(getApplication<Application>(), "Try connect to internet",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("DEBUG", "DrinkList API Request Failed", e)
        }
    }

    //loadAllDrinkList
    fun performFetchAllDrinkList(firstLetter: String) = viewModelScope.launch {
        try {
            val remote = repo.loadAllDrinkList(firstLetter.trim().lowercase())
            if (remote.isSuccessful) {
                val data = remote.body()
                Log.d("DEBUG", "Fetched drink data: firstLetter ")
                if (data != null) {
                    _drinkList.update { data }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(getApplication<Application>(), "Try connect to internet",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("DEBUG", "DrinkList API Request Failed", e)
        }
    }

// Load filtered list of drinks
fun performFetchFilteredDrinkList(selectedAlcoholFromUI: String?, selectedCategory: String?, selectedIngredient: String?) = viewModelScope.launch {

    val selectedAlcohol: String? = when(selectedAlcoholFromUI) {
        "Yes" -> "Alcoholic"
        "No" -> "Non alcoholic"
        "Optional" -> "Optional alcohol"
        else -> null
    }

    var alcoholData: FilteredDrinkList? = null
    var categoryData: FilteredDrinkList? = null
    var ingredientData: FilteredDrinkList? = null

    // Get filtered data
    try {
        if (selectedAlcohol != null) {
            val alcoholRemote = repo.loadFilteredDrinkByAlcohol(selectedAlcohol)
            if (alcoholRemote.isSuccessful){
                alcoholData = alcoholRemote.body()
                Log.d("DEBUG", "Fetched drink alcohol data ")
            }
        }

        if (selectedCategory != null && selectedCategory != "None"){
            val categoryRemote = repo.loadFilteredDrinkByCategory(selectedCategory)
            if (categoryRemote.isSuccessful){
                categoryData = categoryRemote.body()
                Log.d("DEBUG", "Fetched drink category data")
            }
        }

        if (selectedIngredient != null && selectedIngredient != "None"){
            val ingredientRemote = repo.loadFilteredDrinkByIngredient(selectedIngredient)
            if (ingredientRemote.isSuccessful){
               ingredientData = ingredientRemote.body()
                Log.d("DEBUG", "Fetched drink ingredient data")
            }
        }

        // Find intersection of filtered data
        val dataSets = listOfNotNull(alcoholData, categoryData, ingredientData)

        val filteredDrinks = when {
            dataSets.isEmpty() -> emptyList() // No data
            dataSets.size == 1 -> dataSets[0].drinks // Only one dataset
            else -> {
                // Convert each drink list to a set of IDs
                val drinkIdSets = dataSets.map { it.drinks.map { drink -> drink.idDrink }.toSet() }

                // Find common IDs using intersect function
                val commonIds = drinkIdSets.reduce { acc, ids -> acc.intersect(ids) }

                dataSets[0].drinks.filter { it.idDrink in commonIds }
            }
        }

        // Convert FilteredDrink objects to Drink objects using the extension function
        val convertedDrinks = filteredDrinks.map { it.toDrink() }

        // Create a DrinkList with the converted drinks and update _drinkList
        val drinkList = DrinkList(convertedDrinks)
        _drinkList.update { drinkList }



    } catch (e: Exception) {
        Toast.makeText(getApplication<Application>(), "Try connect to internet",
            Toast.LENGTH_SHORT
        ).show()
        Log.e("DEBUG", "DrinkList API Request Failed", e)
    }
}

    fun setCurrentDrink(currentDrink: String) {
        _currentDrink.update { currentDrink }
        performFetchDrinkList(currentDrink)
    }



}