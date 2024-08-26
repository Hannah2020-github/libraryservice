package com.hannah.libraryservice.example

import androidx.room.Embedded

data class Compound(
    @Embedded val album: Album,
    @Embedded val artist: Artist
)