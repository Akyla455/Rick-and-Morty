package com.example.presentation.listcontacts

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.data.room.ContactEntity
import com.example.presentation.databinding.ItemContactBinding

class ContactsViewHolder(val binding: ItemContactBinding): ViewHolder(binding.root){
    fun bind(contact: ContactEntity){
        binding.nameContact.text = contact.name
        binding.numberContact.text = contact.phoneNumber
    }
}