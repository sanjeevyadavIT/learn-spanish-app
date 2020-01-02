package com.betatech.learnspanish.helper

import androidx.room.TypeConverter
import com.betatech.learnspanish.data.model.db.Example
import com.betatech.learnspanish.data.model.db.Examples
import com.betatech.learnspanish.data.model.db.Options
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class Converters{

    @TypeConverter
    fun fromStringToExamples(value: String?): Examples {
        val listType = object : TypeToken<Example?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromExamplesToString(data: Examples?): String {
        val gson = Gson()
        return gson.toJson(data)
    }


    @TypeConverter
    fun fromStringToOptions(value: String?): Options {
        val listType = object : TypeToken<Options?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromOptionsToString(options: Options?): String {
        val gson = Gson()
        return gson.toJson(options)
    }
}