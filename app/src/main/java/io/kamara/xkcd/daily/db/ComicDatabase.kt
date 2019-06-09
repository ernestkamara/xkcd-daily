package io.kamara.xkcd.daily.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.data.ComicDao

@Database(entities = [Comic::class], version = 1, exportSchema = false)
abstract class ComicDatabase : RoomDatabase() {
    abstract fun comicDao(): ComicDao
}