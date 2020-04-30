package com.thoughworks.bookrecommendation.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatalogDao {

    @Query("select * from catalog")
    fun getAll(): LiveData<List<DBCatalog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBatch(dbCatalogs: List<DBCatalog>)

    @Query("delete from catalog")
    fun deleteAll();
}