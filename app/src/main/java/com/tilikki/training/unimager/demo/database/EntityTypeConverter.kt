package com.tilikki.training.unimager.demo.database

import androidx.room.TypeConverter
import java.util.*

object EntityTypeConverter {
    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromTimestamp(timestamp: Long): Date {
        return Date(timestamp)
    }
}