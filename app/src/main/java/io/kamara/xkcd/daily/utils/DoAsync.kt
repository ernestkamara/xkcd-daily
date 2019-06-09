package io.kamara.xkcd.daily.utils

import android.os.AsyncTask

/**
 * Utility class for asynchronous tasks with [AsyncTask]
 *
 * //TODO: Consider using Coroutines
 */
class DoAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}