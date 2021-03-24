package com.sina.covid19project.entry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.sina.covid19project.R
import com.sina.covid19project.fragments.detail.DetailCountryFragment
import com.sina.covid19project.fragments.home.HomeFragment
import com.sina.covid19project.fragments.list_country.ListFragment
import com.sina.covid19project.fragments.splash.SplashFragment

class ViewPagerHostFragment : Fragment() {
    lateinit var pagerAdapter: MyViewPagerAdapter
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout:TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_view_pager_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = requireActivity().findViewById(R.id.my_view_pager)
        val fragments = ArrayList<Fragment>()
        fragments.add(SplashFragment())
        fragments.add(NewHomeFragment())
        fragments.add(ListFragment())

        pagerAdapter = MyViewPagerAdapter(
            requireActivity().supportFragmentManager,
            fragments,
            lifecycle
        )
        viewPager.adapter = pagerAdapter

        tabLayout = requireActivity().findViewById(R.id.my_tab)

    }
}