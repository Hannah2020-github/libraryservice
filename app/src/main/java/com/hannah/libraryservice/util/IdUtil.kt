package com.hannah.libraryservice.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

class IdUtil {
    companion object {
        private val letterValueMap = HashMap<Char, String>()

        init {
            letterValueMap['A'] = "10"
            letterValueMap['B'] = "11"
            letterValueMap['C'] = "12"
            letterValueMap['D'] = "13"
            letterValueMap['E'] = "14"
            letterValueMap['F'] = "15"
            letterValueMap['G'] = "16"
            letterValueMap['H'] = "17"
            letterValueMap['I'] = "34"
            letterValueMap['J'] = "18"
            letterValueMap['K'] = "19"
            letterValueMap['L'] = "20"
            letterValueMap['M'] = "21"
            letterValueMap['N'] = "22"
            letterValueMap['O'] = "35"
            letterValueMap['P'] = "23"
            letterValueMap['Q'] = "24"
            letterValueMap['R'] = "25"
            letterValueMap['S'] = "26"
            letterValueMap['T'] = "27"
            letterValueMap['U'] = "28"
            letterValueMap['V'] = "29"
            letterValueMap['W'] = "32"
            letterValueMap['X'] = "30"
            letterValueMap['Y'] = "31"
            letterValueMap['Z'] = "33"
        }
        fun checkUserId(userID: String, context: Context): Boolean {
            if (userID== "") {
                val ab = AlertDialog.Builder(context)
                ab.setTitle("User ID Error")
                ab.setMessage("User ID cannot be empty.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okay") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return false
            }
            if (userID.length != 10) {
                val ab = AlertDialog.Builder(context)
                ab.setTitle("User ID Error")
                ab.setMessage("User ID length too long or too short.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okay") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return false
            }
            if (!idValidationCheck(userID)) {
                val ab = AlertDialog.Builder(context)
                ab.setTitle("User ID Error")
                ab.setMessage("User ID is not valid.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okay") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return false
            }
            return true
        }

        fun idValidationCheck(id: String): Boolean {
            // 首字為大寫英文字母
            val id = id.uppercase()
            val weight = arrayOf(1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1)
            var sum = 0
            // 英文字母轉換十位數數字後，各自乘上權重
            sum += (letterValueMap[id[0]]!![0].digitToInt()) * weight[0]
            sum += (letterValueMap[id[0]]!![1].digitToInt()) * weight[1]
            for (i in 1 until id.length) {
                sum += id[i].digitToInt() * weight[i + 1]
            }
            return sum % 10 == 0
        }
    }
}