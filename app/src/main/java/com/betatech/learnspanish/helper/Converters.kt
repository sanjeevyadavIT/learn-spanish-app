package com.betatech.learnspanish.helper

import androidx.room.TypeConverter
import com.betatech.learnspanish.data.model.db.Example
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

object Converters{

    @TypeConverter
    fun fromStringToExampleList(value: String?): ArrayList<Example> {
        val listType = object : TypeToken<ArrayList<Example?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromExampleListToString(list: ArrayList<Example?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToList(value: String?): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListToString(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}