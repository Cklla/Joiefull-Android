package com.openclassrooms.joiefull.di

import com.openclassrooms.joiefull.data.repository.ArticlesRepositoryImpl
import com.openclassrooms.joiefull.domain.repository.ArticlesRepository
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
    abstract fun bindArticlesRepository(
        impl: ArticlesRepositoryImpl,
    ) : ArticlesRepository
}