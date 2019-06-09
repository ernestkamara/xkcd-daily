package io.kamara.xkcd.daily.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.kamara.xkcd.daily.repository.ComicRepository
import io.kamara.xkcd.daily.data.Comic



/**
 * Factory for creating a [ComicDetailViewModel] with a constructor that takes a [ComicRepository]
 * and an ID for the current [Comic].
 *
 * //TODO: Make injectable
 */
class ComicDetailViewModelFactory(
    private val comicId: String,
    private val comicRepository: ComicRepository
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ComicDetailViewModel(comicId, comicRepository) as T
    }
}