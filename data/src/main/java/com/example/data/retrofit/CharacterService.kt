package com.example.data.retrofit

import com.example.data.model.ListCharacter
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET ("character")
    suspend fun getCharacterInfo(
        @Query("page")
        page: Int
    ): ListCharacter
}