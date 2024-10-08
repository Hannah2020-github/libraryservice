package com.hannah.libraryservice.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.hannah.libraryservice.R

class ExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val appDatabase = AppDatabase.buildDatabase(this)
        val artistDao = appDatabase.getArtistDao()
        val albumDao = appDatabase.getAlbumDao()

        Thread {
//            val album1 = Album("1", "mary", "2")
//            val album2 = Album("2", "apple", "2")
//            val album3 = Album("3", "candy", "3")

//            albumDao.insertOne(album1)
//            albumDao.insertOne(album2)
//            albumDao.insertOne(album3)

            for (compound in albumDao.findAlbumJoinArtist("2")){
                Log.d("AAA", "${compound.album.albumName} is made by ${compound.artist.age} ages old, artist named ${compound.artist.name}")
            }

//            Log.d("AAA", "${albumDao.findAlbumJoinArtist("2")}")

        }.start()

    }
}