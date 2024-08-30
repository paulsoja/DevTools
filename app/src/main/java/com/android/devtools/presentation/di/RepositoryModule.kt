package com.android.devtools.presentation.di

import com.android.devtools.data.repository.ApiDataRepository
import com.android.devtools.domain.repository.ApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun apiRepository(apiDataRepository: ApiDataRepository): ApiRepository
}