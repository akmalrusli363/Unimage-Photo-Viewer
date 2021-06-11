package com.tilikki.training.unimager.demo.util

import com.tilikki.training.unimager.demo.database.EntityPhoto
import com.tilikki.training.unimager.demo.database.PhotoSearches
import com.tilikki.training.unimager.demo.database.SearchQuery
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.model.NetworkPhoto

@JvmName("fromNetworkPhotoAsDomainEntityPhoto")
fun List<NetworkPhoto>.asDomainEntityPhotos(): List<Photo> {
    return map {
        it.toDomainEntityPhoto()
    }
}

@JvmName("fromNetworkPhotoAsDatabaseEntityPhotos")
fun List<NetworkPhoto>.asDatabaseEntityPhotos(): List<EntityPhoto> {
    return map {
        it.toDatabaseEntityPhoto()
    }
}

@JvmName("fromNetworkPhotoMapToSearchResults")
fun List<NetworkPhoto>.mapToSearchResults(searchQuery: String): List<SearchQuery> {
    return map {
        it.bindSearchQuery(searchQuery)
    }
}

@JvmName("fromEntityPhotoAsDomainEntityPhoto")
fun List<EntityPhoto>.asDomainEntityPhotos(): List<Photo> {
    return map {
        it.toDomainEntityPhoto()
    }
}

@JvmName("fromPhotoSearchesGetPhotos")
fun List<PhotoSearches>.getPhotos(): List<EntityPhoto> {
    return map {
        it.photo
    }
}