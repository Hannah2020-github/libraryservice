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
import com.hannah.libraryservice.db.User
import java.util.concurrent.Executors

class UserRegisterActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var userID: EditText
    private lateinit var userRegisterBtn: Button
    private lateinit var userRegisterResult: TextView

    private val letterValueMap = HashMap<Char, String>()
    private val singleThreadExecutor = Executors.newSingleThreadExecutor()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_register_layout)

        userName = findViewById(R.id.userName_for_register)
        userID = findViewById(R.id.userId_for_register)
        userRegisterBtn = findViewById(R.id.user_register_btn)
        userRegisterResult = findViewById(R.id.user_register_result)

        userRegisterBtn.setOnClickListener {
            if (userName.text.toString() == "") {
                val ab = AlertDialog.Builder(this)
                ab.setTitle("Username Error")
                ab.setMessage("Username cannot be empty.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okay") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return@setOnClickListener

            }
            if (userID.text.toString() == "") {
                val ab = AlertDialog.Builder(this)
                ab.setTitle("User ID Error")
                ab.setMessage("User ID cannot be empty.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okay") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return@setOnClickListener
            }
            if (userID.text.toString().length != 10) {
                val ab = AlertDialog.Builder(this)
                ab.setTitle("User ID Error")
                ab.setMessage("User ID length too long or too short.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okay") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return@setOnClickListener
            }
            if (!idValidationCheck(userID.text.toString())) {
                val ab = AlertDialog.Builder(this)
                ab.setTitle("User ID Error")
                ab.setMessage("User ID is not valid.")
                ab.setCancelable(false)
                ab.setPositiveButton("Okay") {dialog, _ -> dialog.dismiss()}
                ab.create().show()
                return@setOnClickListener
            }

            singleThreadExecutor.execute {
                try {
                    val userNameEntry = userName.text.toString()
                    val userIdEntry = userID.text.toString().uppercase()
                    val db = AppDatabase.buildDatabase(this)
                    db.getUserDao().insertOneNewUser(User(userIdEntry, userNameEntry, null, null, null, null))
                    runOnUiThread {
                        userRegisterResult.text = "The result is: \nCongrats! The user has been registered."
                        userName.setText("")
                        userID.setText("")
                    }

                }catch (e: Exception) {
                    runOnUiThread {
                        userRegisterResult.text = "The result is: \n${e.message}"
                    }
                }
            }
        }
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