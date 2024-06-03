package com.example.domain.useCases

import com.example.data.data.CharacterRepository
import com.example.data.model.InfoCharacter
import com.example.domain.InfoCharacters

interface InfoUseCase {
    suspend fun getInfo(): List<InfoCharacter>
}

class CharactersData(
    private val repository: CharacterRepository
): InfoUseCase{
    suspend override fun getInfo(): List<InfoCharacter> {
        return repository.getCharacter()
    }
}