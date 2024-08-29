package com.hannah.libraryservice.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hannah.libraryservice.R
import com.hannah.libraryservice.db.AppDatabase
import com.hannah.libraryservice.db.Book
import java.util.concurrent.Executors

class DataQueryActivity : AppCompatActivity() {
    private lateinit var dataId: EditText
    private lateinit var searchBtn: Button
    private lateinit var AllUsersBtn: Button
    private lateinit var AllBooksBtn: Button
    private lateinit var result: TextView
    private val singleThreadExecutor = Executors.newSingleThreadExecutor()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_query_layout)

        dataId = findViewById(R.id.dataId_for_query)
        searchBtn = findViewById(R.id.search_btn)
        AllUsersBtn = findViewById(R.id.find_all_user_btn)
        AllBooksBtn = findViewById(R.id.find_all_book_btn)
        result = findViewById(R.id.data_query_result)

        AllUsersBtn.setOnClickListener {
            singleThreadExecutor.execute {
                val db = AppDatabase.buildDatabase(this)
                val userDao = db.getUserDao()
                val bookDao = db.getBookDao()
                var resultText = ""

                for (user in userDao.getAllUsers()) {
                    resultText += "User Name is ${user.fullName}\n"
                    resultText += "User ID is ${user.id}\n"
                    resultText += "Book is now borrow: \n"
                    var isBorrowingBooks = false
                    val bookBorrowing = arrayOf(user.Borrowing1, user.Borrowing2, user.Borrowing3, user.Borrowing4)
                    for (i in bookBorrowing.indices) {
                        if (bookBorrowing[i] != null) {
                            isBorrowingBooks = true
                            val bookFound = bookDao.getBookByISBN(bookBorrowing[i]!!)
                            resultText += "${bookFound.isbn} ${bookFound.bookName}\n"

                        }
                    }
                    if (!isBorrowingBooks) {
                        resultText += "none\n"
                    }

                    resultText += "============\n"
                }
                runOnUiThread {
                    result.text = resultText
                }
            }
        }

        AllBooksBtn.setOnClickListener {
            singleThreadExecutor.execute {
                val db = AppDatabase.buildDatabase(this)
                val bookDao = db.getBookDao()
                var resultText = ""

                for (book in bookDao.getAllBook()) {
                    resultText += "Book name is ${book.bookName}\n"
                    resultText += "Book ISBN is ${book.isbn}\n"
                    resultText += "Book is now borrow to  ${book.borrowTo}\n"
                    resultText += "============\n"
                }
                runOnUiThread {
                    result.text = resultText
                }
            }

        }




    }
}