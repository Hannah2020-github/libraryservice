package com.hannah.libraryservice

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Artist::class, Album::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getArtistDao(): ArtistDao
    abstract fun getAlbumDao(): AlbumDao
}