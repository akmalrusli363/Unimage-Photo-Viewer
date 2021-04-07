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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResults(searchResults: List<SearchQuery>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: EntityPhoto)

    @Query("SELECT * FROM photos JOIN searches ON photos.id = searches.photo_id WHERE search_query LIKE :query")
    fun getPhotoSearchResult(query: String): List<PhotoSearches>
}