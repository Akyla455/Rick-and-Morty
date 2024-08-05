package com.example.rickandmorty.di

import android.app.Application
import androidx.room.Room
import com.example.data.data.CharacterRemoteDataSource
import com.example.data.data.CharacterRepository
import com.example.data.data.NetworkCharacterRepository
import com.example.data.retrofit.CharacterRetrofit
import com.example.data.room.AppDataBase
import com.example.data.room.ContactRepository
import com.example.data.room.LocalContactDataBase
import com.example.domain.useCases.CharactersData
import com.example.domain.useCases.InfoUseCase
import com.example.domain.useCases.database.DeleteContact
import com.example.domain.useCases.database.DeleteContactUseCase
import com.example.domain.useCases.database.GetAllContact
import com.example.domain.useCases.database.GetAllContactUseCase
import com.example.domain.useCases.database.InsertContact
import com.example.domain.useCases.database.InsertContactUseCase
import com.example.domain.useCases.database.UpdateContact
import com.example.domain.useCases.database.UpdateContactUseCase
import com.example.presentation.CharacterViewModel
import com.example.presentation.ContactViewModel
import org.koin.android.ext.koin.androidContext
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
            androidContext(this@App)
            modules(
                characterRootModule,
                contactRootModule
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

    private val contactRootModule = module {
        single {
           Room.databaseBuilder(
               get(),
               AppDataBase::class.java,
               "contact_database"
           ).build()
        }
        single { get<AppDataBase>().contactDao() }
        single <LocalContactDataBase> { ContactRepository(get()) }
        single <DeleteContactUseCase> { DeleteContact(get()) }
        single <GetAllContactUseCase> { GetAllContact(get()) }
        single <UpdateContactUseCase> { UpdateContact(get()) }
        single <InsertContactUseCase> { InsertContact(get()) }
        viewModel { ContactViewModel(get(),get(),get(),get()) }
    }
}

