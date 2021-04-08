package com.tilikki.training.unimager.demo.database

import androidx.room.Embedded
import androidx.room.Relation
import com.tilikki.training.unimager.demo.model.PhotoDetail

data class UserPhotoRelationship(
    @Embedded val user: EntityUser,
    @Relation(parentColumn = "id", entityColumn = "owner_id")
    val photos: List<EntityPhoto>
) {
    fun getPhotoDetail(): PhotoDetail {
        val photo = photos.first()
        return photoDatabaseEntityAsPhotoDetail(photo)
    }

    private fun photoDatabaseEntityAsPhotoDetail(photo: EntityPhoto): PhotoDetail {
        return PhotoDetail(
            id = photo.id,
            createdAt = photo.createdAt,
            width = photo.width,
            height = photo.height,
            color = photo.color,
            likes = photo.likes,
            description = photo.description,
            altDescription = photo.altDescription,
            thumbnailUrl = photo.thumbnailUrl,
            previewUrl = photo.previewUrl,
            fullSizeUrl = photo.fullSizeUrl,
            apiUrl = photo.detailUrl,
            htmlUrl = photo.webLinkUrl,
            user = user.toDomainEntityUser(),
        )
    }
}
