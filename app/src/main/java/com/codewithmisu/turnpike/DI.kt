package com.codewithmisu.turnpike

import com.codewithmisu.turnpike.data.MockRepository
import com.codewithmisu.turnpike.domain.Repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(): Repository = MockRepository()
}
