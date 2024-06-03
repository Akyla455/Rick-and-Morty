package com.example.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.model.InfoCharacter
import com.example.presentation.ViewModel.CharacterState
import com.example.presentation.ViewModel.CharacterViewModel
import com.example.presentation.databinding.FragmentMainBinding
import com.example.presentation.listCharacters.CharacterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val characterViewModel: CharacterViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterViewModel.characterState.observe(viewLifecycleOwner, Observer{ state ->
            when(state){
                is CharacterState.Success -> fragmentTransaction(CharacterFragment(state.info))
                is CharacterState.Loading -> fragmentTransaction(LoadingFragment())
                is CharacterState.Error -> fragmentTransaction(ErrorFragment())
            }
        })

    }

    private fun fragmentTransaction(fragment: Fragment){
        childFragmentManager.beginTransaction().apply {
            replace(binding.mainContainer.id, fragment)
            addToBackStack(null)
            commit()
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
            }
    }
