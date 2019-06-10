package io.kamara.xkcd.daily.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.kamara.xkcd.daily.data.ComicDao
import io.kamara.xkcd.daily.utils.loadResource
import io.kamara.xkcd.daily.utils.loadTestComic
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class ComicRepositoryTest {
    private val comicDao = mock(ComicDao::class.java)
    private val comicRepository = ComicRepository(comicDao)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testGetComic() {
        val comicId = "123"

        comicRepository.getComic(comicId)
        verify(comicDao).findComicById(comicId)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
        "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
    )
    @Test
    fun testSaveComic() {

        val jsonReader =  ComicRepositoryTest::class.java.classLoader.loadResource("mock_comic.json")
        val comic = loadTestComic(jsonReader)

        comicRepository.saveComic(comic)
        verify(comicDao).insertComic(comic)
    }
}