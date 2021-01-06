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
    var mList:MutableList<ResponseData>?=null
    var mSortMode:SortMode=SortMode.DEFAULT
    var mTextSearch:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentListBinding.inflate(inflater)
        return binding.root
    }
    companion object{
        const val TAG="ListFragment"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterObj= CountryListAdapter(requireContext(),mList)
        getMinimumListOfCountryData()
        binding.etSearch.addTextChangedListener {
            mTextSearch=it.toString()
            getMinimumListOfCountryData()
        }
        binding.tvPopulationLf.setOnClickListener {
            mSortMode=SortMode.POPULATION
            getMinimumListOfCountryData()
        }
        binding.tvHighestCaught.setOnClickListener {
            mSortMode=SortMode.CASES
            getMinimumListOfCountryData()
        }
        binding.tvZToA.setOnClickListener {
            mSortMode=SortMode.Z_TO_A
            getMinimumListOfCountryData()
        }
        binding.tvHighestPercentage.setOnClickListener {
            mSortMode=SortMode.PERCENTAGE
            getMinimumListOfCountryData()
        }
    }



    private fun getMinimumListOfCountryData() {
//        var listTemp= mutableListOf<ResponseData>()
        val api = ApiClient.client.create(ApiInterface::class.java)
        val call = api.getAllCountries()
        call.enqueue(object : Callback<MutableList<ResponseData>> {
            override fun onResponse(
                call: Call<MutableList<ResponseData>>,
                response: Response<MutableList<ResponseData>>
            ) {
                if (response.isSuccessful) {
                    mList=response.body()!!
                    setupRecyclerViewWithThisList()
                }
            }

            override fun onFailure(call: Call<MutableList<ResponseData>>, t: Throwable) {
                Log.e("ListFragment", "onFailure:$t ",)

            }
        })


    }

    private fun setupRecyclerViewWithThisList() {
        val newList: MutableList<ResponseData> = if (mTextSearch == "") {
            mList!!
        } else {
            mList?.filter { it.country?.contains(mTextSearch,true)?:true }
         as MutableList<ResponseData>
        }
        when (mSortMode) {
            SortMode.CASES->{newList.sortByDescending { it.cases  }
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is cases", ) }
            SortMode.Z_TO_A->{newList.sortByDescending { it.country }
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is Z to A", )}
            SortMode.POPULATION->{newList.sortByDescending { it.population}
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is population", )}
            SortMode.PERCENTAGE->{ newList.sortByDescending { it.percentage }
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is percentage", )}
            else -> {
                Log.e(TAG, "setupRecyclerViewWithThisList: SortMode is Default", )}
        }
        adapterObj.setList(newList)
        binding.rvCountries.adapter=adapterObj
    }
}