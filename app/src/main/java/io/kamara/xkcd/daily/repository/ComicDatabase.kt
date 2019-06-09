package io.kamara.xkcd.daily.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.data.ComicDao
import io.kamara.xkcd.daily.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.kamara.xkcd.daily.utils.DoAsync
import timber.log.Timber



/**
 * The Room database for this application
 * //TODO: Make injectable
 */
@Database(entities = [Comic::class], version = 1, exportSchema = false)
abstract class ComicDatabase : RoomDatabase() {
    abstract fun comicDao(): ComicDao

    companion object {
        @Volatile
        private var instance: ComicDatabase? = null

        fun getInstance(context: Context): ComicDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also {
                        instance = it
                    }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): ComicDatabase {
            return Room
                .databaseBuilder(context, ComicDatabase::class.java, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigrationFrom()
                .allowMainThreadQueries()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        seedDatabase(context)
                    }
                })
                .build()
        }

        private fun seedDatabase(context: Context) {
            DoAsync {
                val comicDao = getInstance(context).comicDao()
                comicDao.deleteAll()
                try {
                    context.assets.open(Constants.MOCK_COMICS_DATA_FILENAME).use { inputStream ->
                        JsonReader(inputStream.reader()).use { jsonReader ->
                            val comicType = object : TypeToken<List<Comic>>() {}.type
                            val comicList: List<Comic> = Gson().fromJson(jsonReader, comicType)
                            getInstance(context).comicDao().insertAll(comicList)
                        }
                    }
                } catch (ex: Exception) {
                    Timber.e(ex, "Error seeding database")
                }
            }.execute()
        }
    }
}