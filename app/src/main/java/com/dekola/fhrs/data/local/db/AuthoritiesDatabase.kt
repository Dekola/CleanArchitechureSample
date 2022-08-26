package com.dekola.fhrs.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dekola.fhrs.data.model.AuthoritiesEntity

@Database(entities = [AuthoritiesEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AuthoritiesDatabase : RoomDatabase() {
    abstract fun authoritiesDao(): AuthoritiesDao
}