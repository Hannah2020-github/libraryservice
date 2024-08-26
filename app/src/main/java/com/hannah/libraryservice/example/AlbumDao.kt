package com.hannah.libraryservice.example

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao{
    @Insert
    fun insertOne(album: Album)

    @Query("SELECT * FROM Album")
    fun getAllAlbums(): List<Album>

    @Query("SELECT * FROM Album JOIN Artist ON Album.artistId = Artist.id WHERE artistId = :aristId")
    fun findAlbumJoinArtist(aristId: String): List<Compound>
}
