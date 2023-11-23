package com.tilikki.training.unimager.demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [EntityPhoto::class, EntityUser::class, SearchQuery::class],
    version = 2,
)
abstract class RoomDB : RoomDatabase() {
    abstract val photosDao: PhotosDao
    abstract val userDao: UserDao

    companion object {
        private lateinit var INSTANCE: RoomDB

        fun getDatabase(context: Context): RoomDB {
            synchronized(RoomDB::class.java) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = RoomDB::class.java,
                        name = "unimager-db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}