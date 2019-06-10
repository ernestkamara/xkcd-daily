package io.kamara.xkcd.daily.repository

import androidx.lifecycle.LiveData
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.data.ComicDao
import io.kamara.xkcd.daily.repository.api.AppExecutors
import io.kamara.xkcd.daily.repository.api.ComicsService
import io.kamara.xkcd.daily.repository.api.NetworkBoundResource
import io.kamara.xkcd.daily.repository.api.Resource
import io.kamara.xkcd.daily.testing.Mockable
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository module for handling [Comic] data operations.
 *
 */
@Mockable
@Singleton
class ComicRepository
@Inject constructor(
    private val comicDao: ComicDao,
    private val appExecutors: AppExecutors,
    private val comicsService: ComicsService ) {

    fun getComic(comicId: String): LiveData<Comic> = comicDao.findComicById(comicId)

    fun saveComic(comic: Comic) = comicDao.insertComic(comic)

    fun loadComic(comicId: String): LiveData<Resource<Comic>> {
        return object : NetworkBoundResource<Comic, Comic>(appExecutors) {

            override fun saveCallResult(comic: Comic) {
                comicDao.insertComic(comic)
            }

            override fun shouldFetch(data: Comic?) = data == null

            override fun loadFromDb() = comicDao.findComicById(comicId)

            override fun createCall() = comicsService.getComic(comicId)

        }.asLiveData()
    }
}
