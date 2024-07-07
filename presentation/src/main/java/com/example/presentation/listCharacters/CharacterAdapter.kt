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

    private var isLoadingAdded = false
    private var isErrorAdded = false

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

    fun showLoading(show: Boolean) {
       if (show && !isLoadingAdded){
           items.add(ItemsType.Loading)
           notifyItemInserted(items.size - 1)
           isLoadingAdded = true
       } else if (!show && isLoadingAdded){
           val index = items.indexOfFirst { it is ItemsType.Loading }
           if (index != -1){
               items.removeAt(index)
               notifyItemRemoved(index)
               isLoadingAdded = false
           }
       }
    }

    fun showError(show: Boolean){
        if (show && !isErrorAdded){
            items.add(ItemsType.Error)
            notifyItemInserted(items.size - 1)
            isErrorAdded = true
        } else if(!show && isErrorAdded){
            val index = items.indexOfFirst { it is ItemsType.Error }
            if (index != -1){
                items.removeAt(index)
                notifyItemRemoved(index)
                isErrorAdded = false
            }
        }
    }

    fun updateItems(newItems: List<InfoCharacter>){
        val oldList = items.filterIsInstance<ItemsType.Character>().map {it.character}
        val newList = newItems.map { ItemsType.Character(it) }
        if (isLoadingAdded){
            items.removeAt(items.size - 1)
            isLoadingAdded = false
        }
        if (isErrorAdded){
            items.removeAt(items.size - 1)
            isErrorAdded = false
        }
        items.addAll(newList)
        val difCallback = object : DiffUtil.Callback() {

           override fun getOldListSize(): Int  = oldList.size

           override fun getNewListSize(): Int = newList.size

           override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
               return oldList[oldItemPosition].id == newList[newItemPosition].character.id
           }

           override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
               return oldList[oldItemPosition] == newList[newItemPosition].character
           }
       }
        val diffResult = DiffUtil.calculateDiff(difCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}