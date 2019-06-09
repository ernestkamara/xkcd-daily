package io.kamara.xkcd.daily.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.kamara.xkcd.daily.data.ComicDao
import io.kamara.xkcd.daily.repository.ComicDatabase
import io.kamara.xkcd.daily.utils.Constants
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideComicDatabase(app: Application): ComicDatabase {
        return Room
            .databaseBuilder(app, ComicDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideComicDao(db: ComicDatabase): ComicDao {
        return db.comicDao()
    }
}