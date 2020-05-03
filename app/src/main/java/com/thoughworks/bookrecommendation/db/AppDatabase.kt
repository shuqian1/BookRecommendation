package com.thoughworks.bookrecommendation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DBCatalog::class, DBBook::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catalogDao(): CatalogDao
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        const val PAGE_SIZE = 20
        private const val DB_NAME = "book_recommendation"

        fun getDatabaseInstance(mContext: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabaseInstance(mContext).also {
                    instance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

    }
}