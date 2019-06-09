package io.kamara.xkcd.daily.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.data.ComicDao
import timber.log.Timber

object InjectorUtils {
    fun seedDatabase(context: Context, comicDao: ComicDao) {
        DoAsync {
            comicDao.deleteAll()
            try {
                context.assets.open(Constants.MOCK_COMICS_DATA_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val comicType = object : TypeToken<List<Comic>>() {}.type
                        val comicList: List<Comic> = Gson().fromJson(jsonReader, comicType)
                        comicDao.insertAll(comicList)
                    }
                }
            } catch (ex: Exception) {
                Timber.e(ex, "Error seeding database")
            }
        }.execute()
    }

}
