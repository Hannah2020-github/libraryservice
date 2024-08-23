package com.hannah.libraryservice

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Artist::class, Album::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private var db: AppDatabase? = null
        fun buildDatabase(context: Context): AppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(context, AppDatabase::class.java, "database.db").build()
            }
            return db!!
        }
    }

    abstract fun getArtistDao(): ArtistDao
    abstract fun getAlbumDao(): AlbumDao
}