package io.kamara.xkcd.daily.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.kamara.xkcd.daily.data.ComicDao
import io.kamara.xkcd.daily.repository.ComicDatabase
import io.kamara.xkcd.daily.utils.Constants
import javax.inject.Singleton
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.kamara.xkcd.daily.repository.api.ComicsService
import io.kamara.xkcd.daily.repository.api.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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


    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideComicsService(): ComicsService {
        return Retrofit.Builder()
            .baseUrl("https://xkcd.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ComicsService::class.java)
    }
}