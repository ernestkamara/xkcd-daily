package io.kamara.xkcd.daily.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.data.ComicDao


/**
 * The Room database for this application
 */
@Database(entities = [Comic::class], version = 4, exportSchema = false)
abstract class ComicDatabase : RoomDatabase() {
    abstract fun comicDao(): ComicDao
}