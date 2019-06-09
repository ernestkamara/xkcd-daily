package io.kamara.xkcd.daily.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.kamara.xkcd.daily.MainActivity


@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}