package com.android.roomdb.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.roomdb.R
import com.android.roomdb.databinding.FragmentUpdateUserBinding
import com.android.roomdb.model.UserModel
import com.android.roomdb.viewModel.UserViewModel

class UpdateUserFragment : Fragment(R.layout.fragment_update_user) {

    private var _binding: FragmentUpdateUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

    private val args: UpdateUserFragmentArgs by navArgs<UpdateUserFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentUpdateUserBinding.bind(view)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val name = args.currentUser.userName
        val email = args.currentUser.userEmail
        val age = args.currentUser.userAge

        binding.apply {

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            etUpdateName.setText(name)
            etUpdateEmail.setText(email)
            etUpdateAge.setText(age.toString())

            btnUpdate.setOnClickListener {

                val updatedName = etUpdateName.text.toString()
                val updatedEmail = etUpdateEmail.text.toString()
                val updatedAge = etUpdateAge.text.toString()
                if (checkInput(updatedName, updatedEmail, updatedAge)) {
                    val updatedUser = UserModel(
                        args.currentUser.userId,
                        updatedName,
                        updatedEmail,
                        updatedAge.toInt()
                    )
                    viewModel.updateUser(updatedUser)
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun checkInput(updatedName: String, updatedEmail: String, updatedAge: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!updatedEmail.matches(emailPattern.toRegex())) {
            binding.etUpdateEmail.error = "Invalid Email"
            return false
        }
        if (updatedAge.toInt() < 18) {
            binding.etUpdateAge.error = "Age should be greater than 18"
            return false
        }
        if (updatedAge.toInt() > 100) {
            binding.etUpdateAge.error = "Age should be less than 100"
            return false
        }
        return !(updatedName.isEmpty() || updatedEmail.isEmpty() || updatedAge.isEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}