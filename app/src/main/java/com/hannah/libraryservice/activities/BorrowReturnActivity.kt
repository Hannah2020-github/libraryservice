package com.hannah.libraryservice.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.hannah.libraryservice.R
import com.hannah.libraryservice.db.AppDatabase
import com.hannah.libraryservice.db.Book
import com.hannah.libraryservice.db.User
import com.hannah.libraryservice.util.IdUtil
import com.hannah.libraryservice.util.IdUtil.Companion.checkUserId
import com.hannah.libraryservice.util.IsbnUtil.Companion.checkIsbn
import java.util.concurrent.Executors

class BorrowReturnActivity : AppCompatActivity() {
    private lateinit var bookISBN: EditText
    private lateinit var userID: EditText
    private lateinit var borrowBtn: Button
    private lateinit var returnBtn: Button
    private lateinit var result: TextView

    private val singleThreadExecutor =  Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.borrow_return_layout)

        bookISBN = findViewById(R.id.bookISBN_for_borrow)
        userID = findViewById(R.id.userId_for_borrow)
        borrowBtn = findViewById(R.id.borrow_btn)
        returnBtn = findViewById(R.id.return_btn)
        result = findViewById(R.id.borrow_return_result)

        // 借書
        borrowBtn.setOnClickListener {
            val bookIsbn = bookISBN.text.toString()
            val userId = userID.text.toString()

            if (!checkIsbn(bookIsbn, this)) {
                return@setOnClickListener
            }
            if (!checkUserId(userId, this)) {
                return@setOnClickListener
            }

            singleThreadExecutor.execute {
                try {
                    val db = AppDatabase.buildDatabase(this)
                    val userDao = db.getUserDao()
                    val user = userDao.getUserById(userId.uppercase())
                    if (user == null) {
                        runOnUiThread {
                            result.text = "User not found. Borrow failed."
                        }
                        return@execute
                    }

                    val bookDao = db.getBookDao()
                    val book = bookDao.getBookByISBN(bookIsbn)
                    if (book == null) {
                        runOnUiThread {
                            result.text = "Book not found. Borrow failed."
                        }
                        return@execute
                    }

                    if (user.Borrowing1 != null &&
                        user.Borrowing2 != null &&
                        user.Borrowing3 != null &&
                        user.Borrowing4 != null ) {
                        runOnUiThread {
                            result.text = "User has reached the book borrowing limit. Borrow failed."
                        }
                        return@execute
                    }

                    if (book.borrowTo != null) {
                        runOnUiThread {
                            result.text = "This book is borrowed to someone else already. Borrow failed."
                        }
                        return@execute
                    }

                    if (user.Borrowing1 == null) {
                        userDao.updateOneUser(
                            User(
                                user.id,
                                user.fullName,
                                bookIsbn,
                                user.Borrowing2,
                                user.Borrowing3,
                                user.Borrowing4
                            )
                        )
                    }else if (user.Borrowing2 == null) {
                        userDao.updateOneUser(
                            User(
                                user.id,
                                user.fullName,
                                user.Borrowing1,
                                bookIsbn,
                                user.Borrowing3,
                                user.Borrowing4
                            )
                        )
                    }else if (user.Borrowing3 == null) {
                        userDao.updateOneUser(
                            User(
                                user.id,
                                user.fullName,
                                user.Borrowing1,
                                user.Borrowing2,
                                bookIsbn,
                                user.Borrowing4
                            )
                        )
                    } else if (user.Borrowing4 == null) {
                        userDao.updateOneUser(
                            User(
                                user.id,
                                user.fullName,
                                user.Borrowing1,
                                user.Borrowing2,
                                user.Borrowing3,
                                bookIsbn
                            )
                        )
                    }
                    bookDao.updateOneBook(Book(book.isbn, book.bookName, user.id))
                    runOnUiThread {
                        result.text = "Borrow success!"
                        bookISBN.setText("")
                        userID.setText("")
                    }
                }catch (e: Exception) {
                    result.text = e.message
                    e.printStackTrace()
                }
            }
        }

        // 還書
        returnBtn.setOnClickListener {
            val bookIsbn = bookISBN.text.toString()
            val userId = userID.text.toString()

            if (!checkIsbn(bookIsbn, this)) {
                return@setOnClickListener
            }
            if (!checkUserId(userId, this)) {
                return@setOnClickListener
            }

            singleThreadExecutor.execute {
                try {
                    val db = AppDatabase.buildDatabase(this)
                    val userDao = db.getUserDao()
                    val user = userDao.getUserById(userId.uppercase())
                    if (user == null) {
                        runOnUiThread {
                            result.text = "User not found. Return failed."
                        }
                        return@execute
                    }

                    val bookDao = db.getBookDao()
                    val book = bookDao.getBookByISBN(bookIsbn)
                    if (book == null) {
                        runOnUiThread {
                            result.text = "Book not found. Return failed."
                        }
                        return@execute
                    }

                    if (user.Borrowing1 != bookIsbn &&
                        user.Borrowing2 != bookIsbn &&
                        user.Borrowing3 != bookIsbn &&
                        user.Borrowing4 != bookIsbn ) {
                        runOnUiThread {
                            result.text = "User didn't borrow this book. Return failed."
                        }
                        return@execute
                    }

                    if (user.Borrowing1 == bookIsbn) {
                        userDao.updateOneUser(
                            User(
                                user.id,
                                user.fullName,
                                null,
                                user.Borrowing2,
                                user.Borrowing3,
                                user.Borrowing4
                            )
                        )
                    }else if (user.Borrowing2 == bookIsbn) {
                        userDao.updateOneUser(
                            User(
                                user.id,
                                user.fullName,
                                user.Borrowing1,
                                null,
                                user.Borrowing3,
                                user.Borrowing4
                            )
                        )
                    }else if (user.Borrowing3 == bookIsbn) {
                        userDao.updateOneUser(
                            User(
                                user.id,
                                user.fullName,
                                user.Borrowing1,
                                user.Borrowing2,
                                null,
                                user.Borrowing4
                            )
                        )
                    } else if (user.Borrowing4 == bookIsbn) {
                        userDao.updateOneUser(
                            User(
                                user.id,
                                user.fullName,
                                user.Borrowing1,
                                user.Borrowing2,
                                user.Borrowing3,
                                null
                            )
                        )
                    }

                    bookDao.updateOneBook(Book(book.isbn, book.bookName, null))
                    runOnUiThread {
                        result.text = "Return success!"
                        bookISBN.setText("")
                        userID.setText("")
                    }
                }catch (e: Exception) {
                    result.text = e.message
                    e.printStackTrace()
                }
            }

        }
    }
}