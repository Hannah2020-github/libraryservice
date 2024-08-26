package com.hannah.libraryservice.example

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ArtistDao {
    // CRUD => create, update, delete, read
    @Insert
    fun inserOne(arist: Artist)

    @Insert
    fun inserTwo(arist1: Artist, arist2: Artist)

    @Insert
    fun insertManyArtist(vararg artist: Artist)

    @Update
    fun updateOneArtist(arist: Artist)

    @Update
    fun updateManyArtist(vararg arist: Artist)

    @Delete
    fun deleteOneArtist(arist: Artist)

    @Query("SELECT * FROM Artist")
    fun getAllArtists():List<Artist>

    @Query("SELECT * FROM Artist WHERE name = :name")
    fun getArtistByName(name: String): Artist

    @Query("SELECT * FROM Artist WHERE age > :minAge")
    fun getArtistOrderThan(minAge: Int): List<Artist>
    
}