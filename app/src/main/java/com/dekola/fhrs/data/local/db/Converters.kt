package com.dekola.fhrs.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromListToString(stringList: List<String?>?): String {
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun fromStringToList(dataString: String?): List<String> {
        if (dataString == null || dataString.isEmpty()) {
            return mutableListOf()
        }
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(dataString, type)
    }

}