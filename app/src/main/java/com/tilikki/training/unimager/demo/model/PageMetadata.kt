package com.tilikki.training.unimager.demo.model

import android.os.Parcel
import android.os.Parcelable

open class PageMetadata(
    var page: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt())

    open fun onEndOfDataAction() {}

    fun addPage() {
        page += 1
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(page)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        const val MAX_ITEMS_PER_PAGE = 30

        @JvmField
        val CREATOR = object : Parcelable.Creator<PageMetadata> {
            override fun createFromParcel(parcel: Parcel): PageMetadata {
                return PageMetadata(parcel)
            }

            override fun newArray(size: Int): Array<PageMetadata?> {
                return arrayOfNulls(size)
            }
        }
    }
}
