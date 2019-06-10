package io.kamara.xkcd.daily.repository

import java.util.concurrent.TimeUnit
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import io.kamara.xkcd.daily.utils.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@RunWith(AndroidJUnit4::class)
class ComicDatabaseTest {
    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    private lateinit var _db: ComicDatabase

    private val comicDatabase: ComicDatabase get() = _db

    @Before
    fun initDb() {
        _db = Room
            .inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), ComicDatabase::class.java)
            .build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndLoad() {
        val jsonReader =  readResourceAsString("mock_comic.json")
        val comic = loadTestComic(jsonReader)

        comicDatabase.comicDao().insertComic(comic)

        val loadedComic = getValue(comicDatabase.comicDao().findComicById(comic.num))
        assertThat(loadedComic.num, `is`("612"))
    }


    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
        "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
    )
    @Test
    @Throws(InterruptedException::class)

    fun delete() {
        val jsonReader =  readResourceAsString("comics.json")
        val comics = loadTestComics(jsonReader)

        comicDatabase.comicDao().insertAll(comics)

        val loadedComics = getValue(comicDatabase.comicDao().getAllComic())
        assertThat(loadedComics.size, `is`(5))

        val comicToDelete = loadedComics[0]
        comicDatabase.comicDao().deleteComicById(comicToDelete.num)

        assertThat(loadedComics.size, `is`(4))
    }

    private fun readResourceAsString(name: String): String {
        return ComicDatabaseTest::class.java.classLoader.loadResource(name)
    }
}