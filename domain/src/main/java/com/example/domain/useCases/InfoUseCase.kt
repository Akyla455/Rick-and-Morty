package com.example.domain.useCases

import com.example.data.data.CharacterRepository
import com.example.data.model.InfoCharacter

interface InfoUseCase {
    suspend fun getInfo(): List<InfoCharacter>
}

class CharactersData(
    private val repository: CharacterRepository
): InfoUseCase{
    override suspend fun getInfo(): List<InfoCharacter> {
        val info = repository.getCharacter()
        return info
    }
}