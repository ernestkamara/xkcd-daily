package io.kamara.xkcd.daily.data


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import io.kamara.xkcd.daily.repository.ComicDatabase
import io.kamara.xkcd.daily.repository.ComicDatabaseTest
import io.kamara.xkcd.daily.utils.getValue
import io.kamara.xkcd.daily.utils.loadResource
import io.kamara.xkcd.daily.utils.loadTestComic
import io.kamara.xkcd.daily.utils.loadTestComics
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@RunWith(AndroidJUnit4::class)
class ComicDatabaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ComicDatabase
    private lateinit var comicDao: ComicDao

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), ComicDatabase::class.java).build()
        comicDao = database.comicDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndLoad() {
        val jsonReader =  readResourceAsString("mock_comic.json")
        val comic = loadTestComic(jsonReader)

        comicDao.insertComic(comic)

        val loadedComic = getValue(database.comicDao().findComicById(comic.num))
        assertThat(loadedComic.num, CoreMatchers.`is`("612"))
    }


    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
        "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
    )
    //@Test
    fun delete() {
        val jsonReader =  readResourceAsString("comics.json")
        val comics = loadTestComics(jsonReader)

        comicDao.insertAll(comics)


        val loadedComics = getValue(comicDao.getAllComic())
        assertThat(loadedComics.size, CoreMatchers.`is`(5))

        val comicToDelete = loadedComics[0]
        comicDao.deleteComicById(comicToDelete.num)

        assertThat(loadedComics.size, CoreMatchers.`is`(4))
    }

    private fun readResourceAsString(name: String): String {
        return ComicDatabaseTest::class.java.classLoader.loadResource(name)
    }
}