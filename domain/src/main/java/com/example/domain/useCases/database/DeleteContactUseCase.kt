package com.example.domain.useCases.database

import com.example.data.room.ContactEntity
import com.example.data.room.LocalContactDataBase

interface DeleteContactUseCase {
    suspend fun deleteContact(contact: ContactEntity)
}

class DeleteContact(private val localDataBase: LocalContactDataBase): DeleteContactUseCase{
    override suspend fun deleteContact(contact: ContactEntity) {
        localDataBase.deleteContact(contact)
    }

}