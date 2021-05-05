package com.seamfix.maontestapplication.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.data.model.entity.Team
import com.seamfix.maontestapplication.data.model.entity.TeamDetail
import com.seamfix.maontestapplication.data.source.local.dao.CompetitionDAO
import com.seamfix.maontestapplication.data.source.local.dao.TeamDAO
import com.seamfix.maontestapplication.data.source.local.dao.TeamDetailDAO

@Database(entities = [Competition::class, Team::class, TeamDetail::class], version = 1, exportSchema = true)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun competitionDao(): CompetitionDAO
    abstract fun teamDao(): TeamDAO
    abstract fun teamDetailDao(): TeamDetailDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }


    }


}