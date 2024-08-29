package com.hannah.libraryservice.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

class IsbnUtil {

    companion object {
        fun checkIsbn(bookISBN: String, context: Context): Boolean {
            // isbn 不能為空
            if (bookISBN == "") {
                val ab = AlertDialog.Builder(context)
                ab.setTitle("ISBN Error")
                ab.setMessage("The book ISBN cannot be empty.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okey") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return false
            }
            // isbn 長度為 10
            if (bookISBN.length != 10) {
                val ab = AlertDialog.Builder(context)
                ab.setTitle("ISBN Error")
                ab.setMessage("The book ISBN length must be 10 digits long.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okey") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return false
            }
            // isbn validation
            if (!isbnValidation(bookISBN)) {
                val ab = AlertDialog.Builder(context)
                ab.setTitle("ISBN Error")
                ab.setMessage("The book ISBN is not valid.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okey") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return false
            }
            return true
        }

        private fun isbnValidation(isbn: String): Boolean {
            var sum = 0
            // 找出前 9 項
            // 前 9 項的加權和，再去跟最後一項去 mod
            for (i in 0 until isbn.length - 1) {
                sum += (isbn[i] - '0') * (i + 1)
            }

            val checkDigit = sum % 11
            val checkDigitProvided = isbn[9]
            // '0', '1', '2', ....,'X'
            val checkDigitProvidedToInt = if (checkDigitProvided == 'X') 10 else {checkDigitProvided - '0'}
            return checkDigit == checkDigitProvidedToInt
        }
    }

}