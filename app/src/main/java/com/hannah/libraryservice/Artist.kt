package com.hannah.libraryservice

import androidx.room.Entity
import androidx.room.PrimaryKey

// 藝術家，發行專輯
@Entity
data class Artist(
    @PrimaryKey val id: String,
    val name: String,
    val age: Int
)