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
    data object Error: CharacterState
    data class Success(val info: List<InfoCharacter>): CharacterState
}

class CharacterViewModel(
    private val infoUseCase: InfoUseCase
): ViewModel() {
    private val _characterState = MutableLiveData<CharacterState>()
    val characterState: LiveData<CharacterState>
        get() = _characterState

    init {
        getCharacterInfo()
    }

    private fun getCharacterInfo(){
        viewModelScope.launch {
            _characterState.value = CharacterState.Loading
            try {
                val info = infoUseCase.getInfo()
                _characterState.value = CharacterState.Success(info)
            }catch (e: Exception){
                _characterState.value = CharacterState.Error
            }
        }
    }

}