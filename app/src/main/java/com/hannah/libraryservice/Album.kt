package com.hannah.libraryservice

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
@Entity(foreignKeys = [ForeignKey(
    entity = Artist::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("artistId"),
    onDelete = ForeignKey.CASCADE
    )])

data class Album(
    @PrimaryKey val albumId: String,
    val albumName: String,
    val artistId: String // foreign key
)