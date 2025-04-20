package com.macieandrz.barbook.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.macieandrz.barbook.data.models.Player

@Database(entities = [Player::class], version = 1)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao

}

object PlayerDb {
    private var db: PlayerDatabase? = null

    fun getInstance(context: Context): PlayerDatabase {
        if (db == null) {
            db = Room.databaseBuilder(
                context,
                PlayerDatabase::class.java,
                "player_database"
            )
                .fallbackToDestructiveMigration(false).build()
        }
        return db!!
    }


}