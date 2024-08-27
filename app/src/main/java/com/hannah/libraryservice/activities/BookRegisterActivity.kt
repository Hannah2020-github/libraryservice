package com.hannah.libraryservice.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hannah.libraryservice.R

class BookRegisterActivity : AppCompatActivity() {
    private lateinit var bookName: EditText
    private lateinit var bookISBN: EditText
    private lateinit var bookRegisterBtn: Button
    private lateinit var bookRegisterResult: TextView

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
            // isbn 不能為空
            if (bookISBN.text.toString() == "") {
                val ab = AlertDialog.Builder(this)
                ab.setTitle("ISBN Error")
                ab.setMessage("The book ISBN is not valid.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okey") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return@setOnClickListener
            }
            // isbn 長度為 10
            if (bookISBN.length() != 10) {
                val ab = AlertDialog.Builder(this)
                ab.setTitle("ISBN Error")
                ab.setMessage("The book ISBN length must be 10 digits long.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okey") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return@setOnClickListener
            }
            // isbn validation
            if (!isbnValidation(bookISBN.text.toString())) {
                val ab = AlertDialog.Builder(this)
                ab.setTitle("ISBN Error")
                ab.setMessage("The book ISBN is not valid.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okey") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return@setOnClickListener
            }
            // 以上條件滿足，則儲存 book 資料

        }
    }

    private fun isbnValidation(isbn: String): Boolean {
        var sum = 0
        // 找出前 9 項
        // 前 9 項的加權和，再去跟最後一項去 mod
        for (i in 0 until isbn.length - 1) {
            // 此數字為 char，利用 - '0'，轉換成 Int 數字
            sum += (isbn[i] - '0') * (i + 1)
        }

        val checkDigit = sum % 11
        val checkDigitProvided = isbn[9]
        // '0', '1', '2', ....,'X'
        val checkDigitProvidedToInt = if (checkDigitProvided == 'X') 10 else {checkDigitProvided - '0'}
        return checkDigit == checkDigitProvidedToInt
    }
}