package com.hannah.libraryservice.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.hannah.libraryservice.R
import com.hannah.libraryservice.util.IdUtil
import com.hannah.libraryservice.util.IdUtil.Companion.checkUserId
import com.hannah.libraryservice.util.IsbnUtil.Companion.checkIsbn

class BorrowReturnActivity : AppCompatActivity() {
    private lateinit var bookISBN: EditText
    private lateinit var userID: EditText
    private lateinit var borrowBtn: Button
    private lateinit var returnBtn: Button
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.borrow_return_layout)

        bookISBN = findViewById(R.id.bookISBN_for_borrow)
        userID = findViewById(R.id.userId_for_borrow)
        borrowBtn = findViewById(R.id.borrow_btn)
        returnBtn = findViewById(R.id.return_btn)
        result = findViewById(R.id.borrow_return_result)

        borrowBtn.setOnClickListener {
            val bookIsbn = bookISBN.text.toString()
            val userId = userID.text.toString()

            if (!checkIsbn(bookIsbn, this)) {
                return@setOnClickListener
            }

            if (!checkUserId(userId, this)) {
                return@setOnClickListener
            }
        }

        returnBtn.setOnClickListener {

        }

    }
}