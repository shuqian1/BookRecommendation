package com.thoughworks.bookrecommendation.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Maybe

@Dao
interface BookDao {

    @Query("select * from book where catalog_id = :catalogId limit :pageSize")
    fun getListByCondition(catalogId: Int, pageSize: Int): LiveData<List<DBBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBatch(dbBooks: List<DBBook>)

    @Query("delete from book where catalog_id = :catalogId")
    fun deleteByCatalog(catalogId: Int);
}