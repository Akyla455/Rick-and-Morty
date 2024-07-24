package com.example.domain.useCases.database

import com.example.data.room.ContactEntity
import com.example.data.room.LocalContactDataBase

interface UpdateContactUseCase {
    suspend fun updateContact(contact: ContactEntity)
}

class UpdateContact(
    private val localDataBase: LocalContactDataBase
): UpdateContactUseCase{
    override suspend fun updateContact(contact: ContactEntity) {
        localDataBase.updateContact(contact)
    }

}