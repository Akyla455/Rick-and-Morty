package com.example.data.retrofit

import com.example.data.model.ListCharacter
import retrofit2.http.GET

interface CharacterService {
    @GET ("character")
    suspend fun getCharacterInfo(): ListCharacter
}