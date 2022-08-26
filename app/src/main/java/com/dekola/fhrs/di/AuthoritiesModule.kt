package com.dekola.fhrs.di

import com.dekola.fhrs.data.remote.AuthoritiesRemoteDataSource
import com.dekola.fhrs.data.repository.AuthoritiesRepository
import com.dekola.fhrs.data.remote.IAuthoritiesRemoteDataSource
import com.dekola.fhrs.data.repository.IAuthoritiesRepository
import com.dekola.fhrs.data.local.dataSource.AuthoritiesLocalDataSource
import com.dekola.fhrs.data.local.dataSource.IAuthoritiesLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthoritiesModule {

    @Binds
    abstract fun provideAuthoritiesRepository(authoritiesRepository: AuthoritiesRepository): IAuthoritiesRepository

    @Binds
    abstract fun provideAuthoritiesLocalDataSource(authoritiesLocalDataSource: AuthoritiesLocalDataSource): IAuthoritiesLocalDataSource

    @Binds
    abstract fun provideAuthoritiesRemoteDataSource(remoteDataSource: AuthoritiesRemoteDataSource): IAuthoritiesRemoteDataSource

}