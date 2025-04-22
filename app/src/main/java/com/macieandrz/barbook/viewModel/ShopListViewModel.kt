package com.macieandrz.barbook.viewModel

import android.app.Application
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.macieandrz.barbook.data.models.Drink
import com.macieandrz.barbook.data.models.DrinkList
import com.macieandrz.barbook.repository.DrinkListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

// Helper data classes for managing drinks and ingredients
@Parcelize
data class DrinkWithPortions(val drink: Drink, var portions: Int = 1) : Parcelable
@Parcelize
data class ShoppingIngredient(val name: String, val measure: String, var isChecked: Boolean = false) :
    Parcelable

class ShopListViewModel(
    app: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {
    private val repo = DrinkListRepository(app.applicationContext)
    private val _drinkList = MutableStateFlow<DrinkList?>(null)

    // UI States Using SavedStateHandle
    @OptIn(SavedStateHandleSaveableApi::class)
    var drinkName by savedStateHandle.saveable {
        mutableStateOf("")
    }
        private set

    @OptIn(SavedStateHandleSaveableApi::class)
    var selectedDrinks by savedStateHandle.saveable {
        mutableStateOf(listOf<DrinkWithPortions>())
    }

    @OptIn(SavedStateHandleSaveableApi::class)
    var shoppingIngredients by savedStateHandle.saveable {
        mutableStateOf(listOf<ShoppingIngredient>())
    }


    // Methods to update the state
    fun updateDrinkName(name: String) {
        drinkName = name
    }

    fun addDrink(drink: DrinkWithPortions) {
        if (selectedDrinks.none { it.drink.idDrink == drink.drink.idDrink }) {
            selectedDrinks = selectedDrinks + drink
            updateShoppingIngredients()
        }
    }

    fun removeDrink(drink: DrinkWithPortions) {
        selectedDrinks = selectedDrinks.filter { it.drink.idDrink != drink.drink.idDrink }
        updateShoppingIngredients()
    }

    fun updateDrinkPortions(drink: DrinkWithPortions, newPortions: Int) {
        selectedDrinks = selectedDrinks.map {
            if (it.drink.idDrink == drink.drink.idDrink) {
                it.copy(portions = newPortions)
            } else {
                it
            }
        }
        updateShoppingIngredients()
    }

    fun updateIngredientChecked(ingredient: ShoppingIngredient, isChecked: Boolean) {
        shoppingIngredients = shoppingIngredients.map {
            if (it.name == ingredient.name) {
                it.copy(isChecked = isChecked)
            } else {
                it
            }
        }
    }

    fun performFetchDrinkList() {
        if (drinkName.isBlank()) return

        viewModelScope.launch {
            try {
                val remote = repo.loadDrinkList(drinkName.trim().lowercase())
                if (remote.isSuccessful) {
                    val data = remote.body()
                    Log.d("DEBUG", "Fetched drink data: $drinkName")
                    if (data != null) {
                        _drinkList.update { data }

                        data.drinks?.firstOrNull()?.let { newDrink ->
                            addDrink(DrinkWithPortions(newDrink))
                        }

                    }
                }
                drinkName = ""
            } catch (e: Exception) {
                Toast.makeText(
                    getApplication(),
                    "Try connect to internet",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("DEBUG", "DrinkList API Request Failed", e)
            }
        }
    }

    private fun updateShoppingIngredients() {
        shoppingIngredients = calculateRequiredIngredients(selectedDrinks)
    }


    private fun calculateRequiredIngredients(selectedDrinks: List<DrinkWithPortions>): List<ShoppingIngredient> {
        val ingredientMap = mutableMapOf<String, MutableList<String>>()

        for (drinkWithPortions in selectedDrinks) {
            val drink = drinkWithPortions.drink
            val portions = drinkWithPortions.portions

            // Helper function to process an ingredient and its measure
            fun addIngredient(ingredient: String?, measure: String?) {
                if (!ingredient.isNullOrBlank() && ingredient != "null") {
                    val measureStr = when {
                        measure == null || measure == "null" -> "as needed"
                        portions > 1 -> "$portions x $measure"
                        else -> measure.toString()
                    }

                    val ingredientList = ingredientMap.getOrPut(ingredient) { mutableListOf() }
                    ingredientList.add(measureStr)
                }
            }

            // Process all ingredients for this drink
            addIngredient(drink.strIngredient1, drink.strMeasure1)
            addIngredient(drink.strIngredient2, drink.strMeasure2)
            addIngredient(drink.strIngredient3, drink.strMeasure3)
            addIngredient(drink.strIngredient4, drink.strMeasure4)
            addIngredient(drink.strIngredient5, drink.strMeasure5)
            addIngredient(drink.strIngredient6, drink.strMeasure6)
            addIngredient(drink.strIngredient7, drink.strMeasure7)
            addIngredient(drink.strIngredient8, drink.strMeasure8)
            addIngredient(drink.strIngredient9, drink.strMeasure9)
            addIngredient(drink.strIngredient10, drink.strMeasure10)
            addIngredient(drink.strIngredient11, drink.strMeasure11)
            addIngredient(drink.strIngredient12, drink.strMeasure12)
            addIngredient(drink.strIngredient13, drink.strMeasure13)
            addIngredient(drink.strIngredient14, drink.strMeasure14)
            addIngredient(drink.strIngredient15, drink.strMeasure15)
        }

        return ingredientMap.map { (name, measures) ->
            ShoppingIngredient(
                name = name,
                measure = measures.joinToString(", "),
                isChecked = shoppingIngredients.find { it.name == name }?.isChecked ?: false
            )
        }.sortedBy { it.name }
    }
}

