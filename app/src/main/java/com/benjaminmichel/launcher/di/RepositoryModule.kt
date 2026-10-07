package com.benjaminmichel.launcher.di

import com.benjaminmichel.launcher.data.DataStoreFavoritesRepository
import com.benjaminmichel.launcher.data.IntentAppLauncher
import com.benjaminmichel.launcher.data.PackageManagerAppRepository
import com.benjaminmichel.launcher.data.PackageManagerIconProvider
import com.benjaminmichel.launcher.domain.repository.AppIconProvider
import com.benjaminmichel.launcher.domain.repository.AppLauncher
import com.benjaminmichel.launcher.domain.repository.AppRepository
import com.benjaminmichel.launcher.domain.repository.FavoritesRepository
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
    abstract fun bindAppRepository(impl: PackageManagerAppRepository): AppRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(impl: DataStoreFavoritesRepository): FavoritesRepository

    @Binds
    @Singleton
    abstract fun bindAppLauncher(impl: IntentAppLauncher): AppLauncher

    @Binds
    @Singleton
    abstract fun bindAppIconProvider(impl: PackageManagerIconProvider): AppIconProvider
}
