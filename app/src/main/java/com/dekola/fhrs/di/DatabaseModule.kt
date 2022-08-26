package com.dekola.fhrs.di

import android.content.Context
import androidx.room.Room
import com.dekola.fhrs.AUTHORITIES_DATABASE_NAME
import com.dekola.fhrs.data.local.db.AuthoritiesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context, AuthoritiesDatabase::class.java,
        AUTHORITIES_DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(database: AuthoritiesDatabase) = database.authoritiesDao()
}