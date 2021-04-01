package com.tilikki.training.unimager.demo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
@TypeConverters(EntityTypeConverter::class)
interface PhotosDao {
    @Query("select * from photos")
    fun getPhotos(): LiveData<List<EntityPhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<EntityPhoto>)
}