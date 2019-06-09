package io.kamara.xkcd.daily.utils

import android.content.Context
import io.kamara.xkcd.daily.repository.ComicRepository
import io.kamara.xkcd.daily.repository.ComicDatabase

object InjectorUtils {

    fun getComicRepository(context: Context): ComicRepository {
        return ComicRepository.getInstance(
            ComicDatabase.getInstance(context.applicationContext).comicDao())
    }

}
