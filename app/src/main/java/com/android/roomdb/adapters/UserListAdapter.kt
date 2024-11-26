package com.android.roomdb.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.roomdb.databinding.UserListLayoutBinding
import com.android.roomdb.model.UserModel

class UserListAdapter(
    val onItemClickManager: (item: UserModel) -> Unit,
    val onDeleteClickManager: (item: UserModel) -> Unit
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private var userList = emptyList<UserModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            UserListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = userList[position]
        holder.userBinder(data, onItemClickManager, onDeleteClickManager)
        holder.itemView.setOnClickListener {
            onItemClickManager.invoke(data)
        }
    }

    class ViewHolder(private val binding: UserListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun userBinder(
            user: UserModel,
            onItemClickManager: (item: UserModel) -> Unit,
            onDeleteClickManager: (item: UserModel) -> Unit
        ) {
            binding.apply {
                userId.text = user.userId.toString()
                tvName.text = user.userName
                tvEmail.text = user.userEmail
                tvAge.text = "(${user.userAge})"

                userLayout.setOnClickListener {
                    onItemClickManager.invoke(user)
                }
                btnDelete.setOnClickListener {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(it.context)
                    builder.setTitle("Delete User?")
                        .setMessage("Are you sure you want to delete this user?")
                        .setPositiveButton("Yes") { _, _ ->
                            onDeleteClickManager.invoke(user)
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<UserModel>) {
        this.userList = user
        notifyDataSetChanged()

    }
}