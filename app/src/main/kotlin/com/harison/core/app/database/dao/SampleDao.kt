package com.harison.core.app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.harison.core.app.database.entity.SampleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SampleDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(entity: List<SampleEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: SampleEntity): Long?

    @Query("Select * From SampleDB where name=:string")
    fun getByName(string: String): Flow<SampleEntity>

}