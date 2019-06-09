package io.kamara.xkcd.daily.repository

import androidx.lifecycle.LiveData
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.data.ComicDao


/**
 * Repository module for handling [Comic] data operations.
 *
 * // TODO Make injectable
 */
class ComicRepository (private val comicDao: ComicDao) {

    fun getComic(comicId: String): LiveData<Comic> = comicDao.findComicById(comicId)

    fun saveComicToDatabase(comic: Comic) = comicDao.insertComic(comic)

    companion object {
        @Volatile private var instance: ComicRepository? = null
        fun getInstance(comicDao: ComicDao) =
            instance ?: synchronized(this) {
                instance ?: ComicRepository(comicDao).also { instance = it }
            }
    }
}
