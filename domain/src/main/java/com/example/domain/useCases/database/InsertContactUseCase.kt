package com.example.domain.useCases.database

import com.example.data.room.ContactEntity
import com.example.data.room.LocalContactDataBase

interface InsertContactUseCase {
    suspend fun insertContact(contact: ContactEntity)
}

class InsertContact(
    private val localDataBase: LocalContactDataBase
): InsertContactUseCase{
    override suspend fun insertContact(contact: ContactEntity) {
        localDataBase.insertContact(contact)
    }

}