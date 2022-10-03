package com.epiFiAssignment.moviesearch.di

import android.content.Context
import androidx.room.Room
import com.epiFiAssignment.moviesearch.db.MovieSearchDatabase
import com.epiFiAssignment.moviesearch.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : MovieSearchDatabase{
        return Room.databaseBuilder(context,MovieSearchDatabase::class.java , Constants.DB_NAME).build()
    }
}