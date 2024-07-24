package com.example.presentation.listcontacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.room.ContactEntity
import com.example.presentation.databinding.ItemContactBinding
import com.example.presentation.listCharacters.ItemsType

class ContactsAdapter(val item: MutableList<ContactEntity>): RecyclerView.Adapter<ContactsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = item[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int = item.size

    fun updateItems(newList: List<ContactEntity>) {
        val oldList = item.toList()
        item.clear()
        item.addAll(newList)
        val difCallback = object : DiffUtil.Callback() {

            override fun getOldListSize(): Int  = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldList[oldItemPosition]
                val newItem = newList[newItemPosition]
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(difCallback)
        diffResult.dispatchUpdatesTo(this)
    }

}