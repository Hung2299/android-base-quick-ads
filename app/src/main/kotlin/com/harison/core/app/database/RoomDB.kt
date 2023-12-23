package com.harison.core.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harison.core.app.database.dao.SampleDao
import com.harison.core.app.database.entity.SampleEntity

@Database(
    entities = [SampleEntity::class],
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract fun sampleDB(): SampleDao
}