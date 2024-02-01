package com.example.uang.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

import com.example.uang.PagerAdapter
import com.example.uang.R
import com.example.uang.databinding.ActivityAddTranscationBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddTranscationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTranscationBinding

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transcation)
        binding = ActivityAddTranscationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewpagerAdapter = PagerAdapter(this)

        binding.viewpager2.adapter = viewpagerAdapter

        TabLayoutMediator(binding.tabLayout2, binding.viewpager2) { tab, position ->
            // Customize tab titles if needed
            tab.text = when (position) {
                0 -> "Income"
                1 -> "Expense"
                else -> ""
            }
        }.attach()

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "Add Transaction"
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}