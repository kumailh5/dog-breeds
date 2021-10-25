package com.kumail.dogbreeds.di

import com.kumail.dogbreeds.data.repository.BreedRepository
import com.kumail.dogbreeds.data.repository.BreedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by kumailhussain on 25/10/2021.
 */
@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideBreedsListRepository(breedRepositoryImpl: BreedRepositoryImpl): BreedRepository
}