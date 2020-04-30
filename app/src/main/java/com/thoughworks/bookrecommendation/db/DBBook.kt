package com.thoughworks.bookrecommendation.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class DBBook(
    @PrimaryKey(autoGenerate=true)
    val uid: Int,
    @ColumnInfo(name = "catalog_id")var catalogId: Int,
    @ColumnInfo(name = "by_time")val bytime: String,
    @ColumnInfo(name = "catalog")val catalog: String,
    @ColumnInfo(name = "img")val img: String,
    @ColumnInfo(name = "online")val online: String,
    @ColumnInfo(name = "reading")val reading: String,
    @ColumnInfo(name = "sub1")val sub1: String,
    @ColumnInfo(name = "sub2")val sub2: String,
    @ColumnInfo(name = "tags")val tags: String,
    @ColumnInfo(name = "title")val title: String
)