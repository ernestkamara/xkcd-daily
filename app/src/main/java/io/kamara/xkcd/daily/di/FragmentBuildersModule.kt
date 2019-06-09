package io.kamara.xkcd.daily.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.kamara.xkcd.daily.ComicDetailFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeComicDetailFragment(): ComicDetailFragment

}