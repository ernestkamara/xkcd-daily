package io.kamara.xkcd.daily.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.repository.ComicRepository
import io.kamara.xkcd.daily.ComicDetailFragment


/**
 * The ViewModel for the [ComicDetailFragment].
 */
//TODO: Make injectable
class ComicDetailViewModel(comicId: String, comicRepository: ComicRepository): ViewModel(){
    val comic: LiveData<Comic> = comicRepository.getComic(comicId)
}
