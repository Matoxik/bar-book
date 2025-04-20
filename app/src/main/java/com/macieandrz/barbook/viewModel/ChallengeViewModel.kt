package com.macieandrz.barbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.macieandrz.barbook.data.models.Player
import com.macieandrz.barbook.repository.ChallengeRepository
import kotlinx.coroutines.launch

class ChallengeViewModel(app: Application) : AndroidViewModel(app){
    val repo = ChallengeRepository(app.applicationContext)


    fun savePlayer(player: Player) {
        viewModelScope.launch {
            repo.savePlayer(player)
        }
    }


    fun deletePlayersByDrink(drink: String) {
        viewModelScope.launch {
            repo.deletePlayersByDrink(drink)
        }
    }


}