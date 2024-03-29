package com.app.printers.data

import androidx.room.TypeConverter
import com.app.printers.model.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RoomConverter {
    @TypeConverter
    fun fromJsonToLocation(value: String?): Location? {
        return Gson().fromJson(value, Location::class.java)
    }

    @TypeConverter
    fun fromLocationToJson(answer: Location?): String? {
        return Gson().toJson(answer)
    }

    @TypeConverter
    fun fromJsonToListTonerIds(value: String?): List<Int?>? {
        val listType: Type = object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListTonerIdsToJson(list: List<Int?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromJsonToListLocation(value: String?): List<Location?>? {
        val listType: Type = object : TypeToken<List<Location?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListLocationToJson(list: List<Location?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}