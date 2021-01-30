package com.sina.covid19project.fragments.list_country

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodSubtype
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sina.covid19project.R
import com.sina.covid19project.databinding.FragmentListBinding
import com.sina.covid19project.utils_extentions.ListState
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*


class ListFragment : Fragment(),
    CountryListAdapter.ListItemListener {
    lateinit var binding: FragmentListBinding
    private val viewModelList: ListViewModel by sharedViewModel()

    val adapterObj: CountryListAdapter by lazy {
        CountryListAdapter(requireContext(), viewModelList.listContainerWhole, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        const val TAG = "ListFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "onViewCreated: follow thread purposes }")

        handleObservables()
        handleSortClicks()
        onSearch()


    }

    private fun handleObservables() {
        viewModelList.listState.observe(viewLifecycleOwner) {
            when (it) {
                ListState.SUCCESSFULLY_LOADED -> {
                    viewModelList._mList?.let { it1 -> adapterObj.setList(it1) }
                    binding.rvCountries.adapter = adapterObj
                    binding.rvCountries.visibility = View.VISIBLE
                    binding.pbLf.visibility = View.GONE
                    binding.btnRetry.visibility = View.GONE

                }
                ListState.LOADING -> {
                    binding.rvCountries.visibility = View.GONE
                    binding.pbLf.visibility = View.VISIBLE
                    binding.btnRetry.visibility = View.GONE
                }
                ListState.LOADING_FAILED -> {
                    showNoConnectionSnackBar()
                    binding.pbLf.visibility = View.INVISIBLE
                    binding.btnRetry.visibility = View.VISIBLE
                    binding.rvCountries.visibility = View.GONE

                }
                else -> {
                    Log.e(TAG, "onViewCreated: listState is -> $it")
                }
            }
        }
        viewModelList.countryNameClicked.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                //been clicked
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToDetailCountryFragment(
                        it
                    )
                )
            }
        }
    }

    private fun onSearch() {
        binding.etSearch.addTextChangedListener {
            Log.e(TAG, "onViewCreated: searchText -> ${it.toString()}")

            //search and change _mList in viewModel
            viewModelList.onSearch(it.toString())
            //set new list in adapter
            viewModelList._mList?.let { it1 -> adapterObj.setList(it1) }
            //notifyData set Changed
            adapterObj.notifyDataSetChanged()
        }
    }

    private fun handleSortClicks() {
        binding.tvPopulationLf.setOnClickListener {
            viewModelList.onSortWithPopulation()
            adapterObj.notifyDataSetChanged()
            Toast.makeText(requireContext(), "براساس جمعیت مرتب شد", Toast.LENGTH_SHORT).show()
        }
        binding.tvHighestCaught.setOnClickListener {
            viewModelList.onSortWithCases()
            adapterObj.notifyDataSetChanged()
            Toast.makeText(
                requireContext(),
                "براساس بیشترین مورد ابتلا مرتب شد",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.tvZToA.setOnClickListener {
            viewModelList.onSortWithZtoA()
            adapterObj.notifyDataSetChanged()
            Toast.makeText(requireContext(), "برعکس حروف الفبا مرتب شد", Toast.LENGTH_SHORT).show()
        }
        binding.tvHighestPercentage.setOnClickListener {
            viewModelList.onSortWithPercentage()
            adapterObj.notifyDataSetChanged()
            Toast.makeText(
                requireContext(),
                "براساس بیشترین درصد مرگ نسبت به ابتلا مرتب شد",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnRetry.setOnClickListener {
            viewModelList.getListApi()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModelList._CountryNameClicked.value = ""
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: back To List")
        binding.rvCountries.adapter = adapterObj
    }

    private fun showNoConnectionSnackBar() {
        Snackbar.make(requireView(), R.string.no_internet_alert_text, 2000)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.no_internet_color))
            .show()

    }

    override fun listenToCountryItem(country: String) {
        viewModelList._CountryNameClicked.value = country
    }


}