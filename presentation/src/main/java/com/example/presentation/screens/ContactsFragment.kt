package com.example.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.databinding.FragmentContactsBinding
import com.example.presentation.listcontacts.ContactsAdapter
import com.example.presentation.viewModel.ContactState
import com.example.presentation.viewModel.ContactViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by viewModel()
    private lateinit var adapter: ContactsAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewContact.layoutManager = layoutManager
        adapter = ContactsAdapter(mutableListOf())
        binding.recyclerViewContact.adapter = adapter

        lifecycleScope.launch {
            contactViewModel.state.collect { state ->
                when (state) {
                    is ContactState.AddContact -> TODO()
                    is ContactState.Error -> TODO()
                    is ContactState.Loading -> TODO()
                    is ContactState.Success -> {
                        adapter.updateItems(state.contact)
                    }
                }
            }
        }

        binding.fabButton.setOnClickListener {
            val dialog = AddDialogFragment()
            dialog.show(parentFragmentManager, "AddDialogFragment")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment

    }
}