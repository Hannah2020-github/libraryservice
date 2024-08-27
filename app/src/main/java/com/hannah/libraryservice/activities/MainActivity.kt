package com.hannah.libraryservice.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.hannah.libraryservice.R
import com.hannah.libraryservice.db.AppDatabase
import com.hannah.libraryservice.db.User
import com.hannah.libraryservice.db.UserDao

class MainActivity : AppCompatActivity() {
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        btn1 = findViewById(R.id.register_book_btn)
        btn2 = findViewById(R.id.register_user_btn)
        btn3 = findViewById(R.id.borrow_return_btn)
        btn4 = findViewById(R.id.data_query_btn)

        btn1.setOnClickListener {
            startActivity(Intent(this, BookRegisterActivity::class.java))
        }

        btn2.setOnClickListener {
            startActivity(Intent(this, UserRegisterActivity::class.java))
        }

        btn3.setOnClickListener {
            startActivity(Intent(this, BorrowReturnActivity::class.java))
        }

        btn4.setOnClickListener {
            startActivity(Intent(this, DataQueryActivity::class.java))
        }




//        val appDatabase = AppDatabase.buildDatabase(this)
//        val userDao = appDatabase.getUserDao()
//        val bookDao = appDatabase.getBookDao()
//
//        Thread {
//            val user1 = User("1", "Mary", "book1", "book2", "book3", "book4")
//            userDao.insertOneNewUser(user1)
//            Log.d("CCC", "${userDao.getAllUsers()}")
//        }.start()



    }
}

