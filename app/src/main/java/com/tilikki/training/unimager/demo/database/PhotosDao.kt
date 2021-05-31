package com.tilikki.training.unimager.demo.database

import androidx.room.*

@Dao
@TypeConverters(EntityTypeConverter::class)
interface PhotosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<EntityPhoto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResults(searchResults: List<SearchQuery>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: EntityPhoto)

    @Transaction
    @Query("SELECT * FROM photos JOIN searches ON photos.id = searches.photo_id WHERE search_query LIKE :query")
    fun getPhotoSearchResult(query: String): List<PhotoSearches>

    @Query("SELECT * FROM photos WHERE id LIKE :photoId")
    fun getPhotoById(photoId: String): EntityPhoto

    @Transaction
    @Query("SELECT * FROM photos JOIN users ON photos.owner_id = users.id WHERE photos.id LIKE :photoId")
    fun getPhotoDetailById(photoId: String): UserPhotoRelationship
}