package com.thoughworks.bookrecommendation.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catalog")
data class DBCatalog(
    @ColumnInfo(name = "catalog")val catalog: String,
    @PrimaryKey val id: Int
)