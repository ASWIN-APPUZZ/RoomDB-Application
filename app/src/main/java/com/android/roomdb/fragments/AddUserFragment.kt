package com.android.roomdb.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.roomdb.R
import com.android.roomdb.data.UserEntity
import com.android.roomdb.viewModel.UserViewModel
import com.android.roomdb.databinding.FragmentAddUserBinding
import com.android.roomdb.model.UserModel

class AddUserFragment : Fragment(R.layout.fragment_add_user) {

    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddUserBinding.bind(view)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnSave.setOnClickListener {
                insertDataToDatabase()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertDataToDatabase() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val age = binding.etAge.text.toString()
        if (inputCheck(name, email, age)) {
            val user = UserModel(0, name, email, Integer.parseInt(age ))
            viewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "insertDataToDatabase: $user")
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(name: String, email: String, age: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(emailPattern.toRegex())) {
            binding.etEmail.error = "Invalid Email"
            return false
        }
        if (age.toInt() < 18) {
            binding.etAge.error = "Age should be greater than 18"
            return false
        }
        if (age.toInt() > 100) {
            binding.etAge.error = "Age should be less than 100"
            return false
        }
        return !(name.isEmpty() || email.isEmpty() || age.isEmpty())
    }
}