package com.example.data.data

import com.example.data.retrofit.CharacterRetrofit
import com.example.data.retrofit.CharacterService

class CharacterRemoteDataSource(
    private val characterRetrofit: CharacterRetrofit
) {
    val characterService: CharacterService by lazy {
        characterRetrofit.retrofit.create(CharacterService::class.java)
    }
}