package com.harison.core.app.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SampleDB")
class SampleEntity(
    @PrimaryKey(autoGenerate = true) val generateId: Int? = null,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "label") val label: String?,
    @ColumnInfo(name = "name") val name: String?
)