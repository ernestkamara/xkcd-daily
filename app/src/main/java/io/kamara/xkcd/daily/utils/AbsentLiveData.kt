package io.kamara.xkcd.daily.utils

import androidx.lifecycle.LiveData

/**
 * A LiveData class that has `null` value.
 * Ref: https://proandroiddev.com/5-common-mistakes-when-using-architecture-components-403e9899f4cb
 */
class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {
    init {
        // use post instead of set since this can be created on any thread
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}