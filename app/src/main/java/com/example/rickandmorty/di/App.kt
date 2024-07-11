package com.example.rickandmorty.di

import android.app.Application
import com.example.data.data.CharacterRemoteDataSource
import com.example.data.data.CharacterRepository
import com.example.data.data.NetworkCharacterRepository
import com.example.data.retrofit.CharacterRetrofit
import com.example.domain.useCases.CharactersData
import com.example.domain.useCases.InfoUseCase
import com.example.presentation.viewModel.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
    private fun initKoin(){
        startKoin {
            modules(
                characterRootModule
            )
        }
    }

    private val characterRootModule = module {
        single { CharacterRetrofit() }
        single { CharacterRemoteDataSource(get()) }
        single <CharacterRepository> { NetworkCharacterRepository(get()) }
        single <InfoUseCase> { CharactersData(get()) }
        viewModel { CharacterViewModel(get()) }
    }
}

