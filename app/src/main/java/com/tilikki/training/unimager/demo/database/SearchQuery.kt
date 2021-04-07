package com.tilikki.training.unimager.demo.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "searches", primaryKeys = ["photo_id", "search_query"])
data class SearchQuery(
    @ColumnInfo(name = "photo_id")
    val photoId: String,
    @ColumnInfo(name = "search_query")
    val searchQuery: String,
)

data class PhotoSearches(
    @Embedded val photo: EntityPhoto,
    @Relation(parentColumn = "id", entityColumn = "photo_id")
    val searchQuery: SearchQuery
)