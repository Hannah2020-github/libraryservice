package com.hannah.libraryservice.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = Book::class,
    parentColumns = arrayOf("isbn"),
    childColumns = arrayOf("Borrowing1"),
    onDelete = ForeignKey.SET_NULL
), ForeignKey(
    entity = Book::class,
    parentColumns = arrayOf("isbn"),
    childColumns = arrayOf("Borrowing2"),
    onDelete = ForeignKey.SET_NULL
), ForeignKey(
    entity = Book::class,
    parentColumns = arrayOf("isbn"),
    childColumns = arrayOf("Borrowing3"),
    onDelete = ForeignKey.SET_NULL
), ForeignKey(
    entity = Book::class,
    parentColumns = arrayOf("isbn"),
    childColumns = arrayOf("Borrowing4"),
    onDelete = ForeignKey.SET_NULL
)])

data class User(
    @PrimaryKey val id: String,
    val fullName: String,
    // 可借四本書，未借則顯示 null.
    val Borrowing1: String?,
    val Borrowing2: String?,
    val Borrowing3: String?,
    val Borrowing4: String?
)