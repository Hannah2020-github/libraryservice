package com.hannah.libraryservice.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hannah.libraryservice.R
import com.hannah.libraryservice.db.AppDatabase
import com.hannah.libraryservice.db.Book
import com.hannah.libraryservice.util.IsbnUtil.Companion.checkIsbn
import java.util.concurrent.Executors

class BookRegisterActivity : AppCompatActivity() {
    private lateinit var bookName: EditText
    private lateinit var bookISBN: EditText
    private lateinit var bookRegisterBtn: Button
    private lateinit var bookRegisterResult: TextView
    private val sigleThreadExecutor =  Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_register_layout)

        bookName = findViewById(R.id.bookName_for_register)
        bookISBN = findViewById(R.id.bookISBN_for_register)
        bookRegisterBtn = findViewById(R.id.book_register_btn)
        bookRegisterResult = findViewById(R.id.book_register_result)

        bookRegisterBtn.setOnClickListener {
            // book name 不能為空
            if (bookName.text.toString() == "") {
                val ab = AlertDialog.Builder(this)
                ab.setTitle("book Title Error")
                ab.setMessage("The book title cannot be empty.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okay") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return@setOnClickListener
            }

            if (!checkIsbn(bookISBN.text.toString(), this)) {
                return@setOnClickListener
            }

            // 以上條件滿足，則儲存 book 資料
            sigleThreadExecutor.execute {
                try {
                    val bookTitle = bookName.text.toString()
                    val bookISBNEntry = bookISBN.text.toString()
                    var db = AppDatabase.buildDatabase(this)
                    db.getBookDao().insertOneNewBook(Book(bookISBNEntry, bookTitle, null))
                    runOnUiThread {
                        bookRegisterResult.text = "The result is:\nCongrats! The book has been registered."
                        bookName.setText("")
                        bookISBN.setText("")
                    }
                }catch (e: Exception) {
                    runOnUiThread {
                        bookRegisterResult.text = "The result is :\n${e.message}"
                    }

                }
            }

        }
    }
}