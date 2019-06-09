package io.kamara.xkcd.daily.repository

import androidx.lifecycle.LiveData
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.data.ComicDao

class ComicRepository (private val comicDao: ComicDao) {

    fun loadComic(comicId: String): LiveData<Comic> {
        return comicDao.findComicById(comicId)
    }

    fun saveComicToDatabase(comic: Comic){
        comicDao.insertComic(comic)
    }
}