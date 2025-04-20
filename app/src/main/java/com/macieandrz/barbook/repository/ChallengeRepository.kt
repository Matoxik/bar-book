package com.macieandrz.barbook.repository

import android.content.Context
import com.macieandrz.barbook.data.local.PlayerDao
import com.macieandrz.barbook.data.local.PlayerDb
import com.macieandrz.barbook.data.models.Player
import kotlinx.coroutines.flow.Flow

class ChallengeRepository(context: Context) {
    private val dao = PlayerDb.getInstance(context).playerDao()

    // Get data from local database
    fun getPlayerListByDrink(drink: String): Flow<List<Player>>{
        return dao.getPlayerListByDrink(drink)
    }

    suspend fun savePlayer(player: Player) {
        dao.insert(player)
    }

    suspend fun deletePlayersByDrink(drink: String) {
        dao.deletePlayersByDrink(drink)
    }

}