package com.example.presentation.listCharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.data.model.InfoCharacter
import com.example.presentation.R
import com.example.presentation.databinding.ItemCharacterBinding

class CharacterAdapter(private var items: MutableList<InfoCharacter>): RecyclerView.Adapter<CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = items[position]
        with(holder.binding){
            Glide.with(holder.itemView.context)
                .load(character.image)
                .transform(CircleCrop())
                .error(R.drawable.ic_down)
                .into(holder.binding.icCharacter)
            nameCharacter.text = character.name
        }

    }
    fun updateItems(newItems: List<InfoCharacter>){
       val difCallback = object : DiffUtil.Callback(){
           override fun getOldListSize(): Int  = items.size

           override fun getNewListSize(): Int = newItems.size

           override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
               return items[oldItemPosition].id == newItems[newItemPosition].id
           }

           override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
               return items[oldItemPosition] == newItems[newItemPosition]
           }
       }
        val diffResult = DiffUtil.calculateDiff(difCallback)
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}