package com.example.usersrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.usersrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val usersAdapter = RecyclerViewUserAdapter(usersList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        onClickListeners()

    }

    private fun init() {
        binding.usersRecyclerView.adapter = usersAdapter
    }

    private fun onClickListeners() {
        binding.addUserButton.setOnClickListener {
            Intent(this@MainActivity, UserActivity::class.java).apply {
                putExtra("isEditing", false)
                startActivity(this)
            }
        }

        usersAdapter.onDeleteClickListener = {
            usersList = usersList.toMutableList()
            usersList.remove(it)
            updateRecycler(usersList)
        }

        usersAdapter.onEditClickListener = {
            Intent(this@MainActivity, UserActivity::class.java).apply {
                putExtra("isEditing", true)
                putExtra("user", it)
                startActivity(this)
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        updateRecycler(usersList)
    }

    private fun updateRecycler(newList: List<User>) {
        usersAdapter.setData(newList)
    }

}