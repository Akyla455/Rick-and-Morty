package com.example.presentation.listCharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.model.InfoCharacter
import com.example.presentation.R
import com.example.presentation.databinding.ItemCharacterBinding

class CharacterAdapter(val item: List<InfoCharacter>): RecyclerView.Adapter<CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = item[position]
        with(holder.binding){
            Glide.with(holder.itemView.context)
                .load(character.image)
                .error(R.drawable.ic_down_24)
            nameCharacter.text = character.name
        }

    }

}