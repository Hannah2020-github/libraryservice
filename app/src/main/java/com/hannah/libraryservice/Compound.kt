package com.hannah.libraryservice

import androidx.room.Embedded

data class Compound(
    @Embedded val album: Album,
    @Embedded val artist: Artist
)