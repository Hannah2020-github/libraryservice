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
import com.hannah.libraryservice.util.IdUtil
import com.hannah.libraryservice.util.IdUtil.Companion.checkUserId
import java.util.concurrent.Executors

class UserRegisterActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var userID: EditText
    private lateinit var userRegisterBtn: Button
    private lateinit var userRegisterResult: TextView


    private val singleThreadExecutor = Executors.newSingleThreadExecutor()

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

            if (!checkUserId(userID.text.toString(), this)) {
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
}