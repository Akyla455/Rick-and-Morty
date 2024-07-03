package com.example.presentation.listCharacters

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemErrorBinding

class ErrorViewHolder(private val binding: ItemErrorBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(retryCallback: () -> Unit){
        binding.retryButton.setOnClickListener{ retryCallback() }
    }
}