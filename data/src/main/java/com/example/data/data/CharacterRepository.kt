package com.example.data.data

import com.example.data.model.InfoCharacter

interface CharacterRepository {
    suspend fun getCharacter(): List<InfoCharacter>
}

class NetworkCharacterRepository(
    private val characterRemoteDataSource: CharacterRemoteDataSource
): CharacterRepository {
    override suspend fun getCharacter(): List<InfoCharacter> {
        return characterRemoteDataSource.characterService
            .getCharacterInfo().results.map { results ->
                InfoCharacter(
                    name = results.name,
                    image = results.image
                )
            }

    }

}