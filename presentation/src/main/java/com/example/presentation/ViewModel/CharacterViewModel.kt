package com.example.presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.InfoCharacter
import com.example.domain.useCases.InfoUseCase
import kotlinx.coroutines.launch

sealed interface CharacterState{
    data object Loading: CharacterState
    data class Error(val message: String): CharacterState
    data class Loaded(val info: List<InfoCharacter>): CharacterState
}

class CharacterViewModel(
    private val infoUseCase: InfoUseCase
): ViewModel() {
    private var currentPage = 1

    private val _characterState = MutableLiveData<CharacterState>()
    val characterState: LiveData<CharacterState>
        get() = _characterState

    init {
        getCharacterInfo()
    }
    fun loadMoreCharacters(){
        getCharacterInfo()
    }

    private fun getCharacterInfo(){
        viewModelScope.launch {
            _characterState.value = CharacterState.Loading
            try {
                val newCharacters = infoUseCase.getInfo(currentPage)
                _characterState.value = CharacterState.Loaded(newCharacters)
                currentPage++
            }catch (e: Exception){
                _characterState.value = CharacterState.Error(e.message?: "")
            }
        }
    }

}