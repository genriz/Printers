package com.app.printers.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.printers.model.Location
import com.app.printers.model.Printer
import com.app.printers.model.Toner

@Database(entities = [Printer::class, Toner::class, Location::class],
    version = 1, exportSchema = false)
abstract class DB: RoomDatabase() {

    abstract fun dao() : DAO

    companion object {

        @Volatile
        private var INSTANCE: DB? = null

        fun getDB() : DB {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(APP, DB::class.java, "DB")
                    .fallbackToDestructiveMigration()
                    .build()
                return INSTANCE!!
            }
        }
    }
}