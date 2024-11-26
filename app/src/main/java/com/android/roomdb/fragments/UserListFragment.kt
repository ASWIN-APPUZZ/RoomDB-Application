package com.android.roomdb.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.android.roomdb.R
import com.android.roomdb.adapters.UserListAdapter
import com.android.roomdb.databinding.FragmentUserListBinding
import com.android.roomdb.viewModel.UserViewModel

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserListAdapter

    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserListBinding.bind(view)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.apply {
            btnAddUser.setOnClickListener {
                findNavController().navigate(R.id.action_userListFragment_to_addUserFragment)
            }
            initViews()
        }
    }

    private fun initViews() {
        adapter = UserListAdapter(onItemClickManager = {user->
            findNavController().navigate(UserListFragmentDirections.actionUserListFragmentToUpdateUserFragment(currentUser = user))
        }, onDeleteClickManager = {deleteUser->
            viewModel.deleteUser(deleteUser)
        })

        binding.rvUsers.adapter= adapter

        viewModel.getAllUsers.observe(viewLifecycleOwner){users->
            adapter.setData(users)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}