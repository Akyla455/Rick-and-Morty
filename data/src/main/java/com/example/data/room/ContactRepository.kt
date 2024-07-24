package com.example.data.room

import kotlinx.coroutines.flow.Flow

interface LocalContactDataBase{
    suspend fun insertContact(contact: ContactEntity)
    suspend fun updateContact(contact: ContactEntity)
    suspend fun deleteContact(contact: ContactEntity)
    fun getAllContacts(): Flow<List<ContactEntity>>
}


class ContactRepository(
    private val contactDao: ContactDao
): LocalContactDataBase {
    override suspend fun insertContact(contact: ContactEntity){
        contactDao.insertContact(contact)
    }
    override suspend fun updateContact(contact: ContactEntity){
        contactDao.updateContact(contact)
    }
    override suspend fun deleteContact(contact: ContactEntity){
        contactDao.deleteContact(contact)
    }
    override fun getAllContacts(): Flow<List<ContactEntity>>{
        return contactDao.getAllContacts()
    }
}