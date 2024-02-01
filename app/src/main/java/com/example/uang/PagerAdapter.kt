package com.example.uang

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uang.ui.fragment.expanse.ExpanseFragment
import com.example.uang.ui.fragment.income.IncomeFragment


class PagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IncomeFragment()
            1 -> ExpanseFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

}