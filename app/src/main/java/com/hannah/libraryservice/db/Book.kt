package com.hannah.libraryservice.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = User::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("borrowTo"),
    onDelete = ForeignKey.SET_NULL
)
]  )
data class Book(
    @PrimaryKey val isbn: String,
    val bookName: String,
    // 借到哪裡去
    val borrowTo: String?
)
