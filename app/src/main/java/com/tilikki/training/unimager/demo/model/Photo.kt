package com.tilikki.training.unimager.demo.model

import android.os.Parcel
import android.os.Parcelable
import com.tilikki.training.unimager.demo.database.EntityPhoto
import com.tilikki.training.unimager.demo.model.util.Resolution
import java.util.*

data class Photo(
    val id: String,
    val createdAt: Date,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int?,
    val description: String?,
    val altDescription: String?,
    val thumbnailUrl: String,
    val previewUrl: String,
    val fullSizeUrl: String,
    val apiUrl: String,
    val htmlUrl: String,
    val owner: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readString().toString(),
        createdAt = Date(parcel.readLong()),
        width = parcel.readInt(),
        height = parcel.readInt(),
        color = parcel.readString().toString(),
        likes = parcel.readInt() as? Int,
        description = parcel.readString(),
        altDescription = parcel.readString(),
        thumbnailUrl = parcel.readString().toString(),
        previewUrl = parcel.readString().toString(),
        fullSizeUrl = parcel.readString().toString(),
        apiUrl = parcel.readString().toString(),
        htmlUrl = parcel.readString().toString(),
        owner = parcel.readString().toString()
    )

    fun getResolution(): Resolution {
        return Resolution(width, height)
    }

    fun toDatabaseEntityPhoto(): EntityPhoto {
        return EntityPhoto(
            id = id,
            createdAt = createdAt,
            width = width,
            height = height,
            color = color,
            likes = likes,
            description = description,
            altDescription = altDescription,
            thumbnailUrl = thumbnailUrl,
            previewUrl = previewUrl,
            fullSizeUrl = fullSizeUrl,
            detailUrl = apiUrl,
            webLinkUrl = htmlUrl,
            owner = owner,
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeLong(createdAt.time)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(color)
        parcel.writeInt(likes ?: 0)
        parcel.writeString(description)
        parcel.writeString(altDescription)
        parcel.writeString(thumbnailUrl)
        parcel.writeString(previewUrl)
        parcel.writeString(fullSizeUrl)
        parcel.writeString(apiUrl)
        parcel.writeString(htmlUrl)
        parcel.writeString(owner)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }
}
