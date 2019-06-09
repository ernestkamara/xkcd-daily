package io.kamara.xkcd.daily

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.kamara.xkcd.daily.di.AppInjector
import timber.log.Timber
import javax.inject.Inject

class ComicApp : Application(), HasActivityInjector {
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        when {
            BuildConfig.DEBUG -> Timber.plant(Timber.DebugTree())
        }

        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}