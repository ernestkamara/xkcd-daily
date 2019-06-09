package io.kamara.xkcd.daily


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.google.gson.Gson
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.data.ComicDao
import io.kamara.xkcd.daily.repository.ComicDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

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

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @Test
    @Throws(Exception::class)
    fun testComicInsertion() {
        val mockComic = javaClass.classLoader.getResource("mock_comic.json").readText()
        val comic = Gson().fromJson(mockComic, Comic::class.java)

        val comicId = comic.num

        comicDao.insertComic(comic)

        val insertedComic =  comicDao.findComicById(comicId)
        val comicTitle = getValue(insertedComic).title
        assertEquals(comic.title, comicTitle)
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        return data[0] as T
    }
}