package com.beehomie.wallpaperjsonbuilder.local.typeConverters

import androidx.room.TypeConverter

class IntListConverter {

    @TypeConverter
    fun fromInt(value: String): List<Int> {
        return value.split(",").filter { it.isNotBlank() }.map { it.toInt() }
    }

    @TypeConverter
    fun toInt(value: List<Int>): String {
        return value.joinToString(",")
    }
}