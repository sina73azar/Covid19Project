package com.sina.covid19project.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sina.covid19project.R
import com.sina.covid19project.data.ApiClient
import com.sina.covid19project.data.ApiInterface
import com.sina.covid19project.data.ResponseCountry
import com.sina.covid19project.data.ResponseData
import com.sina.covid19project.databinding.FragmentDetailCountryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailCountryFragment : Fragment() {
    companion object{
        private const val TAG = "DetailCountryFragment"
    }
    private lateinit var binding: FragmentDetailCountryBinding
    var countryObj:ResponseCountry?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentDetailCountryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countryName =
            DetailCountryFragmentArgs.fromBundle(requireArguments()).countryName ?: "iran"
        Log.e(TAG, "onViewCreated: countryName got from bundle is: $countryName", )
        getSpecificCountryData(countryName)
    }

    private fun getSpecificCountryData(countryName:String) {
        val apiInterface = ApiClient.client.create(ApiInterface::class.java)
        val callCountry=apiInterface.getSpecificCountry(countryName)
        callCountry.enqueue(object : Callback<ResponseCountry> {
            override fun onResponse(
                call: Call<ResponseCountry>,
                response: Response<ResponseCountry>
            ) {
                countryObj = response.body()
                Log.e(TAG, "onResponse: $countryObj")
            }

            override fun onFailure(call: Call<ResponseCountry>, t: Throwable) {
//                Log.e(TAG, "onFailure: is called")
                Log.e(TAG, "onFailure: ${t.message}", )
            }

        })
    }

}