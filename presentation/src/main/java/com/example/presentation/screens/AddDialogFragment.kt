package com.example.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.data.room.ContactEntity
import com.example.presentation.databinding.FragmentAddDialogBinding
import com.example.presentation.ContactViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddDialogFragment : DialogFragment() {

    private val contactViewModel: ContactViewModel by sharedViewModel()
    private var _binding: FragmentAddDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentAddDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phoneNumber = binding.phoneEditText.text.toString()

            if (name.isBlank() || phoneNumber.isBlank()){
                Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                val newContact = ContactEntity(name = name, phoneNumber = phoneNumber)
                contactViewModel.addContact(newContact)
                dismiss()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = AddDialogFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
