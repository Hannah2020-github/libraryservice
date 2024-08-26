package com.hannah.libraryservice.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.hannah.libraryservice.R
import com.hannah.libraryservice.db.AppDatabase
import com.hannah.libraryservice.db.User
import com.hannah.libraryservice.db.UserDao

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val appDatabase = AppDatabase.buildDatabase(this)
        val userDao = appDatabase.getUserDao()
        val bookDao = appDatabase.getBookDao()

        Thread {
            val user1 = User("1", "Mary", "book1", "book2", "book3", "book4")
            userDao.insertOneNewUser(user1)
            Log.d("CCC", "${userDao.getAllUsers()}")
        }.start()



    }
}

