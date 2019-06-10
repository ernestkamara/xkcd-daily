package io.kamara.xkcd.daily.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.kamara.xkcd.daily.data.Comic
import org.mockito.Mockito
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun ClassLoader.loadResource(name: String): String {
    return getResource(name).readText()
}

fun loadTestComic(jsonReader: String): Comic {
    return Gson().fromJson(jsonReader, Comic::class.java)
}

fun loadTestComics(jsonReader: String): List<Comic> {
    val comicType = object : TypeToken<List<Comic>>() {}.type
    return Gson().fromJson(jsonReader, comicType)
}

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

/**
 * Helper method for testing LiveData objects, from
 * https://github.com/googlesamples/android-architecture-components.
 *
 * Get the value from a LiveData object. We're waiting for LiveData to emit, for 2 seconds.
 * Once we got a notification via onChanged, we stop observing.
 */
fun <T> getValue(liveData: LiveData<T>): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data[0] = o
            latch.countDown()
            liveData.removeObserver(this)
        }
    }
    liveData.observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)

    @Suppress("UNCHECKED_CAST")
    return data[0] as T
}