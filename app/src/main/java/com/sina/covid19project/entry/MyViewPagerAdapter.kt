package com.sina.covid19project.entry

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewPagerAdapter(
    fragmentManager: FragmentManager,
    fragments: ArrayList<Fragment>,
    lifecycle: Lifecycle

):FragmentStateAdapter(fragmentManager,lifecycle) {
    private val list=fragments
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {

        return list[position]

    }
}