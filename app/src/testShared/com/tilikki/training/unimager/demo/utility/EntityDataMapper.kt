package com.tilikki.training.unimager.demo.utility

import com.tilikki.training.unimager.demo.database.EntityPhoto
import com.tilikki.training.unimager.demo.model.Photo

fun List<Photo>.asDatabaseEntityPhotos(): List<EntityPhoto> {
    return map {
        it.toDatabaseEntityPhoto()
    }
}