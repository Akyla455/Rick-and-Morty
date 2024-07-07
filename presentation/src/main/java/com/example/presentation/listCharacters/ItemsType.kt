package com.example.presentation.listCharacters

import com.example.data.model.InfoCharacter

sealed class ItemsType {
    data class Character(val character: InfoCharacter): ItemsType()
    data object Error: ItemsType()
    data object Loading: ItemsType()
}