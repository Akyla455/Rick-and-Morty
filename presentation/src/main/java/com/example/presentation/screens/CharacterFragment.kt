package com.example.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.model.InfoCharacter
import com.example.presentation.R
import com.example.presentation.databinding.FragmentCharacterBinding
import com.example.presentation.databinding.FragmentMainBinding
import com.example.presentation.listCharacters.CharacterAdapter


class CharacterFragment(
    private val info: List<InfoCharacter>
) : Fragment() {
    private lateinit var binding: FragmentCharacterBinding
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        adapter = CharacterAdapter(info)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance() = CharacterFragment()
//    }
}