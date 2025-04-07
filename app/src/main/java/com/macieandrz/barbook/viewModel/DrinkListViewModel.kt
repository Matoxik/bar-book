package com.macieandrz.barbook.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.macieandrz.barbook.data.models.DrinkList
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
                Log.d("DEBUG", "Fetched drink data: $data")
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

    fun setCurrentDrink(currentDrink: String) {
        _currentDrink.update { currentDrink }
    }


}