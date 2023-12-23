package com.harison.core.app.di

import android.content.Context
import androidx.room.Room
import com.harison.core.app.database.RoomDB
import com.harison.core.app.database.dao.SampleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): RoomDB {
        return Room.databaseBuilder(context, RoomDB::class.java, "RoomDB")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideSampleDAO(roomDB: RoomDB): SampleDao {
        return roomDB.sampleDB()
    }
}