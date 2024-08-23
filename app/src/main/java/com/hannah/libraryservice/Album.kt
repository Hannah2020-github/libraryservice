package com.hannah.libraryservice

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(
    @PrimaryKey val albumId: String,
    val albumName: String,
    val artlistId: String
)