package com.seamfix.maontestapplication.data.source.local

import androidx.annotation.Keep
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.seamfix.maontestapplication.data.model.common.Area
import com.seamfix.maontestapplication.data.model.common.Player
import com.seamfix.maontestapplication.data.model.response.competitions.CurrentSeason

const val EMPTY = ""
@Keep
class Converters {

    @TypeConverter
    fun areaToString(area: Area?): String {
        if(area == null) return EMPTY
        val gSon = Gson()
        return gSon.toJson(area)
    }

    @TypeConverter
    fun stringToArea(string: String): Area?{
        if(string == EMPTY) return null
        val gSon = Gson()
        return gSon.fromJson(string, Area::class.java)
    }


    @TypeConverter
    fun currentSeasonToString(currentSeason: CurrentSeason?): String {
        if(currentSeason == null) return EMPTY
        val gSon = Gson()
        return gSon.toJson(currentSeason)
    }

    @TypeConverter
    fun stringToCurrentSeason(string: String): CurrentSeason?{
        if(string == EMPTY) return null
        val gSon = Gson()
        return gSon.fromJson(string, CurrentSeason::class.java)
    }


    @TypeConverter
    fun listOfPlayersToString(list: List<Player>?): String {
        if(list == null) return EMPTY
        val gSon = Gson()
        return gSon.toJson(list)
    }

    @TypeConverter
    fun stringOfPlayersToList(string: String): List<Player>?{
        if(string == EMPTY) return null
        val gSon = Gson()
        return gSon.fromJson(string)
    }

    internal inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}