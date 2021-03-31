package com.tilikki.training.unimager.demo.util

import com.tilikki.training.unimager.demo.database.EntityPhoto
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.model.NetworkPhoto

fun List<NetworkPhoto>.asDomainEntityPhotos(): List<Photo> {
    return map {
        it.toDomainEntityPhoto()
    }
}

fun List<NetworkPhoto>.asDatabaseEntityPhotos(): List<EntityPhoto> {
    return map {
        it.toDatabaseEntityPhoto()
    }
}

fun List<EntityPhoto>.asEntityPhotos(): List<Photo> {
    return map {
        it.toDomainEntityPhoto()
    }
}