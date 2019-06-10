package io.kamara.xkcd.daily.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.kamara.xkcd.daily.data.Comic
import io.kamara.xkcd.daily.repository.ComicRepository
import io.kamara.xkcd.daily.ComicDetailFragment
import io.kamara.xkcd.daily.repository.api.Resource
import io.kamara.xkcd.daily.utils.AbsentLiveData
import javax.inject.Inject


/**
 * The ViewModel for the [ComicDetailFragment].
 */
class ComicDetailViewModel @Inject constructor(comicRepository: ComicRepository): ViewModel(){
    private val comicId = MutableLiveData<String>()

    val comic: LiveData<Resource<Comic>>? = Transformations.switchMap(comicId) { comicId ->
        when (comicId) {
            null -> AbsentLiveData.create()
            else -> comicRepository.loadComic(comicId)
        }
    }

    fun setComicId(comicId: String?) {
        this.comicId.value = comicId
    }
}
