package com.macieandrz.barbook.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.macieandrz.barbook.data.models.Player
import kotlinx.coroutines.flow.Flow


@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(player: Player)

    @Delete
    suspend fun delete(player: Player)

    @Update
    suspend fun update(player: Player)

    @Query("SELECT * FROM player_table WHERE drink=:drink")
    fun getPlayerListByDrink(drink: String): Flow<List<Player>>

    @Query("DELETE FROM player_table WHERE drink = :drink")
    suspend fun deletePlayersByDrink(drink: String)

}