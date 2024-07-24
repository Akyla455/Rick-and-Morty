package com.example.domain.useCases.database

import com.example.data.room.ContactEntity
import com.example.data.room.LocalContactDataBase
import kotlinx.coroutines.flow.Flow

interface GetAllContactUseCase {
    fun getContact(): Flow<List<ContactEntity>>
}

class GetAllContact(
    private val localDataBase: LocalContactDataBase
): GetAllContactUseCase{
    override fun getContact(): Flow<List<ContactEntity>> {
        return localDataBase.getAllContacts()
    }

}