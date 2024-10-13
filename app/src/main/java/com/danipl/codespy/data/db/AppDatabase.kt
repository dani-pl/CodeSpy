package com.danipl.codespy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserAppEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userAppEntityDao(): UserAppEntityDao
}