package com.tilikki.training.unimager.demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [EntityPhoto::class], version = 1, exportSchema = false)
@TypeConverters(EntityTypeConverter::class)
abstract class RoomDB : RoomDatabase() {
    abstract val photosDao: PhotosDao

    companion object {
        private lateinit var INSTANCE: RoomDB

        fun getDatabase(context: Context): RoomDB {
            synchronized(RoomDB::class.java) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDB::class.java,
                        "photos"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}