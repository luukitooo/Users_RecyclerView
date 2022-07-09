package com.example.usersrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.children
import com.example.usersrecyclerview.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    private var isEditing: Boolean = false
    private lateinit var oldUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        onClickListeners()

    }

    private fun init() {
        isEditing = intent.getBooleanExtra("isEditing", false)
        if (isEditing) {
            oldUser = intent.getSerializableExtra("user") as User
            binding.apply {
                modeTextView.text = "Edit User"
                emailEditText.visibility = View.GONE
                firstNameEditText.setText(oldUser.firstName)
                lastNameEditText.setText(oldUser.lastName)
                emailEditText.setText(oldUser.email)
            }
        }
    }

    private fun onClickListeners() {
        binding.saveButton.setOnClickListener {
            when (isEditing) {
                false -> {
                    createUser(getUser())
                }
                true -> {
                    editUser(oldUser, getUser())
                }
            }
        }
    }

    private fun areLinesEmpty(viewGroup: ViewGroup): Boolean {
        viewGroup.children.forEach { if (it is EditText && it.text.isEmpty()) return true }
        return false
    }

    private fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun userExists(user: User): Boolean {
        usersList.forEach { if (user.email == it.email) return true }
        return false
    }

    private fun getUser(): User {
        return User(
            binding.firstNameEditText.text.toString(),
            binding.lastNameEditText.text.toString(),
            binding.emailEditText.text.toString(),
        )
    }

    private fun createUser(user: User) {
        val email = binding.emailEditText.text.toString()
        if (!areLinesEmpty(binding.root) && isEmailValid(email) && !userExists(user)) {
            usersList = usersList.toMutableList()
            usersList.add(user)
            finish()
        } else Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
    }

    private fun editUser(oldUser: User, newUser: User) {
        usersList = usersList.toMutableList()
        if (!areLinesEmpty(binding.root)) {
            usersList.forEach {
                if (newUser.email == it.email) {
                    usersList[usersList.indexOf(oldUser)] = newUser
                }
            }
            finish()
        } else Toast.makeText(this, "Lines are empty!", Toast.LENGTH_SHORT).show()
    }

}