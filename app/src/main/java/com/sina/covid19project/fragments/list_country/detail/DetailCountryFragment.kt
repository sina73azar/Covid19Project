package com.sina.covid19project.fragments.list_country.detail


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.sina.covid19project.R

import com.sina.covid19project.data.network.ApiClient
import com.sina.covid19project.data.network.ApiInterface
import com.sina.covid19project.data.data_model.ResponseCountry
import com.sina.covid19project.databinding.FragmentDetailCountryBinding
import com.sina.covid19project.utils.CustomTextView
import kotlinx.android.synthetic.main.fragment_detail_country.view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailCountryFragment : Fragment() {
    companion object {
        private const val TAG = "DetailCountryFragment"
    }

    private lateinit var binding: FragmentDetailCountryBinding
    var countryObj: ResponseCountry? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailCountryBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get country name from list fragment
        val countryName =
            DetailCountryFragmentArgs.fromBundle(requireArguments()).countryName
        binding.tvCountryName.text = countryName
        fillCustomTextView()
        binding.appBarLayout.addOnOffsetChangedListener(object :
            AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                Log.e(TAG, "onOffsetChanged: $verticalOffset", )
            }

        })
        getSpecificCountryData(countryName)
        
    }


    @SuppressLint("SetTextI18n")
    private fun fillCustomTextView() {


    }


    private fun getSpecificCountryData(countryName: String) {
        val apiInterface = ApiClient.client.create(ApiInterface::class.java)
        val callCountry = apiInterface.getSpecificCountry(countryName)
        callCountry.enqueue(object : Callback<ResponseCountry> {
            override fun onResponse(
                call: Call<ResponseCountry>,
                response: Response<ResponseCountry>
            ) {
                countryObj = response.body()
                Log.e(TAG, "onResponse: $countryObj")
                bindDataToView()
            }

            override fun onFailure(call: Call<ResponseCountry>, t: Throwable) {
//                Log.e(TAG, "onFailure: is called")
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    private fun bindDataToView() {
        countryObj?.countryInfo?.let {

            val flagUrl = it.flag
            flagUrl?.let { loadImageFlag(it) }
        }
        binding.tvContinent.text = countryObj?.continent
        binding.tvPopulation.text = countryObj?.population.toString()
        setPgGone()
//        setGroupVisible()
    }

    private fun setPgGone() {
        binding.pbDetailCountry.visibility = View.GONE
    }

    private fun setGroupVisible() {
//        binding.groupDetailsFragment.visibility=View.VISIBLE
    }

    private fun loadImageFlag(url: String) {
        Glide.with(requireContext()).load(url).into(binding.imgFlag)
    }

}