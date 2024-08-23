package com.hannah.libraryservice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.hannah.libraryservice.ui.theme.LibraryServiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "database.db").build()
        val artistDao = appDatabase.getArtistDao()
        val albumDao = appDatabase.getAlbumDao()

        Thread {
            val artist1 = Artist("1", "Willson", 29)
            val artist2 = Artist("2", "Grace", 30)
            val artist3 = Artist("3", "Mike", 31)
            val artist4 = Artist("4", "Spencer", 32)
            val artist5 = Artist("5", "Esther", 33)
            val artist6 = Artist("6", "Naloe", 34)

            artistDao.inserOne(artist1)
            artistDao.inserTwo(artist2, artist3)
            artistDao.insertManyArtist(artist4, artist5, artist6)

            Log.d("AAA", "${artistDao.getAllArtists()}")

        }.start()

    }
}

