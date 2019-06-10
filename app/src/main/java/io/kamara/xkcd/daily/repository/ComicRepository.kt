package io.kamara.xkcd.daily.repository

import androidx.lifecycle.LiveData
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.data.ComicDao
import io.kamara.xkcd.daily.testing.Mockable
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository module for handling [Comic] data operations.
 *
 */
@Mockable
@Singleton
class ComicRepository @Inject constructor(private val comicDao: ComicDao) {

    fun getComic(comicId: String): LiveData<Comic> = comicDao.findComicById(comicId)

    fun saveComic(comic: Comic) = comicDao.insertComic(comic)
}
