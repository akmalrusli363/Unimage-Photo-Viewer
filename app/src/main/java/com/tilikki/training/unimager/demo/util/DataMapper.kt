package com.tilikki.training.unimager.demo.util

import com.tilikki.training.unimager.demo.database.EntityPhoto
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.model.NetworkPhoto

@JvmName("fromNetworkPhotoAsDomainEntityPhoto")
fun List<NetworkPhoto>.asDomainEntityPhotos(): List<Photo> {
    return map {
        it.toDomainEntityPhoto()
    }
}

fun List<NetworkPhoto>.asDatabaseEntityPhotos(searchQuery: String): List<EntityPhoto> {
    return map {
        it.toDatabaseEntityPhoto(searchQuery)
    }
}

@JvmName("fromEntityPhotoAsDomainEntityPhoto")
fun List<EntityPhoto>.asDomainEntityPhotos(): List<Photo> {
    return map {
        it.toDomainEntityPhoto()
    }
}