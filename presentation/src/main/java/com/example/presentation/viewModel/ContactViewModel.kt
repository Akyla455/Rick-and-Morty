package com.example.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.room.ContactEntity
import com.example.domain.useCases.database.DeleteContactUseCase
import com.example.domain.useCases.database.GetAllContactUseCase
import com.example.domain.useCases.database.InsertContactUseCase
import com.example.domain.useCases.database.UpdateContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ContactState{
    data object Loading: ContactState
    data object AddContact: ContactState
    data class Success(val contact: List<ContactEntity>) : ContactState
    data object Error: ContactState
}

class ContactViewModel(
    private val getAllContactUseCase: GetAllContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val insertContactUseCase: InsertContactUseCase
): ViewModel() {
    private val _state = MutableStateFlow<ContactState>(ContactState.Loading)
    val state: StateFlow<ContactState>
        get() = _state

    init {
        fetchContacts()
    }

    private fun fetchContacts(){
        viewModelScope.launch {
            try {
                getAllContactUseCase.getContact().collect{ contactsList ->
                    _state.value = ContactState.Success(contactsList)
                }

            } catch (e: Exception){
                _state.value = ContactState.Error
            }
        }
    }

    fun addContact(contact: ContactEntity){
        viewModelScope.launch {
            try {
                insertContactUseCase.insertContact(contact)
                fetchContacts()
            } catch (e: Exception){
                _state.value = ContactState.Error
            }
        }
    }

    fun updateContact(contact: ContactEntity){
        viewModelScope.launch {
            try {
                updateContactUseCase.updateContact(contact)
                fetchContacts()
            } catch (e: Exception){
                _state.value = ContactState.Error
            }
        }
    }

    fun deleteContact(contact: ContactEntity){
        viewModelScope.launch {
            try {
                deleteContactUseCase.deleteContact(contact)
                fetchContacts()
            } catch (e: Exception){
                _state.value = ContactState.Error
            }
        }
    }

    fun showAddContact(){
        _state.value = ContactState.AddContact
    }

    fun closeAddContact(){
        fetchContacts()
    }
}