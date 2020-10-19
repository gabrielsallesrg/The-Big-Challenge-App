package com.gsrg.tbc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gsrg.tbc.core.models.Challenge

@Database(
    entities = [Challenge::class],
    version = 1,
    exportSchema = false
)
abstract class TbcDatabase : RoomDatabase(), ITbcDatabase {

    companion object {

        @Volatile
        private var INSTANCE: TbcDatabase? = null

        fun getInstance(context: Context): TbcDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TbcDatabase::class.java,
                "TheBigChallenge.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}