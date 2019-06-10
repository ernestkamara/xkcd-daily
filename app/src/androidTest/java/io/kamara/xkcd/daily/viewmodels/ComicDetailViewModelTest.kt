package io.kamara.xkcd.daily.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.repository.ComicRepository
import io.kamara.xkcd.daily.utils.getValue
import io.kamara.xkcd.daily.utils.mock
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.verify


@RunWith(JUnit4::class)
class ComicDetailViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val comicRepository = Mockito.mock(ComicRepository::class.java)
    private val comicDetailViewModel = ComicDetailViewModel(comicRepository)


    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() {
        assertNull(getValue(comicDetailViewModel.comic))
    }

    @Test
    fun testNull() {
        assertThat(comicDetailViewModel.comic, notNullValue())
        verify(comicRepository, never()).getComic(anyString())

        comicDetailViewModel.setComicId("123")
        verify(comicRepository, never()).getComic(anyString())
    }

    @Test
    fun nullComic() {
        val observer = mock<Observer<Comic>>()
        comicDetailViewModel.setComicId("123")

        comicDetailViewModel.setComicId(null)

        comicDetailViewModel.comic.observeForever(observer)
        verify(observer).onChanged(null)
    }
}