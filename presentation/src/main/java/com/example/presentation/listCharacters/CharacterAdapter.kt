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

class CharacterAdapter(
    private var items: MutableList<InfoCharacter>,
    private val retryCallback: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_CHARACTER = 0
        const val ITEM_TYPE_ERROR = 1
    }

    private var showError = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE_ERROR -> {
                val binding = ItemErrorBinding.inflate(inflater, parent, false)
                ErrorViewHolder(binding)
            }

            else -> {
                val binding = ItemCharacterBinding.inflate(inflater, parent, false)
                CharacterViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (showError && position == items.size) ITEM_TYPE_ERROR else ITEM_TYPE_CHARACTER
    }

    override fun getItemCount(): Int {
        return if (showError) items.size + 1 else items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_TYPE_ERROR -> (holder as ErrorViewHolder).bind(retryCallback)
            ITEM_TYPE_CHARACTER -> {
                val character = items[position]
                with((holder as CharacterViewHolder).binding) {
                    Glide.with(holder.itemView.context)
                .load(character.image)
                .transform(CircleCrop())
                        .error(R.drawable.ic_down)
                        .into(holder.binding.icCharacter)
                    nameCharacter.text = character.name
                }
            }
        }
    }

    fun showError(show: Boolean) {
        if (show){
            if (!showError){
                showError = true
                notifyItemInserted(items.size)
            }
        } else{
            if (showError){
                showError = true
                notifyItemRemoved(items.size)
            }
        }
    }
    fun updateItems(newItems: List<InfoCharacter>){
        val oldList = items.toList()
        items.addAll(newItems)
        val difCallback = object : DiffUtil.Callback() {

           override fun getOldListSize(): Int  = oldList.size

           override fun getNewListSize(): Int = items.size

           override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
               return oldList[oldItemPosition].id == items[newItemPosition].id
           }

           override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
               return oldList[oldItemPosition] == items[newItemPosition]
           }
       }
        val diffResult = DiffUtil.calculateDiff(difCallback)
        diffResult.dispatchUpdatesTo(this)
    }
}