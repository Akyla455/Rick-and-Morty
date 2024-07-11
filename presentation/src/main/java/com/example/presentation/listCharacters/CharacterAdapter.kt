package com.example.presentation.listCharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.presentation.R
import com.example.presentation.databinding.ItemCharacterBinding
import com.example.presentation.databinding.ItemErrorBinding
import com.example.presentation.databinding.ItemLoadingBinding

class CharacterAdapter(
    private var items: MutableList<ItemsType>,
    private val retryCallback: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_CHARACTER = 0
        const val ITEM_TYPE_ERROR = 1
        const val ITEM_TYPE_LOADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE_ERROR -> {
                val binding = ItemErrorBinding.inflate(inflater, parent, false)
                ErrorViewHolder(binding).apply {
                    binding.retryButton.setOnClickListener{
                        retryCallback()
                    }

                }
            }

            ITEM_TYPE_CHARACTER -> {
                val binding = ItemCharacterBinding.inflate(inflater, parent, false)
                CharacterViewHolder(binding)
            }
            ITEM_TYPE_LOADING -> {
                val binding = ItemLoadingBinding.inflate(inflater, parent, false)
                LoadingViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is ItemsType.Character -> ITEM_TYPE_CHARACTER
            is ItemsType.Error -> ITEM_TYPE_ERROR
            is ItemsType.Loading -> ITEM_TYPE_LOADING
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = items[position]){
            is ItemsType.Character -> {
                val character = item.character
                with((holder as CharacterViewHolder).binding){
                    Glide.with(holder.itemView.context)
                        .load(character.image)
                        .transform(CircleCrop())
                        .error(R.drawable.ic_down)
                        .into(icCharacter)
                    nameCharacter.text = character.name
                }
            }
            is ItemsType.Loading -> (holder as LoadingViewHolder)
            is ItemsType.Error -> (holder as ErrorViewHolder)
        }
    }

    fun updateItems(newList: List<ItemsType>) {
        val oldList = items.toList()
        items.clear()
        items.addAll(newList)
        val difCallback = object : DiffUtil.Callback() {

           override fun getOldListSize(): Int  = oldList.size

            override fun getNewListSize(): Int = newList.size

           override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
               val oldItem = oldList[oldItemPosition]
               val newItem = newList[newItemPosition]
               return if(oldItem is ItemsType.Character && newItem is ItemsType.Character){
                   oldItem.character.id == newItem.character.id
               } else {
                   oldItem == newItem
               }
           }

           override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
               return oldList[oldItemPosition] == newList[newItemPosition]
           }
       }
        val diffResult = DiffUtil.calculateDiff(difCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}