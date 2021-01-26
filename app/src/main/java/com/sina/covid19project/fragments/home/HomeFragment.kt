package com.sina.covid19project.fragments.home


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat


import androidx.navigation.fragment.findNavController
import com.sina.covid19project.R
import com.sina.covid19project.repository.HomeRepository
import com.sina.covid19project.databinding.FragmentHomeBinding
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
        binding.tvByCountry.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }
    }

    private fun handleObservables() {
        viewModel.isInternetConnected.observe(viewLifecycleOwner) { isConnected ->
            Log.e(TAG, "onViewCreated: isInternetConnected: $isConnected")
            if (isConnected) {
                Log.e(TAG, "onViewCreated: internet on")
                viewModel.retrofitRequestForData()

            } else {
                //no internet at all
                Log.e(TAG, "onViewCreated: internetIs off")
                handleDataOffline()
                handleViewOffline()
            }
            setViewContent()
        }
        viewModel.repository.dataIsLoaded.observe(viewLifecycleOwner) {
            Log.e(TAG, "onViewCreated: isdataLoaded: $it")
            if (it) {

                handleViewChange(it)
            } else {
                Log.e(TAG, "onViewCreated: onFailure retrofit")
                handleDataOffline()
                handleViewChange(it)
            }
            setViewContent()
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

    private fun handleDataOffline() {
        viewModel.repository.setMapFromSharedPref()
        setViewContent()
    }

    private fun handleViewChange(isDataLoaded: Boolean) {
        Log.e(TAG, "handleViewChange: handle view change method when data is loaded is : $isDataLoaded", )
        if (isDataLoaded) {
            binding.tvStLastUpdate.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.m_green_2
                )
            )
            binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.m_green_2))
            binding.tvAlertNoInternet.visibility = View.GONE
        } else {
            if(viewModel.isInternetConnected.value == true){
                Log.e(TAG, "handleViewChange: ", )
                binding.apply {
                    tvAlertNoInternet.text = requireContext().getString(R.string.no_internet_text)
                    tvAlertNoInternet.visibility = View.VISIBLE
                    tvStLastUpdate.setTextColor(Color.BLACK)
                    tvDate.setTextColor(Color.BLACK)
                }
            }

        }
    }

    private fun setViewContent() {
        Log.e(TAG, "setViewContent:")

        binding.run {
            tvShowCases.text = viewModel.repository.dataHomeMap[HomeRepository.CASES]
            tvCasesTodayHf.text = viewModel.repository.dataHomeMap[HomeRepository.TODAY_CASES]
            tvDeathAll.text = viewModel.repository.dataHomeMap[HomeRepository.DEATHS]
            tvDeathToday.text = viewModel.repository.dataHomeMap[HomeRepository.TODAY_DEATH]
            tvShowRecovered.text = viewModel.repository.dataHomeMap[HomeRepository.RECOVERED]
            tvRecoveredToday.text =
                viewModel.repository.dataHomeMap[HomeRepository.TODAY_RECOVERED]
            tvDate.text = viewModel.repository.dataHomeMap[HomeRepository.DATE_CONST]
        }
    }
}