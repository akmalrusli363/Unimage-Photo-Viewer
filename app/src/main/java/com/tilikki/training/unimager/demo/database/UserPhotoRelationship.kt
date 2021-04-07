package com.tilikki.training.unimager.demo.database

import androidx.room.Embedded
import androidx.room.Relation

data class UserPhotoRelationship(
    @Embedded val user: EntityUser,
    @Relation(parentColumn = "id", entityColumn = "owner_id")
    val photos: List<EntityPhoto>
)
