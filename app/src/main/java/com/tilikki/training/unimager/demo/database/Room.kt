package com.tilikki.training.unimager.demo.database

import android.content.Context
import androidx.room.Room

object Room {
    private lateinit var INSTANCE: RoomDatabase

    fun getDatabase(context: Context): RoomDatabase {
        synchronized(RoomDatabase::class.java) {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabase::class.java,
                    "photos"
                ).build()
            }
        }
        return INSTANCE
    }
}