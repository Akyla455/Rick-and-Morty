package com.example.data.data

import com.example.data.model.InfoCharacter

interface CharacterRepository {
    suspend fun getCharacter(page: Int): List<InfoCharacter>
}

class NetworkCharacterRepository(
    private val characterRemoteDataSource: CharacterRemoteDataSource
): CharacterRepository {
    override suspend fun getCharacter(page: Int): List<InfoCharacter> {
        return characterRemoteDataSource.characterService
            .getCharacterInfo(page).results.map { results ->
                InfoCharacter(
                    id = results.id,
                    name = results.name,
                    image = results.image
                )
            }

    }

}