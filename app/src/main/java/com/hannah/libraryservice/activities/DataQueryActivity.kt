package com.hannah.libraryservice.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hannah.libraryservice.R
import com.hannah.libraryservice.db.AppDatabase
import com.hannah.libraryservice.db.Book
import com.hannah.libraryservice.util.IdUtil
import com.hannah.libraryservice.util.IsbnUtil
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

        searchBtn.setOnClickListener {
            val id = dataId.text.toString()
            if (id == "") {
                val ab = AlertDialog.Builder(this)
                ab.setTitle("ID Error")
                ab.setMessage("ID cannot is be empty.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okay") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return@setOnClickListener
            }
            // 首字如果是英文字，則為身分證號碼
            if (id[0].isLetter() && !IdUtil.checkUserId(id, this)) {
                return@setOnClickListener
            // 首字如果不是英文字，則為 ISBN 書碼
            }else if (!id[0].isLetter() && !IsbnUtil.checkIsbn(id, this)) {
                return@setOnClickListener
            }

            singleThreadExecutor.execute {
                try {
                    var resultText = ""
                    // 使用身分證號碼查詢
                    if (id[0].isLetter()) {
                        id[0].uppercase()
                        val userDao = AppDatabase.buildDatabase(this).getUserDao()
                        val bookDao = AppDatabase.buildDatabase(this).getBookDao()
                        val user = userDao.getUserById(id.uppercase())
                        if (user != null) {
                            resultText += "User name is ${user.fullName}\n"
                            resultText += "User ID is ${user.id}\n"
                            resultText += "The books this user is borrowing: \n"

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

                        }else {
                            resultText += "User not found."
                        }
                    // 使用 ISBN 書碼查詢
                    } else {
                        val bookDao = AppDatabase.buildDatabase(this).getBookDao()
                        val book = bookDao.getBookByISBN(id)
                        resultText += if (book != null) {
                            "${book.isbn} : ${book.bookName}"
                        }else {
                            "Book not found."
                        }
                    }

                    runOnUiThread {
                        result.text = resultText
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

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