package io.kamara.xkcd.daily.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.kamara.xkcd.daily.viewmodels.ComicDetailViewModel
import io.kamara.xkcd.daily.viewmodels.ViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ComicDetailViewModel::class)
    abstract fun bindComicDetailViewModel(comicDetailViewModel: ComicDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
