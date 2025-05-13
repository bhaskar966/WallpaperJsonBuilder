package com.beehomie.wallpaperjsonbuilder.local.typeConverters

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }?.toList()
    }

    @TypeConverter
    fun toList(list: List<String>?): String? {
        return list?.joinToString(separator = ",")
    }

}