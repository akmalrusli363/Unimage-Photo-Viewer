package com.tilikki.training.unimager.demo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotosDao {
    @Query("select * from photos")
    fun getPhotos(): LiveData<List<EntityPhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<EntityPhoto>)
}