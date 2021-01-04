package com.sina.covid19project.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.sina.covid19project.R
import com.sina.covid19project.data.*
import com.sina.covid19project.databinding.FragmentListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListFragment : Fragment() {
    lateinit var binding:FragmentListBinding
    lateinit var adapterObj:CountryListAdapter
    var mSortMode:SortMode=SortMode.DEFAULT
    var mTextSearch:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentListBinding.inflate(inflater)
        return binding.root
    }
    companion object{
        const val TAG="ListFragment"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMinimumListOfCountryData(mTextSearch,mSortMode)
        binding.etSearch.addTextChangedListener {
            mTextSearch=it.toString()
            getMinimumListOfCountryData(mTextSearch,mSortMode)
        }
        binding.tvPopulationLf.setOnClickListener {
            mSortMode=SortMode.POPULATION
            getMinimumListOfCountryData(mTextSearch,mSortMode)
        }
        binding.tvHighestCaught.setOnClickListener {
            mSortMode=SortMode.CASES
            getMinimumListOfCountryData(mTextSearch,mSortMode)
        }
        binding.tvZToA.setOnClickListener {
            mSortMode=SortMode.Z_TO_A
            getMinimumListOfCountryData(mTextSearch,mSortMode)
        }
        binding.tvHighestPercentage.setOnClickListener {
            mSortMode=SortMode.Z_TO_A
            getMinimumListOfCountryData(mTextSearch,mSortMode)
        }
    }



    private fun getMinimumListOfCountryData(searchText:String,sortMode: SortMode) {
//        var listTemp= mutableListOf<ResponseData>()
        val api = ApiClient.client.create(ApiInterface::class.java)
        val call = api.getAllCountries()
        call.enqueue(object : Callback<MutableList<ResponseData>> {
            override fun onResponse(
                call: Call<MutableList<ResponseData>>,
                response: Response<MutableList<ResponseData>>
            ) {
                if (response.isSuccessful) {
                    setupRecyclerViewWithThisList(response.body()!!, searchText, sortMode)
                }
            }

            override fun onFailure(call: Call<MutableList<ResponseData>>, t: Throwable) {
                Log.e("ListFragment", "onFailure:$t ",)

            }
        })

    }

    private fun setupRecyclerViewWithThisList(list: MutableList<ResponseData>,searchText: String,sortMode: SortMode) {
        var newList= mutableListOf<ResponseData>()
        newList = if (searchText == "") {
            list
        } else {
            list.filter { it.country.contains(searchText,true) } as MutableList<ResponseData>
        }
        when (sortMode) {
            
            SortMode.CASES->{newList.sortByDescending { it.cases  }
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is cases", )
            }
            SortMode.Z_TO_A->{newList.sortByDescending { it.country }
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is Z to A", )}
            SortMode.POPULATION->{newList.sortByDescending { it.population}
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is population", )}
            SortMode.PERCENTAGE->{ newList.sortBy { (it.deaths.toFloat()/it.cases.toFloat())*100 }
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is percentage", )}
            else -> {
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is Default", )}
        }
        val adapterObj=CountryListAdapter(requireContext(),newList)
        binding.rvCountries.adapter=adapterObj
    }



}