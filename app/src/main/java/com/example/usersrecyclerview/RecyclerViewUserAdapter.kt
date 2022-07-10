package com.example.usersrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.usersrecyclerview.databinding.UserItemBinding

class RecyclerViewUserAdapter(private var users: List<User>): RecyclerView.Adapter<RecyclerViewUserAdapter.UserViewHolder>() {

    var onDeleteClickListener: ((User) -> Unit)? = null
    var onEditClickListener: ((User) -> Unit)? = null

    inner class UserViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        init(holder, position)
    }

    override fun getItemCount() = users.size

    private fun init(holder: UserViewHolder, position: Int) {

        val user = users[position]

        holder.binding.apply {
            firstNameTextView.text = user.firstName
            lastNameTextView.text = user.lastName
            emailTextView.text = user.email

            deleteImageButton.setOnClickListener {
                onDeleteClickListener?.invoke(user)
            }
            editImageButton.setOnClickListener {
                onEditClickListener?.invoke(user)
            }
        }
    }

    fun setData(newList: List<User>) {
        val diffUtil = UserDiffCallback(users, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        users = newList
        diffResult.dispatchUpdatesTo(this)
    }

}

class UserDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].email == newList[newItemPosition].email
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}