package com.tilikki.training.unimager.demo.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EntityPhoto::class], version = 2)
abstract class RoomDatabase : RoomDatabase() {
    abstract val photosDao: PhotosDao
}