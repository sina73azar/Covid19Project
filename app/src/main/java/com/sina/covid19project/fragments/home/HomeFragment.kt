package com.sina.covid19project.fragments.home

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.sina.covid19project.R
import com.sina.covid19project.databinding.FragmentHomeBinding
import com.sina.covid19project.utils_extentions.ListState
import com.sina.covid19project.utils_extentions.getFormattedAmount
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    companion object {
        const val TAG = "HomeFragment"
    }

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleObservables()
        binding.tvLinkSource.movementMethod = LinkMovementMethod.getInstance()
        binding.tvByCountry.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }
    }

    private fun handleObservables() {
        viewModel.isInternetConnected.observe(viewLifecycleOwner) { isConnected ->
            Log.e(TAG, "onViewCreated: isInternetConnected: $isConnected")
            Log.e(TAG, "onViewCreated: internet on")
            viewModel.getWorldData()
        }
        viewModel.listState.observe(viewLifecycleOwner) {
            if (it == ListState.SUCCESSFULLY_LOADED) {
                handleSuccessLoadingView()
            } else if (it == ListState.LOADING_FAILED) {
                Log.e(TAG, "Loading failed")
                handleViewOffline()
            }
            setViewContent()
        }
    }

    private fun handleSuccessLoadingView() {
        binding.apply {

            tvAlertNoInternet.visibility = View.GONE
            ContextCompat.getColor(
                requireContext(),
                R.color.m_green_2
            ).let {
                tvStLastUpdate.setTextColor(it)
                tvDate.setTextColor(it)
            }

        }
    }

    private fun handleViewOffline() {
        binding.apply {
            tvAlertNoInternet.text = requireContext().getString(R.string.no_internet_alert_text)
            tvAlertNoInternet.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.no_internet_color
                )
            )
            tvAlertNoInternet.visibility = View.VISIBLE
            tvStLastUpdate.setTextColor(Color.BLACK)
            tvDate.setTextColor(Color.BLACK)
        }
    }

    private fun setViewContent() {
        Log.e(TAG, "setViewContent:")
        binding.run {
            viewModel.sharedPref.let {
                tvShowCases.text = getFormattedAmount(it.getInt(HomeViewModel.CASES, 0))
                tvCasesTodayHf.text = getFormattedAmount(it.getInt(HomeViewModel.TODAY_CASES, 0))
                tvDeathAll.text = getFormattedAmount(it.getInt(HomeViewModel.DEATHS, 0))
                tvDeathToday.text = getFormattedAmount(it.getInt(HomeViewModel.TODAY_DEATH, 0))
                tvShowRecovered.text = getFormattedAmount(it.getInt(HomeViewModel.RECOVERED, 0))
                tvRecoveredToday.text =
                    getFormattedAmount(it.getInt(HomeViewModel.TODAY_RECOVERED, 0))
                tvDate.text = it.getString(HomeViewModel.DATE_CONST, "....")
            }
        }
    }
}