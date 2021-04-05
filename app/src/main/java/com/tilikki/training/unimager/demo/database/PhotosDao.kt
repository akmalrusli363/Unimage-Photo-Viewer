package com.tilikki.training.unimager.demo.database

import androidx.room.*
import io.reactivex.Observable

@Dao
@TypeConverters(EntityTypeConverter::class)
interface PhotosDao {
    @Query("select * from photos")
    fun getPhotos(): Observable<List<EntityPhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<EntityPhoto>)

    @Query("SELECT * FROM photos WHERE search_query LIKE :query")
    fun getSearchResult(query: String): List<EntityPhoto>

    @Query("DELETE FROM photos WHERE search_query LIKE :query")
    fun deletePhotoResult(query: String)
}