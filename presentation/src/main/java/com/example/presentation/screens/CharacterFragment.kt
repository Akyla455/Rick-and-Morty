package com.example.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.R
import com.example.presentation.viewModel.CharacterState
import com.example.presentation.viewModel.CharacterViewModel
import com.example.presentation.databinding.FragmentCharacterBinding
import com.example.presentation.listCharacters.CharacterAdapter
import com.example.presentation.listCharacters.PaginationScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharacterFragment : Fragment() {
    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var binding: FragmentCharacterBinding
    private lateinit var adapter: CharacterAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var isLoading = false
    private var isLastPage = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        adapter = CharacterAdapter(mutableListOf(),
            retryCallback = {retryLoadMoreCharacters()})
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                viewModel.loadMoreCharacters()
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

        })
        viewModel.characterState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is CharacterState.LoadingShimmer -> {
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()
                }

                is CharacterState.Loaded -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.updateItems(state.info)
                    isLoading = false
                }
                is CharacterState.Error -> {
                    binding.shimmerLayout.visibility = View.GONE
                    isLoading = true
                    Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun retryLoadMoreCharacters(){
        isLoading = true
        viewModel.loadMoreCharacters()
    }

    companion object {

        @JvmStatic
        fun newInstance() = CharacterFragment()
    }
}