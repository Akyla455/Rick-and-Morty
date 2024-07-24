package com.example.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.InfoUseCase
import com.example.presentation.listCharacters.ItemsType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface CharacterState{
    data object LoadingShimmer: CharacterState
    data class Error(val message: String): CharacterState
    data class Loaded(val info: List<ItemsType>): CharacterState
}

class CharacterViewModel(
    private val infoUseCase: InfoUseCase
): ViewModel() {
    private var currentPage = 1

    private val _characterState = MutableLiveData<CharacterState>()
    val characterState: LiveData<CharacterState>
        get() = _characterState
    private val characterList: MutableList<ItemsType> = mutableListOf()
    init {
        getCharacterInfo()
    }
    fun loadMoreCharacters(){
        if (_characterState.value != CharacterState.LoadingShimmer) {
            getCharacterInfo()
        }
    }

    private fun getCharacterInfo(){
        viewModelScope.launch {
            if(characterList.isEmpty()){
                _characterState.value = CharacterState.LoadingShimmer
            } else {
                _characterState.value = CharacterState.Loaded(characterList + ItemsType.Loading)
            }
            delay(3000)
            try {
                val newCharacters = infoUseCase.getInfo(currentPage)
                characterList.addAll(newCharacters.map{
                    ItemsType.Character(it)
                })
                _characterState.value = CharacterState.Loaded(characterList)
                currentPage++
            }catch (e: Exception){
                characterList.removeAll { it is ItemsType.Loading}
                _characterState.value = CharacterState.Loaded(characterList + ItemsType.Error)
            }
        }
    }

}